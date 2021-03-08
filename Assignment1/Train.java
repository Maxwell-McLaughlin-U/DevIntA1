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
    public static Map<String,Integer> getFreq(String strPath) throws IOException{
        String strRegex = "^[a-zA-Z]+$";


        List<File> spamFileList = Files.walk(Paths.get(strPath))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList());

        //System.out.println(spamFileList);//debug msg
        
        Map<String, Integer> spamFrequency = new HashMap<String, Integer>();

        for(int i = 0; i<spamFileList.size();i++){
            File currentFile = spamFileList.get(i);
            Scanner lineReader = new Scanner(currentFile);

            Map<String, Boolean> currentMap = new HashMap<String, Boolean>();

            while(lineReader.hasNextLine()){
                String strLine = lineReader.nextLine();
                String[] strWords = strLine.split(" ");

                for(int j = 0;j < strWords.length;j++){
                    strWords[j].toLowerCase();
                    if ((strWords[j].length() > 1) && (strWords[j].matches(strRegex))){
                        currentMap.putIfAbsent(strWords[j], true);
                    }
                }
            }

            for(Map.Entry<String,Boolean> entry : currentMap.entrySet()){
                if(spamFrequency.containsKey(entry.getKey())){
                    spamFrequency.put(entry.getKey(),spamFrequency.get(entry.getKey()) + 1);
                }else{
                    spamFrequency.put(entry.getKey(),1);
                }
            }

            lineReader.close();
            
        }

        //System.out.println(spamFrequency);
        return(spamFrequency);
    }


   
}