package tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.observableLogic.ObservableProgressProperty;

import java.util.concurrent.TimeUnit;

public class ProgressForGraphTest extends Application {
    public class ObservableClass extends ObservableProgressProperty {
        final int numOfIterations;

        public ObservableClass(int numOfIterations){
            System.out.println("AsyncImpl constructor");
            this.numOfIterations = numOfIterations;

            this.setIncrement((double)1/numOfIterations);//extra Line on regular constructor
        }

        public void startObservableProcess() {
            System.out.println("::INICIO DEL PROCESO::");
            for(int i = 0; i<numOfIterations; i++){
                //System.out.println("Iteracion nro: "+ i);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Error en Observable Process");
                    e.printStackTrace();
                }

                this.updateProgress();//extra Line on regular method
            }
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    private ProgressBar setTestWindow(Stage stage){
        //Creating a progress bar
        ProgressBar progressBar = new ProgressBar(0.1);

        //Creating a hbox to hold the progress bar and progress indicator
        HBox hbox = new HBox(50);
        hbox.setPadding(new Insets(75, 150, 50, 60));
        hbox.getChildren().add(progressBar);
        //Setting the stage
        Group root = new Group(hbox);
        Scene scene = new Scene(root, 595, 200);
        stage.setTitle("Progress Bar");
        stage.setScene(scene);
        stage.show();
        return progressBar;
    }

    @Override
    public void start(Stage primaryStage){
        ProgressBar progressBar = setTestWindow(primaryStage);


        ObservableClass os = new ObservableClass(100);
        os.addListener((observable, oldValue, newValue) -> progressBar.setProgress((Double) newValue));

        //This is what the Adapter will do
        Thread t = new Thread(os::startObservableProcess);
        t.start();
    }

}
