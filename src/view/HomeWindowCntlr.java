package view;

import data_access.CareerDAOImpl;
import data_access.ClassroomDAOImpl;
import data_transfer.*;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Modality;
import service.UIDataValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

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
    private ListView<Showable> careersSubjectView;
    @FXML
    private ListView<Showable> lecturesSubjectView;
    @FXML
    private ListView<Showable> subjectsSubjectView;
    @FXML
    private TextField newSubjectField;

    private final int minTime = 8;
    private final int maxTime = 22;
    private final int bandDuration = 4;

    public void initialize(){
        initCareer();
        initClassroom();
        initSubjectTab();
        viewSubjects.setCellFactory(new ItemViewFactory());
        viewCareers.setCellFactory(new ItemViewFactory());
        viewAllClassrooms.setCellFactory(new ItemViewFactory());
        runTestCustomItems();
    }

    private void initSubjectTab() {
        //set custom cell factory
        subjectsSubjectView.setCellFactory(new ItemViewFactory());
        lecturesSubjectView.setCellFactory(new ItemViewFactory());
        careersSubjectView.setCellFactory(new ItemViewFactory());

        ObservableList<Showable> subjectsItems = lecturesSubjectView.getItems();
        ObservableList<Showable> careersItems = careersSubjectView.getItems();
        ObservableList<Showable> lecturesItems = lecturesSubjectView.getItems();

        //load careers and lectures when selecting a subject
        ReadOnlyProperty<Showable> selectedItemSubject = subjectsSubjectView.getSelectionModel().selectedItemProperty();
        selectedItemSubject.addListener((observableValue, oldValue, newValue) -> {
            System.out.println("Selected Item");
            subjectsItems.clear();
            careersItems.clear();

            if(newValue != null){
                SubjectDTO newSubject = (SubjectDTO)newValue;
                //load lectures and careers
                //ArrayList<Showable> lectures = LectureDAOImpl.getInstance().getLectures(newSubject.getIdSubject());
                //lecturesItems.addAll(lectures);

                List<CareerInstance> careers = newSubject.getCareers();
                careersItems.addAll(careers);
            }
        });

        careersItems.addListener((ListChangeListener<Showable>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {

                    CareerInstance dto = null;
                    try {
                        dto = (CareerInstance) change.getRemoved().get(0);
                        //Pab is removed from system
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    if (dto != null && dto.isDeleted().get()) {
                        ((SubjectDTO) selectedItemSubject.getValue()).removeCareerInstance(dto);
                    }

                }
            }
        });
    }

    private void initClassroom() {
        //Inicialize custom item View
        viewPabs.setCellFactory(new ItemViewFactory());
        viewRoomsForPab.setCellFactory(new ItemViewFactory());

        ObservableList<Showable> itemsPabs = viewPabs.getItems();
        ReadOnlyProperty<Showable> selectedItemPab = viewPabs.getSelectionModel().selectedItemProperty();

        selectedItemPab.addListener((observable, oldValue, newValue) -> {
            //System.out.println("Se ha seleccionado el item: "+ oldValue + "newValue: "+newValue);

            ObservableList<Showable> itemsRoomsForPab = viewRoomsForPab.getItems();
            itemsRoomsForPab.clear();

            if(newValue!=null){
                ArrayList<ClassroomDTO> rooms = ClassroomDAOImpl.getInstance().getRoomsOnPab(((ClassroomDTO)newValue).getPabName());

                if(rooms != null)
                    itemsRoomsForPab.addAll(rooms);

                System.gc();
            }
        });


        itemsPabs.addListener((ListChangeListener<Showable>) c -> {
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
        });

        ObservableList<Showable> allRoomsList = viewAllClassrooms.getItems();
        ObservableList<Showable> itemsRoomsForPab = viewRoomsForPab.getItems();

        allRoomsList.addListener((ListChangeListener<Showable>) c -> {
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
                        ClassroomDAOImpl dao = ClassroomDAOImpl.getInstance();

                        //Si esta seleccionado el pabellon al que pertenece el aula eliminada
                        //Se elimina el aula de la lista de aulas
                        if(((ClassroomDTO)selectedItemPab.getValue()).getPabName().equals(dto.getPabName())){

                            final ClassroomDTO dtoToRemove = dto;

                            itemsRoomsForPab.removeIf(itemToCompare -> dtoToRemove.toString().equals(itemToCompare.toString()));

                        }

                        //Room is removed from system
                        dao.deleteClassroom(dto.getIdRoom());
                        System.out.println("Se elimino desde ALLrooms el dto: "+ dto);
                    }

                }
            }
        });

        itemsRoomsForPab.addListener((ListChangeListener<Showable>) c -> {
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
                        ClassroomDAOImpl dao = ClassroomDAOImpl.getInstance();

                        final ClassroomDTO classroomToRemove = dto;

                        //Remove from view
                        allRoomsList.removeIf(itemToCompare -> classroomToRemove.toString().equals(itemToCompare.toString()));

                        //Room is removed from system
                        dao.deleteClassroom(dto.getIdRoom());
                        System.out.println("Se elimino desde rooms for pab el dto: "+ dto);
                    }

                }
            }
        });

    }

    private void initCareer(){
        yearsCBCareer.setVisibleRowCount(3);
        startTimeCBCareer.setVisibleRowCount(3);
        endTimeCBCareer.setVisibleRowCount(3);
        fillChoiceBox(yearsCBCareer, 1, 8, 'i');
        fillChoiceBox(startTimeCBCareer, this.minTime, this.maxTime - bandDuration, 't');
        fillChoiceBox(endTimeCBCareer, this.minTime, this.maxTime, 't');

        /**
         * Agregar un listener: cuando se borra un valor de la vista, este se saca de la bd
         */
        ObservableList<Showable> allCareersList = viewCareers.getItems();
        allCareersList.addListener((ListChangeListener<Showable>) c -> {
            while (c.next()){
                if(c.wasRemoved()){
                    CareerDTO dto = (CareerDTO) c.getRemoved().get(0);

                    if(dto != null && dto.isDeleted().get()){
                        //TODO: Solo se estan eliminando las carreras que se estan mostrando
                        //TODO: Solucion propuesta:
                        final CareerDTO careerToRemove = dto;

                         careersSubjectView.getItems().removeIf(showable -> {
                             boolean containsCareer = ((CareerInstance)showable).getIdCareer() == careerToRemove.getIdCareer();
                             if(containsCareer)
                                 showable.setDeleted(true);

                             return containsCareer;
                         });
                    }
                    CareerDAOImpl.getInstance().deleteCareer(dto.getIdCareer());

                }
                //System.out.println("ESTADO DE PERCISTENCIA\n"+CareerCompDAOImpl.getInstance().toString()+"\nFIN ESTADO DE PERCISTENCIA");
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

            CareerDAOImpl.getInstance().createCareer((CareerDTO)newCareer);

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

        elems.add(new LectureDTO(1));

        return elems;
    }

    public void addCareerMateria(ActionEvent actionEvent) {
        SubjectDTO selected = (SubjectDTO) subjectsSubjectView.getSelectionModel().getSelectedItem();
        if(selected != null)
            callWaitNewStageFill("materiaAddCareer.fxml", "Agregar una Materia Cursante");
        else
            System.err.println("Error: Seleccione una materia primero");
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

    public void tabMateriaSelected(Event event) {
        //Load all subjects
        if(subjectsSubjectView.getItems().size() < 1){
            //ArrayList<Showable> subjects = SubjectDAOImpl.getInstance.getAllSubjects();
           // subjectsSubjectView.getItems().addAll(subjects);
        }
    }

    public void addSubject(ActionEvent actionEvent) {
        Showable newSubject = UIDataValidator.subjectValidator(newSubjectField);
        if(newSubject != null){

            //SubjectDAOImpl.getInstance().createSubject((SubjectDTO)newSubject);

            subjectsSubjectView.getItems().add(newSubject);
            //System.out.println("Materia Agregada!");
        }else
            System.err.println("Datos Invalidos: No es posible agregar la materia porque ya existe o esta mal escrita.");
    }

    public void addValidCareerToSubject(Showable careerInstance) {
        Showable selected = subjectsSubjectView.getSelectionModel().selectedItemProperty().get();
        ((SubjectDTO)selected).addCareerInstance((CareerInstance) careerInstance);
        careersSubjectView.getItems().add(careerInstance);
    }
}