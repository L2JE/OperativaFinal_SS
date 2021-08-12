package view;

import data_access.ClassroomDAO;
import data_access.ClassroomDAOImpl;
import data_access.LectureDAO;
import data_transfer.ClassroomDTO;
import data_transfer.DayOfWeek;
import data_transfer.LectureDTO;
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
import service.ScheduleStrategy;
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
            if(validateData()){
                Window w = ((Node)event.getSource()).getScene().getWindow();
                ((Stage)w).close();
            }
            else
                errorMsjLabel.setVisible(true);
        }
    }

    private boolean validateData() {
        //(pab, room, day, time, teacher)
        //(pab, room, day, time)
        //(day, time, teacher)
        //(day, time)
        //(teacher) ... then all lessons available are assigned to the same teacher
        //(day)
        //(Other cases are prohibited)
        ClassroomDTO room = (ClassroomDTO)roomCB.getValue();
        String date = dayCB.getValue();
        int lectureStartAt = -1;
        String teacher = teacherText.getText();

        if(startTimeCB.getValue() != null && !startTimeCB.getValue().equals("")) {
            String tmp = ((String) startTimeCB.getValue());
            lectureStartAt = Integer.parseInt(tmp.substring(0, tmp.indexOf(":")));
        }

        LectureDTO lecture = new LectureDTO(1);
        if(room != null)                        lecture.setRoomId(room.getIdRoom());
        if(date != null && !date.equals(""))    lecture.setDayOfWeek(DayOfWeek.valueOf(date));
        if(teacher != null)                     lecture.setTeacher(teacher);
        if(lectureStartAt > -1)                lecture.setDesiredTimeSlot(lectureStartAt);

        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("Materia: "+ lecture.getIdSubject());
        System.out.println("Clase: "+ lecture.getIdLecture());
        System.out.println("Dia de la semana: "+ lecture.getDayOfWeek());
        System.out.println("hora: "+ lecture.getDesiredTimeSlot());
        System.out.println("Ticher: "+ lecture.getTeacher());
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::");

        return false;
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