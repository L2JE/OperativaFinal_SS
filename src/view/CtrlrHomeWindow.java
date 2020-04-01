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

    }
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

}
