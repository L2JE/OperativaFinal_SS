package tests;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProgressForGraphTest extends Application {
    private DoubleProperty progress = new SimpleDoubleProperty(0.0);

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
    public void start(Stage primaryStage) throws Exception {
        ProgressBar progressBar = setTestWindow(primaryStage);
        progress.addListener((observable, oldValue, newValue) -> progressBar.setProgress((double) newValue));
        progress.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });

    }

}
