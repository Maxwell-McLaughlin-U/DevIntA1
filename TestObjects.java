package csci2040u.assignment1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestObjects {
    private static double n = 0.0;
    public double accuracy = 0.0;
    public double precision = 0.0;


    public ObservableList<TestFile> getSpamData(String strDirectory) throws IOException {

        Map<String, Double> trainMap = Train.getSpamProb(strDirectory + "/train");

        ObservableList<TestFile> spamResults = FXCollections.observableArrayList();

        double spamProb = 0.0;

        double numCorrectGuesses = 0.0;
        double numGuesses = 0.0;;
        double numTruePositives = 0.0;;
        double numFalsePositives = 0.0;;

        List<File> hamTest = Files.walk(Paths.get(strDirectory + "/test/ham")).filter(Files::isRegularFile)
                .map(Path::toFile).collect(Collectors.toList());

        for (int i = 0; i < hamTest.size(); i++) {
            File currentFile = hamTest.get(i);
            Scanner lineReader = new Scanner(currentFile);
            double nExponent = 0.0;

            while (lineReader.hasNext()) {
                String strLine = lineReader.nextLine();
                String[] strWords = strLine.split(" ");

                for (int j = 0; j < strWords.length; j++) {
                    strWords[j] = strWords[j].toLowerCase();
                    if (trainMap.containsKey(strWords[j]) && trainMap.get(strWords[j]) < 1) {
                        nExponent += (Math.log(1.0 - trainMap.get(strWords[j])) - Math.log(trainMap.get(strWords[j])));
                    }
                }

            }
            lineReader.close();
            spamProb = 1.0 / (1.0 + Math.pow(Math.E, nExponent));

            if(spamProb < 0.5){
                numCorrectGuesses++;
                numGuesses ++;
            }
            else{
                numFalsePositives ++;
                numGuesses ++;
            }
            spamResults.add(new TestFile(currentFile.getName(), "ham", spamProb));
        }
        spamProb = 0.0;
        List<File> spamTest = Files.walk(Paths.get(strDirectory + "/test/spam")).filter(Files::isRegularFile)
                .map(Path::toFile).collect(Collectors.toList());

        for (int i = 0; i < spamTest.size(); i++) {
            File currentFile = spamTest.get(i);
            Scanner lineReader = new Scanner(currentFile);
            double nExponent = 0.0;

            while (lineReader.hasNext()) {
                String strLine = lineReader.nextLine();
                String[] strWords = strLine.split(" ");

                for (int j = 0; j < strWords.length; j++) {
                    strWords[j] = strWords[j].toLowerCase();
                    if (trainMap.containsKey(strWords[j]) && trainMap.get(strWords[j]) < 1) {
                        nExponent += (Math.log(1.0 - trainMap.get(strWords[j])) - Math.log(trainMap.get(strWords[j])));
                    }
                }

            }
            lineReader.close();
            spamProb = 1.0 / (1.0 + Math.pow(Math.E, nExponent));

            if(spamProb > 0.5){
                numCorrectGuesses++;
                numGuesses ++;
                numTruePositives ++;
            }
            else{
                numGuesses ++;
            }

            spamResults.add(new TestFile(currentFile.getName(), "spam", spamProb));
        }

        this.accuracy = numCorrectGuesses/numGuesses;
        this.precision = numTruePositives/(numFalsePositives + numTruePositives);

        return spamResults;
    }


}
