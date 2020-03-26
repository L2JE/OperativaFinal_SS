package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.awt.*;
import java.util.ArrayList;

public class CtrlrHomeWindow {

    public ListView viewSubjects;
    public ListView viewRanges;
    public ListView viewCareers;
    public ListView viewClassrooms;

    public CtrlrHomeWindow(){
        if(true){//Error al cargar
            /*
            viewSubjects = new ListView();
            viewRanges = new ListView();
            viewCareers = new ListView();
            viewClassrooms = new ListView();*/
        }else{//Cargar de archivos .CSV

        }
        /*
        viewSubjects.setCellFactory(new ItemViewFactory());
        viewRanges.setCellFactory(new ItemViewFactory());
        viewCareers.setCellFactory(new ItemViewFactory());
        viewClassrooms.setCellFactory(new ItemViewFactory());
        */
        ObservableList<String> seasonList = FXCollections.<String>observableArrayList("Spring", "Summer", "Fall", "Winter");
        viewSubjects.setItems(seasonList);
    }

}
