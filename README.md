# CSCI 2020U - Assignment 1

Group Members: Maxwell McLaughlin and Soumia Umaputhiran (100744669)

Project Information
The purpose of this project is to create a spam detector that will train itself using some spam and not spam emails. Then this training is tested using some spam and not spam (ham emails) and displays a JavaFX application of the spam probability of the emails. Some statistics is also displayed on the bottom of the JavaFX application. The accuracy of the spam detector and the precision of the spam dector are the statistics used in this project to determine the effectiveness of the spam detector. The Naive Bayes spam filtering and Bag-of-words model was used for this spam detector.

This is a screenshot of the running application
<img width="499" alt="a3" src="https://user-images.githubusercontent.com/60481370/110410254-97f6aa80-8056-11eb-8afd-20748070413e.png">

How to run: Step-by-Step
1. 
Improvments
We improved the project by making sure that the words read into the file during training is lower cased so the same words are not counted as two different words. For example, "apple" and "Apples" will be known as one word. In addition, we included the files that have a file with probability of 1 given that the word in the file is a spam word, to the spam probability calculation. The probability of 1.0 could not be used in the spam probability calculation, so it was made to 0.9, to round as close to 1.0 as possible. In addition, when the spam emails were read during the testing phase of the project, if the probability of a file was 0 given that a word in the file is spam, it was made to 0.5, since the Bayesian model predicts 50% of emails are assumed to be spam.

References
[1] https://docs.oracle.com/javafx/2/ui_controls/jfxpub-ui_controls.htm
[2] https://en.wikipedia.org/wiki/Bayesian_probability
[3] https://mkyong.com/java/java-files-walk-examples/
