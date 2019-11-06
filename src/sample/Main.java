package sample;

import db.DataAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("SingleEncounter.fxml"));
        //Controller db = new Controller();
        //db.DataAccess("test", "test", "test", "test", "test");
        primaryStage.setTitle("Single Encounter");
        primaryStage.setScene(new Scene(root, 1368, 849));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
