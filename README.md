# CSCI 2020U - Assignment 1

Group Members: Maxwell McLaughlin and Soumia Umaputhiran (100744669)

Project Information
The purpose of this project is to create a spam detector that will train itself using some spam and not spam emails. Then this training is tested using some spam and not spam (ham emails) and displays a JavaFX application of the spam probability of the emails. Some statistics is also displayed on the bottom of the JavaFX application. The accuracy of the spam detector and the precision of the spam dector are the statistics used in this project to determine the effectiveness of the spam detector. The Naive Bayes spam filtering and Bag-of-words model was used for this spam detector.

This is a screenshot of the running application
<img width="499" alt="a3" src="https://user-images.githubusercontent.com/60481370/110410254-97f6aa80-8056-11eb-8afd-20748070413e.png">



How to run: Step-by-Step
1. Click on the green button that says "Code" which is located on the top right corner of the screen.
2. Copy the HTTPS url.
3. Choose a location you want to store your project.
4. Instiate git repository by typing _git init.
5. Then on your terminal type _git remote add origin "url"
6. Then type _git fetch origin main_
7. This should clone the project onto your local repository 
8. Open IntelliJ or any other Java supported IDE.
Instructions for Intelli J:
- Go to _File/Open
- Choose the _Assignment1_ folder that you cloned onto your computer.
- Go to Main.java which is located in _src/csci2040u.assignment1
- Click on the little green hammar on the top right of the screen.
- Click on the little green play button to run the program
Now you will see the JavaFX application.

Improvments
We improved the project by making sure that the words read into the file during training is lower cased so the same words are not counted as two different words. For example, "apple" and "Apples" will be known as one word. In addition, we included the files that have a file with probability of 1 given that the word in the file is a spam word, to the spam probability calculation. The probability of 1.0 could not be used in the spam probability calculation, so it was made to 0.9, to round as close to 1.0 as possible. In addition, when the spam emails were read during the testing phase of the project, if the probability of a file was 0 given that a word in the file is spam, it was made to 0.5, since the Bayesian model predicts 50% of emails are assumed to be spam.

References

[1] https://docs.oracle.com/javafx/2/ui_controls/jfxpub-ui_controls.htm

[2] https://en.wikipedia.org/wiki/Bayesian_probability

[3] https://mkyong.com/java/java-files-walk-examples/
