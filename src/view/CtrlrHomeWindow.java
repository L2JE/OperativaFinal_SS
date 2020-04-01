package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import view.customView.*;

public class CtrlrHomeWindow {

    public ListView viewSubjects;
    public ListView viewRanges;
    public ListView viewCareers;
    public ListView viewClassrooms;

    public void initialize(){
        viewSubjects.setCellFactory(new ItemViewFactory());

    }

/** TEST:
 *  CLICK EN LISTA "MATERIAS" AGREGAR 5 ELEMENTOS
 **/
    private ArrayList<String> createList()
    {
        ArrayList<String> elems = new ArrayList<String>();

        elems.add("Donna" + "Duncan");
        elems.add("Layne" + "Estes");
        elems.add("John" + "Jacobs");
        elems.add("Mason" + "Boyd");
        elems.add("Harry" + "Eastwood");

        return elems;
    }

    public void click(MouseEvent mouseEvent) {
        ListView p = (ListView) mouseEvent.getSource();
        System.out.println("Evento: " + mouseEvent);
        this.viewSubjects.getItems().addAll(createList());
    }
}
