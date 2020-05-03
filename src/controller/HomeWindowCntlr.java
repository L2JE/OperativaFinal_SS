package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import view.customView.*;

public class HomeWindowCntlr {

    public ListView viewSubjects;
    public ListView viewRanges;
    public ListView viewCareers;
    public ListView viewClassrooms;

    public void initialize(){
        viewSubjects.setCellFactory(new ItemViewFactory());
        runTestCustomItems();
    }

/** TEST:
 *  CLICK EN LISTA "MATERIAS" AGREGAR 5 ELEMENTOS
 **/
    private void runTestCustomItems(){
        viewSubjects.setOnMouseClicked(event -> {
            System.out.println("Nro Elementos Antes: " + viewSubjects.getItems().size());
            viewSubjects.getItems().addAll(createList());
            System.out.println("Nro Elementos Despues: " + viewSubjects.getItems().size());
            System.out.println("=====================================");
        });
    }

    private ArrayList<String> createList()
    {
        ArrayList<String> elems = new ArrayList<String>();

        elems.add("Introduccion a la jodita");
        elems.add("Feminismo II");
        elems.add("Aborto Legal I");
        elems.add("Org. de Manifestaciones");
        elems.add("Turismo ahre");

        return elems;
    }
}
