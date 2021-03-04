import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WordCounter {

    private Map<String, Integer> wordCounts;
    private Map<String,Integer> uniqueKeys;
    private Map<String,Integer> uniqueKeys2;
    //private Entry<String, Integer> keyEntry;

    public WordCounter(){
        wordCounts = new TreeMap<>();
    }

    public void parseFile(File file) throws IOException {
        System.out.println("Starting parsing the file:" + file.getAbsolutePath());

        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                parseFile(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                if (isValidWord(token)){
                    countWords(token);
                }
            }
        }

    }

    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

    private void countWords(String word){
        if(wordCounts.containsKey(word)){
            int previous = wordCounts.get(word);
            wordCounts.put(word, previous+1);
        }else{
            wordCounts.put(word, 1);
        }

        /*for (Map.Entry<String,Integer> entry: wordCounts.entrySet())
        {
            String key = entry.getKey();
            int value2 = entry.getValue();

            System.out.println("Key" + key + ", Value: " + value2);
        }*/
    }
    /*private void countWord(String word){
        if(wordCounts.containsKey(word)){
            int previous = wordCounts.get(word);
            wordCounts.put(word, previous+1);
        }else{
            wordCounts.put(word, 1);
        }

        String a[]=word.split(" ");

        for(int i=0;i<a.length;i++)
        {
            if(a[i].length()>0 && wordCounts.containsKey(a[i]))
            {
                wordCounts.put(a[i], wordCounts.get(a[i]) + 1);
                //totword+=1;
            }
            else if(a[i].length() > 0)
            {
                //value =1;
                wordCounts.put(a[i], 1);
                //totword+=1;
            }
        }
    }*/

    /*private void uniqueWord(String word){
        String[] keys = word.split("[!.?:;\\s]");
        int previous;
        for(String key : keys){
            previous = uniqueKeys.get(wordCounts);
            if(uniqueKeys.containsKey(key)){
                // if your keys is already in map, increment count of it
                uniqueKeys.put(key, previous+1);
            }else{
                // if it isn't in it, add it
                uniqueKeys.put(key, 1);
            }
        }

        for(Map.Entry<String, Integer> keyEntry : uniqueKeys.entrySet()){
            if(uniqueKeys2.containsKey(keyEntry)){
                uniqueKeys2.put(keyEntry.getKey(),
                        uniqueKeys2.get(keyEntry.getKey()) + keyEntry.getValue());
            }else{
                uniqueKeys2.put(keyEntry.getKey(), keyEntry.getValue());
            }
        }
    }*/


    public void outputWordCount(int minCount, File output) throws IOException{
        System.out.println("Saving word counts to file:" + output.getAbsolutePath());
        System.out.println("Total words:" + countWords.keySet().size());

        if (!output.exists()){
            output.createNewFile();
            if (output.canWrite()){
                PrintWriter fileOutput = new PrintWriter(output);

                Set<String> keys = countWords.keySet();
                Iterator<String> keyIterator = keys.iterator();

                while(keyIterator.hasNext()){
                    String key = keyIterator.next();
                    int count = countWords.get(key);
                    // testing minimum number of occurances
                    if(count>=minCount){
                        fileOutput.println(key + ": " + count);
                    }
                }

                fileOutput.close();
            }
        }else{
            System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
        }

    }

    //main method
    public static void main(String[] args) {

        if(args.length < 2){
            System.err.println("Usage: java WordCounter <inputDir> <outfile>");
            System.exit(0);
        }

        File dataDir = new File(args[0]);
        File outFile = new File(args[1]);

        WordCounter wordCounter = new WordCounter();
        System.out.println("Hello");
        try{
            wordCounter.parseFile(dataDir);
            wordCounter.outputWordCount(2, outFile);
        }catch(FileNotFoundException e){
            System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }


    }

}