package controller;

import data_access.ClassroomDAO;
import data_access.ClassroomDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    public ComboBox<String> pabCBRoom;
    public ComboBox<String> roomCBRoom;

    private final int minTime = 8;
    private final int maxTime = 22;
    private final int bandDuration = 4;
    public Button addCareerMateriaButton;
    public Button fixClassMateriaButton;

    public void initialize(){
        initCareer();
        initClassroom();
        viewSubjects.setCellFactory(new ItemViewFactory());
        viewCareers.setCellFactory(new ItemViewFactory());
        viewClassrooms.setCellFactory(new ItemViewFactory());
        runTestCustomItems();
    }

    private void initClassroom() {
        /**
         * TODO: Llenar con datos previamente almacenados
         ClassroomDAO classroomDAO = new ClassroomDAOImp();
         pabCBRoom.getItems().addAll(classroomDAO.getAllLocations());
          */

        pabCBRoom.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedPabChangedAula();
            }
        });

    }

    private void initCareer(){
        /**
         * TODO: Llenar con datos previamente almacenados
         */
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

    public void addAulaPressed(ActionEvent actionEvent) {

        Showable newRoom = UIDataValidator.roomValidator(pabCBRoom, roomCBRoom);
        if(newRoom != null){
            viewClassrooms.getItems().add(newRoom);
        }else
            System.out.println("Datos Invalidos: No es posible agregar el aula por un error en los datos ingresados.");

    }

    public void selectedPabChangedAula() {
        /**
         * TODO: necesita inicializar con una implementacion de ClassroomDAO
         */

        roomCBRoom.getItems().clear();
        ArrayList<String> roomsOnPab = UIDataValidator.locationValidator(pabCBRoom);
        if(roomsOnPab != null){

            roomCBRoom.getItems().addAll(roomsOnPab);
            System.out.println(pabCBRoom.getValue());
        }else
            System.out.println("Dato Invalido: Error de tipeo");

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

    public void addCareerMateria(ActionEvent actionEvent) throws IOException {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = loader.load(getClass().getResource("view/materiaAddCareer.fxml").openStream());

            stage.setScene(new Scene(root));
            stage.setTitle("2nd Window");
            stage.showAndWait();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void fixLessonMateria(ActionEvent actionEvent) {
    }
}
