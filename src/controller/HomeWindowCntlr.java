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

        cb.getItems().addAll(values);
    }

    public void addCarreraPressed(ActionEvent actionEvent) {
        int id = 0;
        int duration = -1;
        String careerName = "";
        int chosenStartTime = -1;
        int chosenEndTime = -1;

        ///validate duration
        if(yearsCBCareer.getValue() != null)
            duration = Integer.parseInt((String) yearsCBCareer.getValue());

        ///Validate careerName
        if(nameFieldCareer.getText() != null)
            careerName = nameFieldCareer.getText();

        ///Validate Band
        if(startTimeCBCareer.getValue() != null){
            String tmp = ((String)startTimeCBCareer.getValue());
            chosenStartTime = Integer.parseInt(tmp.substring(0,tmp.indexOf(":")));
        }

        if(endTimeCBCareer.getValue() != null){
            String tmp = ((String)endTimeCBCareer.getValue());
            chosenEndTime = Integer.parseInt(tmp.substring(0,tmp.indexOf(":")));
        }

        CareerDTO c = DataValidator.careerValidator(yearsCBCareer, nameFieldCareer, startTimeCBCareer, endTimeCBCareer);
        System.out.println("Chosen Name: " + careerName);
        System.out.println("Chosen Years: " + duration);
        System.out.println("Chosen StartTime: " + chosenStartTime);
        System.out.println("Chosen endTime: " + chosenEndTime);

        CareerDTO newCareer = new CareerDTO(id, careerName, duration, chosenStartTime, chosenEndTime);
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
