import controller.FileCntlr;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("SchedulerSoft FACULTAD DE CS. HUMANAS - UNICEN");
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
