package controller;

import data_access.CareerDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.ArrayList;

import model.Course;
import model.Showable;
import view.customView.*;

public class HomeWindowCntlr {

    public ListView viewSubjects;
    public ListView viewRanges;
    public ListView viewCareers;
    public ListView viewClassrooms;
    public ComboBox yearsCBCareer;
    public ComboBox hInicCarrera;
    public ComboBox hFinCarrera;
    public TextField nameFieldCareer;

    public void initialize(){
        initCarrera();
        viewSubjects.setCellFactory(new ItemViewFactory());
        viewCareers.setCellFactory(new ItemViewFactory());
        runTestCustomItems();
    }

    private void initCarrera(){
        yearsCBCareer.setVisibleRowCount(3);
        hInicCarrera.setVisibleRowCount(3);
        hFinCarrera.setVisibleRowCount(3);
        fillChoiceBox(yearsCBCareer, 1, 8, 'i');
        fillChoiceBox(hInicCarrera, 8, 21, 't');
        fillChoiceBox(hFinCarrera, 8, 22, 't');
    }

    private void fillChoiceBox(ComboBox cb, int min, int max, char format){
        String[] values = new String[max-min+1];
        String separator;

        switch (format){
            case 't':   separator = ":00hs";
                break;
            default:    separator = "";
                break;
        }

        for(int i = 0; min <= max; i++, min++)
            values[i] = min + separator;

        cb.getItems().addAll(values);
    }

    public void addCarreraPressed(ActionEvent actionEvent) {
        int id = 0;
        int duration = -1;

        /*String careerName = nameFieldCareer.getText();
        int chosenStartTime = (int)hInicCarrera.getValue();
        int chosenEndTime = (int)hFinCarrera.getValue();
*/
        ///validate duration
        if(yearsCBCareer.getSelectionModel().getSelectedItem() != null)
            System.out.println((int)yearsCBCareer.getSelectionModel().getSelectedItem());

  //      CareerDTO newCareer = new CareerDTO(id, careerName, duration, chosenStartTime, chosenEndTime);
        System.out.println("Carrera Agregada!");
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

}
