package csci2040u.assignment1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

import java.io.FileInputStream;
public class Main extends Application {

    private TableView<TestFile> table = new TableView<TestFile>();
    //public String accuracy;
    @Override
    public void start(Stage stage) throws Exception{

        GridPane myGrid = new GridPane();
        myGrid.setAlignment(Pos.CENTER);
        myGrid.setHgap(10);
        myGrid.setVgap(10);
        myGrid.setPadding(new javafx.geometry.Insets(25, 25,  25, 25));
        //myGrid.getChildren().addAll(imageView,table);
        Scene scene = new Scene(new Group(),Color.rgb(110, 115, 119, .99));
        stage.setTitle("Lab05");
        stage.setWidth(680);
        stage.setHeight(650);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(stage);
        String strDirectory = mainDirectory.getPath();

        FileInputStream input = new FileInputStream("resources/images/spamLogo.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(400);


        Text accuracyText = new Text();
        accuracyText.setText("Accuracy:");
        accuracyText.setWrappingWidth(60);
        accuracyText.setFont(Font.font ("Verdana", 12));
        accuracyText.setFill(Color.BLACK);

        Text precisionText = new Text();
        precisionText.setText("Precision:");
        precisionText.setFont(Font.font ("Verdana", 12));
        precisionText.setFill(Color.BLACK);

        TextField accuracyField = new TextField();
        TextField precisionField = new TextField();



        TableColumn Filename = new TableColumn("File Name");
        Filename.setMinWidth(300);
        Filename.setCellValueFactory(new PropertyValueFactory<TestFile, String>("Filename"));


        TableColumn ActualClass = new TableColumn("Actual Class");
        ActualClass.setMinWidth(100);
        ActualClass.setCellValueFactory(new PropertyValueFactory<TestFile, String>("ActualClass"));

        TableColumn SpamProbability = new TableColumn("Spam Probability");
        SpamProbability.setMinWidth(200);
        SpamProbability.setCellValueFactory(new PropertyValueFactory<TestFile, String>("SpamProbRounded"));

        TestObjects tester = new TestObjects();
        table.setItems(tester.getSpamData(strDirectory));
        table.getColumns().addAll(Filename,ActualClass,SpamProbability);
        myGrid.add(imageView, 2,0);
        myGrid.add(table, 2,1);

        myGrid.add(accuracyText, 2,50);
        myGrid.add(accuracyField, 3, 50);
        myGrid.add(precisionText, 2,51);
        myGrid.add(precisionField, 3,51);

        //myGrid.add(precisionLabel, 2,4);

        accuracyField.setText(String.valueOf(tester.accuracy));
        precisionField.setText(String.valueOf(tester.precision));

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(15, 0, 0, 10));
        vbox.getChildren().addAll(imageView,table);


        ((Group) scene.getRoot()).getChildren().addAll(myGrid, vbox);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}