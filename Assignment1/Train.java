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
    private static Map<String,Float> getFreq(String strPath) throws IOException{
        String strRegex = "^[a-zA-Z]+$";


        List<File> spamFileList = Files.walk(Paths.get(strPath)).filter(Files::isRegularFile)
        .map(Path::toFile).collect(Collectors.toList());

        //System.out.println(spamFileList);//debug msg
        
        Map<String, Float> spamFrequency = new HashMap<String, Float>();

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
                    spamFrequency.put(entry.getKey(),1f);
                }
            }

            lineReader.close();
            
        }

        //System.out.println(spamFrequency);

        int intDenom = spamFileList.size();
        for(Map.Entry<String,Float> entry : spamFrequency.entrySet()){
            spamFrequency.put(entry.getKey(),entry.getValue()/intDenom);
        }

        return(spamFrequency);
    }

    public static Map<String,Float> getSpamProb(String spamPath, String hamPath) throws IOException{

        Map<String,Float> retMap = new HashMap<String,Float>();

        Map<String,Float> spamMap = getFreq(spamPath);
        Map<String,Float> hamMap = getFreq(hamPath);

        Map<String,Boolean> legendKeyMap = new HashMap<String,Boolean>();

        for(Map.Entry<String,Float> entry: spamMap.entrySet()){
            legendKeyMap.put(entry.getKey(), true);
        }
        for(Map.Entry<String,Float> entry: hamMap.entrySet()){
            legendKeyMap.put(entry.getKey(), true);
        }

        for(Map.Entry<String,Boolean> entry: legendKeyMap.entrySet()){
            float probSpam = 0;
            float probHam = 0;

            if(spamMap.containsKey(entry.getKey())){
                probSpam = spamMap.get(entry.getKey()) ;
            }
            if(hamMap.containsKey(entry.getKey())){
                probHam = hamMap.get(entry.getKey());
            }


            retMap.put(entry.getKey(),(probSpam)/(probSpam + probHam));
        }

        return retMap;
    }
   
}