import controller.FileCntlr;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // just load fxml file and display it in the stage:

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/homeWindow.fxml"));
        Parent homeWindow = loader.load();
        Scene scene = new Scene(homeWindow);
        primaryStage.setTitle("SchedulerSoft FACULTAD DE CS. HUMANAS - UNICEN");

        homeWindow.prefWidth(700);
        homeWindow.prefHeight(408);
        ((SplitPane)homeWindow).setDividerPosition(0,0.5074);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

        primaryStage.setScene(scene);
        primaryStage.show();

        //runTestImportCSV();
        //testPersistence();

    }

    public void runTestImportCSV(){
        ArrayList<String> elementos = new ArrayList<String>();
        FileCntlr.importCSV("D:\\Users\\leo_c\\Desktop\\TP_Opertativa\\Datos\\materias.txt", elementos);

        for(String s : elementos){
            System.out.println(s);
        }
    }

    public void testPersistence(){
        //no testear SAVE hasta que este conectado con el front
        //FileCntlr.save();
        FileCntlr.load();
    }
}
