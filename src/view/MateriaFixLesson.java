package view;

import data_access.ClassroomDAO;
import data_access.ClassroomDAOImpl;
import data_transfer.ClassroomDTO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import service.Showable;
import java.util.ArrayList;

public class MateriaFixLesson implements SendableFilling {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Showable> pabCB;

    @FXML
    private ComboBox<Showable> roomCB;

    @FXML
    private ComboBox<String> dayCB;

    @FXML
    private ComboBox<String> startTimeCB;

    @FXML
    private TextField teacherText;

    @FXML
    private Label errorMsjLabel;

    private HomeWindowCntlr home;

    private final int minTime = 8;
    private final int maxTime = 21;

    public void initialize(){
        ClassroomDAO dao = ClassroomDAOImpl.getInstance();

        pabCB.getItems().addAll(dao.getAllPabs());
        pabCB.getItems().add(new Showable() {
            @Override
            public String toString() {
                return "";
            }
        });

        pabCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Showable> items = roomCB.getItems();
            if(items != null)
                items.clear();

            if(newValue != null && !newValue.toString().isEmpty()){

                ArrayList<ClassroomDTO> rooms = dao.getRoomsOnPab(((ClassroomDTO)newValue).getPabName());
                if(rooms != null && rooms.size() > 0)
                items.addAll(rooms);

            }
        });

        dayCB.setVisibleRowCount(5);
        dayCB.getItems().addAll(genValidDates());
        startTimeCB.setVisibleRowCount(5);
        startTimeCB.getItems().addAll(genValidHours());
    }

    @Override
    public void setFillingReceiver(HomeWindowCntlr homeCntlr) {
        this.home = homeCntlr;
    }

    public void addPressed(Event event) {

        System.out.println("Hubo un Evento en Agregar");

        if(buttonWasPressed(event)){
            //(pab, room, day, time, teacher)
            //(pab, room, day, time)
            //(day, time, teacher)
            //(day, time)
            //(teacher) ... then all lessons available are assigned to the same teacher
            //(day)
            //(Other cases are prohibited)
            boolean validData = false;


            if(validData){
                Window w = ((Node)event.getSource()).getScene().getWindow();
                ((Stage)w).close();
            }

            errorMsjLabel.setVisible(true);
        }
    }

    public void cancelPressed(Event event) {
        System.out.println("Hubo un Evento en Cancelar");
        if(buttonWasPressed(event)){
            if(buttonWasPressed(event)){
                Window w = ((Node)event.getSource()).getScene().getWindow();
                ((Stage)w).close();
            }
        }
    }

    private boolean buttonWasPressed(Event event){
        String eventType = event.getEventType().getName();
        if(eventType.equals("KEY_PRESSED") &&
                ((KeyEvent) event).getCode() == KeyCode.ENTER)
            return true;

        if(eventType.equals("ACTION"))
            return true;

        return false;

    }

    private String[] genValidDates(){
        return new String[]{ "","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    }

    private String[] genValidHours(){
        String[] values = new String[this.maxTime-this.minTime+2];
        String separator = ":00hs";
        int t = minTime;

        values[0] = "";
        for(int i = 1; t <= maxTime; i++, t++)
            values[i] = t + separator;

        return values;
    }
}