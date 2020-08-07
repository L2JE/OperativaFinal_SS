package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;

import model.Course;
import model.Showable;
import view.customView.*;

public class HomeWindowCntlr {

    public ListView viewSubjects;
    public ListView viewRanges;
    public ListView viewCareers;
    public ListView viewClassrooms;
    public ComboBox<String> comboProfesor;

    public void initialize(){
        viewSubjects.setCellFactory(new ItemViewFactory());
        runTestCustomItems();
        comboProfesor.getItems().addAll(
                "Luis Hernandez",
                "Julio Profe",
                "Jose Servantes",
                "Pedro Fernandez"

        );
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

    private ArrayList<Showable> createList()
    {
        ArrayList<Showable> elems = new ArrayList<Showable>();

        elems.add(new Course("Introduccion a la jodita", "C1"));
        elems.add(new Course("Feminismo II", "C2"));
        elems.add(new Course("Aborto Legal I", "C1"));
        elems.add(new Course("Org. de Manifestaciones", "C3"));
        elems.add(new Course("Turismo ahre", "C1"));

        return elems;
    }

    public void addItem(ActionEvent actionEvent) {
        comboProfesor.getItems().add(comboProfesor.getValue());
    }
}
