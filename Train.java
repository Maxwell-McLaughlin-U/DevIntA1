package csci2040u.assignment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.List;
import java.io.File;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.lang.String;

public abstract class Train{
    private static Map<String,Double> getFreq(List<File> spamFileList) throws IOException{
        String strRegex = "^[a-zA-Z]+$";


        //List<File> spamFileList = Files.walk(Paths.get(strPath)).filter(Files::isRegularFile)
        //.map(Path::toFile).collect(Collectors.toList());

        //System.out.println(spamFileList);//debug msg

        Map<String, Double> spamFrequency = new HashMap<String, Double>();

        for(int i = 0; i<spamFileList.size();i++){
            File currentFile = spamFileList.get(i);
            Scanner lineReader = new Scanner(currentFile);

            Map<String, Boolean> currentMap = new HashMap<String, Boolean>();

            while(lineReader.hasNextLine()){
                String strLine = lineReader.nextLine();
                String[] strWords = strLine.split(" ");

                for(int j = 0;j < strWords.length;j++){
                    strWords[j] = strWords[j].toLowerCase();
                    if ((strWords[j].length() > 1) && (strWords[j].matches(strRegex))){
                        currentMap.putIfAbsent(strWords[j], true);
                    }
                }
            }

            for(Map.Entry<String,Boolean> entry : currentMap.entrySet()){
                if(spamFrequency.containsKey(entry.getKey())){
                    spamFrequency.put(entry.getKey(),spamFrequency.get(entry.getKey()) + 1);
                }else{
                    spamFrequency.put(entry.getKey(),1.0);
                }
            }

            lineReader.close();

        }

        //System.out.println(spamFrequency);

        double intDenom = spamFileList.size();
        Map<String, Double> retMap = new HashMap<String, Double>();
        for(Map.Entry<String,Double> entry : spamFrequency.entrySet()){
            spamFrequency.put(entry.getKey(), entry.getValue() / intDenom);
        }

        for(Map.Entry<String,Double> entry : spamFrequency.entrySet()){
            if(entry.getValue() > 0.005){
                retMap.put(entry.getKey(), entry.getValue());
            }
        }

        System.out.println("\n" + retMap);
        return(retMap);
    }

    public static Map<String,Double> getSpamProb(String strPath) throws IOException{
        Map<String,Double> retMap = new HashMap<String,Double>();


        List<File> spamFileList = Files.walk(Paths.get(strPath + "/spam")).filter(Files::isRegularFile)
                .map(Path::toFile).collect(Collectors.toList());

        List<File> hamFileList = Files.walk(Paths.get(strPath + "/ham")).filter(Files::isRegularFile)
                .map(Path::toFile).collect(Collectors.toList());

        List<File> hamFileList2 = Files.walk(Paths.get(strPath + "/ham2")).filter(Files::isRegularFile)
                .map(Path::toFile).collect(Collectors.toList());

        hamFileList.addAll(hamFileList2);


        Map<String,Double> spamMap = getFreq(spamFileList);
        Map<String,Double> hamMap = getFreq(hamFileList);

        Map<String,Boolean> legendKeyMap = new HashMap<String,Boolean>();

        for(Map.Entry<String,Double> entry: spamMap.entrySet()){
            legendKeyMap.put(entry.getKey(), true);
        }
        for(Map.Entry<String,Double> entry: hamMap.entrySet()){
            legendKeyMap.put(entry.getKey(), true);
        }

        for(Map.Entry<String,Boolean> entry: legendKeyMap.entrySet()){
            double probSpam = 0.0;
            double probHam = 0.0;

            if(spamMap.containsKey(entry.getKey())){
                probSpam = spamMap.get(entry.getKey()) ;
            }
            if(hamMap.containsKey(entry.getKey())){
                probHam = hamMap.get(entry.getKey());
            }


            retMap.put(entry.getKey(),(probSpam)/(probSpam + probHam));
        }

        //System.out.println(retMap);
        return retMap;
    }

}