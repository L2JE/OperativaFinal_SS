package controller;

import data_access.CareerDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.ArrayList;

import model.Course;
import model.Showable;
import view.customView.*;

public class HomeWindowCntlr {

    public ListView<Showable> viewSubjects;
    public ListView<Showable> viewRanges;
    public ListView<Showable> viewCareers;
    public ListView<Showable> viewClassrooms;
    public ComboBox yearsCBCareer;
    public ComboBox startTimeCBCareer;
    public ComboBox endTimeCBCareer;
    public TextField nameFieldCareer;

    private final int minTime = 8;
    private final int maxTime = 22;
    private final int bandDuration = 4;

    public void initialize(){
        initCarrera();
        viewSubjects.setCellFactory(new ItemViewFactory());
        viewCareers.setCellFactory(new ItemViewFactory());
        runTestCustomItems();
    }

    private void initCarrera(){
        yearsCBCareer.setVisibleRowCount(3);
        startTimeCBCareer.setVisibleRowCount(3);
        endTimeCBCareer.setVisibleRowCount(3);
        fillChoiceBox(yearsCBCareer, 1, 8, 'i');
        fillChoiceBox(startTimeCBCareer, this.minTime, this.maxTime - bandDuration, 't');
        fillChoiceBox(endTimeCBCareer, this.minTime, this.maxTime, 't');
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

        cb.getItems().addAll((Object[])values);
    }

    public void addCarreraPressed(ActionEvent actionEvent) {

        Showable newCareer = UIDataValidator.careerValidator(yearsCBCareer, nameFieldCareer, startTimeCBCareer, endTimeCBCareer);

        if(newCareer != null){
            /**
             * TODO: Verificar si la carrera ya existe
             */

            viewCareers.getItems().add(newCareer);
            System.out.println("Carrera Agregada!");
        }else
            System.out.println("Datos Invalidos: No es posible agregar la carrera por un error en los datos ingresados.");
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
