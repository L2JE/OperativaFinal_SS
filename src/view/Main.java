package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.io.FileCtrlr;

import java.io.FileWriter;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // just load fxml file and display it in the stage:

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("SchedulerSoft FACULTAD DE CS. HUMANAS");
        primaryStage.setScene(scene);
        primaryStage.show();

        //runTestImportCSV();

    }

    public void runTestImportCSV(){
        ArrayList<String> elementos = new ArrayList<String>();
        FileCtrlr.importCSV("D:\\Users\\leo_c\\Desktop\\TP_Opertativa\\Datos\\materias.txt", elementos);

        for(String s : elementos){
            System.out.println(s);
        }
    }
}
