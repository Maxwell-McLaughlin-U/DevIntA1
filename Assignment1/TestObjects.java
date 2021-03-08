package csci2040u.assignment1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public abstract class TestObjects {
    private static double n = 0.0;
    private static File dataDir;
    private static double spamProb = 0.0;
    private static String userPath;
    private static Map<String,Float> bayesMap;

    private static Map<String,Integer> wordCounts = new TreeMap<>();
    private static TreeMap<String,Integer> tempWordCounts = new TreeMap<>();
    //formula
    // n += Math.log(1-Train.getSpamProb(key)) - Math.log(Train.getSpamProb.get(key));

    public TestObjects(String path, String userPath) throws IOException{
        this.dataDir = new File(path);
        this.userPath = userPath;
        bayesMap = Train.getSpamProb(userPath);
    }

    public static ObservableList<TestFile> getSpamData() {
        ObservableList<TestFile> spamResults = FXCollections.observableArrayList();

        File[] dataDirList = dataDir.listFiles();
        for(File file : dataDirList) {
            try{
                tempWordCounts.clear();
                Scanner scanner = new Scanner(file);
                // scanning token by token
                while (scanner.hasNext()){
                    String  token = scanner.next();
                    token = token.toLowerCase(); //Convert word to lower case and then continue to read word
                    if (isValidWord(token)){
                        countWords(token);
                    }
                }
                wordCounts.putAll(tempWordCounts);


                for(String word : wordCounts.keySet()) {
                    if(bayesMap.get(word) == null){
                        spamProb = 0.0;
                    }
                    else if(bayesMap.get(word) != null) {
                        n += (Math.log(1.0 - bayesMap.get(word)) - Math.log(bayesMap.get(word)));
                        spamProb = 1.0/(1.0+Math.pow(Math.E,n));
                    }
                }

                //spamProb = 1.0/(1.0+Math.pow(Math.E,n));


                if(spamProb > 0.5) {
                    TestFile spamData = new TestFile(dataDir.getName(),"spam",spamProb);
                    spamResults.add(spamData);
                } else {
                    TestFile hamData = new TestFile(dataDir.getName(),"ham",spamProb);
                    spamResults.add(hamData);
                }

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        return spamResults;

    }

    private static boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

    private static void countWords(String word){
        if(wordCounts.containsKey(word)){
            int previous = wordCounts.get(word);
            tempWordCounts.put(word, previous+1);

        }else{
            tempWordCounts.put(word, 1);
        }


    }
}
