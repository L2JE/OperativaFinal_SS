package view;

import data_access.CareerCompDAOImpl;
import data_access.ClassroomDAOImpl;
import data_transfer.CareerDTO;
import data_transfer.ClassroomDTO;
import data_transfer.LectureDTO;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.util.Callback;
import javafx.util.Pair;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import service.ShowableChangeFXCb;
import service.UIDataValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.stage.Stage;
import service.Showable;
import view.customView.*;

public class HomeWindowCntlr {

    private Stage homeStage;

    public ListView<Showable> viewSubjects;
    public ListView<Showable> viewRanges;
    public ListView<Showable> viewCareers;
    public ListView<Showable> viewAllClassrooms;
    public ComboBox yearsCBCareer;
    public ComboBox startTimeCBCareer;
    public ComboBox endTimeCBCareer;
    public TextField nameFieldCareer;

    @FXML
    private ListView<Showable> viewPabs;
    @FXML
    private ListView<Showable> viewRoomsForPab;
    @FXML
    private TextField newPabField;
    @FXML
    private TextField newRoomField;


    @FXML
    private ComboBox<Showable> careerCBSubject;
    @FXML
    private ComboBox<Integer> yearCBSubject;

    private final int minTime = 8;
    private final int maxTime = 22;
    private final int bandDuration = 4;
    public Button addCareerMateriaButton;
    public Button fixClassMateriaButton;

    public void initialize(){
        initCareer();
        initClassroom();
        initSubject();
        viewSubjects.setCellFactory(new ItemViewFactory());
        viewCareers.setCellFactory(new ItemViewFactory());
        viewAllClassrooms.setCellFactory(new ItemViewFactory());
        runTestCustomItems();
    }

