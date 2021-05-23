package view;

import data_access.CareerCompDAOImpl;
import data_access.ClassroomDAO;
import data_access.ClassroomDAOImpl;
import data_transfer.CareerCompDTO;
import data_transfer.CareerDTO;
import data_transfer.LectureDTO;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.stage.Modality;
import service.UIDataValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;

import javafx.stage.Stage;
import service.Showable;
import view.customView.*;

public class HomeWindowCntlr {

    private Stage homeStage;

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

        /**
         * Agregar un listener: cuando se borra un valor de la vista, este se saca de la bd
         * ItemView.getItem() returns Showable
         * Cuando borro un itemView le pido a la lista que llame a un listener para avisar que se elimino
         */
        ObservableList<Showable> items = viewCareers.getItems();
        items.addListener(new ListChangeListener<Showable>() {
            @Override
            public void onChanged(Change<? extends Showable> c) {
                while (c.next()){
                    if(c.wasRemoved()){
                        //c.getRemoved().get(0);

                        CareerDTO dto = (CareerDTO) c.getRemoved().get(0);
                        CareerCompDAOImpl.getInstance().deleteCareer(dto.getIdCareer());
                        /*
                        for(Showable item : c.getRemoved()){
                            CareerDTO dto = (CareerDTO) item;
                            CareerCompDAOImpl.getInstance().deleteCareer(dto.getIdCareer());
                        }*/
                    }

                    System.out.println("ESTADO DE PERCISTENCIA\n"+CareerCompDAOImpl.getInstance().toString()+"\nFIN ESTADO DE PERCISTENCIA");

                }
            }
        });
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

            CareerCompDAOImpl.getInstance().createCareer((CareerDTO)newCareer);

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

        elems.add(new LectureDTO(1,"Introduccion a la Jodita C1", "Tinelli"));
        elems.add(new LectureDTO(2,"Feminismo II", "Basile"));
        elems.add(new LectureDTO(3,"Aborto Legal I C2", "Vidella"));
        elems.add(new LectureDTO(4,"Org. de Manifestaciones C1", "Kirchner"));
        elems.add(new LectureDTO(5,"Turismo Ahre C1", "Pacuolo"));

        return elems;
    }

    public void addCareerMateria(ActionEvent actionEvent) throws IOException {
        callWaitNewStageFill("materiaAddCareer.fxml", "Nueva carrera cursante");
    }

    public void fixLessonMateria(ActionEvent actionEvent) {
        callWaitNewStageFill("materiaFixLesson.fxml", "Fijar una clase");
    }

    private void callWaitNewStageFill(String path, String stageTitle){
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

            Parent root = loader.load();
            SendableFilling filling = loader.getController();
            filling.setFillingReceiver(this);

            stage.initOwner(this.homeStage);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle(stageTitle);
            stage.showAndWait();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setHomeStage(Stage homeStage){
        this.homeStage = homeStage;

    }
}