    private void initClassroom() {
        /**
         * TODO: Llenar con datos previamente almacenados
          */

        //Inicialize custom item View
        viewPabs.setCellFactory(new ItemViewFactory());
        viewRoomsForPab.setCellFactory(new ItemViewFactory());

        ObservableList<Showable> itemsPabs = viewPabs.getItems();
        ReadOnlyProperty<Showable> selectedItemPab = viewPabs.getSelectionModel().selectedItemProperty();

        selectedItemPab.addListener(new ChangeListener<Showable>() {
            @Override
            public void changed(ObservableValue<? extends Showable> observable, Showable oldValue, Showable newValue) {
                //System.out.println("Se ha seleccionado el item: "+ oldValue + "newValue: "+newValue);

                ObservableList<Showable> itemsRoomsForPab = viewRoomsForPab.getItems();
                itemsRoomsForPab.clear();

                if(newValue!=null){
                    ArrayList<ClassroomDTO> rooms = ClassroomDAOImpl.getInstance().getRoomsOnPab(((ClassroomDTO)newValue).getPabName());

                    if(rooms != null)
                        itemsRoomsForPab.addAll(rooms);

                    System.gc();
                }
            }
        });


        itemsPabs.addListener(new ListChangeListener<Showable>() {
            @Override
            public void onChanged(Change<? extends Showable> c) {
                while (c.next()){
                    if(c.wasRemoved()){

                        ClassroomDTO dto = null;
                        try{
                            dto = (ClassroomDTO) c.getRemoved().get(0);
                            //Pab is removed from system
                        }catch (IllegalStateException e){
                            e.printStackTrace();
                        }

                        if(dto != null && dto.isDeleted().get()){

                            System.out.println("El Elemento: " + dto + "\n Ha sido eliminado de la lista");

                            ClassroomDAOImpl dao = ClassroomDAOImpl.getInstance();
                            dao.deletePab(dto.getPabName());

                            //Pab is removed from views and viewAllClassrooms get refresh
                            ObservableList<Showable> itemsRoomsForPab = viewRoomsForPab.getItems();
                            itemsRoomsForPab.clear();
                            viewAllClassrooms.getItems().clear();
                            viewAllClassrooms.getItems().addAll(dao.getAllRooms());

                            Showable selected = viewPabs.getSelectionModel().getSelectedItem();
                            if(selected != null) {
                                itemsRoomsForPab.addAll(dao.getRoomsOnPab(((ClassroomDTO) selected).getPabName()));
                            }


                            //System.out.println("Pabellon Borrado y lista principal limpia");

                            System.gc();
                        }
                    }
                    //System.out.println("ESTADO DE PERCISTENCIA\n"+ClassroomDAOImpl.getInstance().toString()+"\nFIN ESTADO DE PERCISTENCIA");

                }
            }
        });

        ObservableList<Showable> itemsRooms = viewAllClassrooms.getItems();
        ObservableList<Showable> itemsRoomsForPab = viewRoomsForPab.getItems();

        //TODO: VER DE DIFERENCIAR ENTRE REMOVED FOR CLEAR Y REMOVED FOR BUTTON

        itemsRooms.addListener(new ListChangeListener<Showable>() {
            @Override
            public void onChanged(Change<? extends Showable> c) {
                while (c.next()){
                    if(c.wasRemoved()){
                        System.out.println("Llamado a listener por removed en itemsRoom");
                        ClassroomDTO dto = null;
                        try{
                            dto = (ClassroomDTO) c.getRemoved().get(0);
                            //Pab is removed from system
                        }catch (IllegalStateException e){
                            e.printStackTrace();
                        }

                        if(dto != null && dto.isDeleted().get()){
                            //System.out.println("Cantidad de eliminados en ALLROOMS: "+ c.getRemovedSize());
                            ClassroomDAOImpl dao = ClassroomDAOImpl.getInstance();

                            //System.out.println("dto eliminado: "+ dto);

                            //Se elimina de la lista de aulas del item seleccionado
                            if(((ClassroomDTO)selectedItemPab.getValue()).getPabName().equals(dto.getPabName())){

                                final ClassroomDTO finalDto = dto;
                                int srcIndexDto = itemsRoomsForPab.filtered(new Predicate<Showable>() {
                                    @Override
                                    public boolean test(Showable showable) {
                                        return finalDto.toString().equals(showable.toString());
                                    }
                                }).getSourceIndex(0);

                                itemsRoomsForPab.remove(srcIndexDto);

                                //System.out.println("Es del pabellon seleccionado, deberia haberse eliminado");
                            }

                            //Room is removed from system
                            dao.deleteClassroom(dto.getIdRoom());
                            System.out.println("Se elimino desde ALLrooms el dto: "+ dto);
                        }

                    }
                }
            }
        });

        itemsRoomsForPab.addListener(new ListChangeListener<Showable>() {
            @Override
            public void onChanged(Change<? extends Showable> c) {
                while (c.next()){
                    if(c.wasRemoved()){
                        System.out.println("Llamado a listener por removed en itemsRoom4 pab");
                        ClassroomDTO dto = null;
                        try{
                            dto = (ClassroomDTO) c.getRemoved().get(0);
                            //Pab is removed from system
                        }catch (IllegalStateException e){
                            e.printStackTrace();
                        }

                        if(dto != null && dto.isDeleted().get()){
                            //System.out.println("Cantidad de eliminados: "+ c.getRemovedSize());
                            ClassroomDAOImpl dao = ClassroomDAOImpl.getInstance();
                            //System.out.println("dto eliminado: "+ dto);

                            final ClassroomDTO finalDto = dto;
                            int srcIndexDto = itemsRooms.filtered(new Predicate<Showable>() {
                                @Override
                                public boolean test(Showable showable) {
                                    return finalDto.toString().equals(showable.toString());
                                }
                            }).getSourceIndex(0);

                            itemsRooms.remove(srcIndexDto);

                            //Room is removed from system
                            dao.deleteClassroom(dto.getIdRoom());
                            System.out.println("Se elimino desde rooms for pab el dto: "+ dto);
                        }

                    }
                }
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
         */
        ObservableList<Showable> items = viewCareers.getItems();
        items.addListener(new ListChangeListener<Showable>() {
            @Override
            public void onChanged(Change<? extends Showable> c) {
                while (c.next()){
                    if(c.wasRemoved()){
                        //c.getRemoved().get(0);

                        CareerDTO dto = (CareerDTO) c.getRemoved().get(0);

                        if(dto != null && dto.isDeleted().get()){
                            final CareerDTO finalDto = dto;
                             FilteredList filteredResult = careerCBSubject.getItems().filtered(new Predicate<Showable>() {
                                @Override
                                public boolean test(Showable showable) {
                                    return finalDto.getIdCareer() == ((CareerDTO)showable).getIdCareer();
                                }
                            });

                            if(filteredResult != null && filteredResult.size() > 0)
                                careerCBSubject.getItems().remove(filteredResult.getSourceIndex(0));

                        }
                        CareerCompDAOImpl.getInstance().deleteCareer(dto.getIdCareer());

                    }
                    //System.out.println("ESTADO DE PERCISTENCIA\n"+CareerCompDAOImpl.getInstance().toString()+"\nFIN ESTADO DE PERCISTENCIA");
                }
            }
        });
    }

    private void initSubject(){

        this.careerCBSubject.setVisibleRowCount(5);
        this.yearCBSubject.setVisibleRowCount(5);

        this.careerCBSubject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Showable>() {
            @Override
            public void changed(ObservableValue<? extends Showable> observable, Showable oldValue, Showable newValue) {
                if(newValue != null){
                    int years = ((CareerDTO)newValue).getYears();
                    ObservableList<Integer> items = yearCBSubject.getItems();
                    items.clear();
                    for(int i = 1; i <= years; i++)
                        items.add(i);
                }
            }
        });
    }

    public void initSubjectTab(Event event) {
        this.careerCBSubject.getItems().clear();
        ArrayList<CareerDTO> careers = UIDataValidator.getAvailableCareers();
        this.careerCBSubject.getItems().addAll(careers);

        System.gc();
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
            //System.out.println("Carrera Agregada!");
        }else
            System.err.println("Datos Invalidos: No es posible agregar la carrera por un error en los datos ingresados.");
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

    public void addCareerSubjectPressed(ActionEvent actionEvent) {
        Showable item = this.careerCBSubject.getValue();


        Pair<Showable,Integer> careerInstance = UIDataValidator.careerInstanceValidator(this.careerCBSubject, this.yearCBSubject);

        System.out.println(careerInstance);
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

    public void addPab(ActionEvent actionEvent) {
        Showable newPab = UIDataValidator.pabValidator(newPabField);
        if(newPab != null){

            ClassroomDAOImpl.getInstance().createPab(((ClassroomDTO)newPab).getPabName());

            viewPabs.getItems().add(newPab);
            //System.out.println("Pabellon Agregado!");
        }else
            System.err.println("Datos Invalidos: No es posible agregar el Pabellon por un error en los datos ingresados.");
    }

    public void addRoom(ActionEvent actionEvent) {
        Showable newRoom = UIDataValidator.roomValidator(newRoomField,viewPabs.getSelectionModel().getSelectedItem());
        if(newRoom != null){

            ClassroomDAOImpl.getInstance().createClassroom((ClassroomDTO)newRoom);

            viewRoomsForPab.getItems().add(newRoom);
            viewAllClassrooms.getItems().add(newRoom);
            //System.out.println("Aula Agregada!");
        }else
            System.err.println("Datos Invalidos: No es posible agregar el Aula por un error en los datos ingresados.");
    }
}