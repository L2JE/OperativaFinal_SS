package view;

import data_access.*;
import data_transfer.ClassroomDTO;
import data_transfer.DayOfWeek;
import data_transfer.LectureDTO;
import data_transfer.SubjectDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.Showable;
import java.util.ArrayList;
import java.util.List;

public class MateriaFixLesson extends SendableFilling {
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
    private int subjectId = -1;
    private LectureDTO lectureToAdd;

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

                List<ClassroomDTO> rooms = dao.getRoomsOnPab(((ClassroomDTO)newValue).getIdPab());
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

    @Override
    public void setParam(Object param) {
       subjectId = ((SubjectDTO)param).getIdSubject();
    }

    @Override
    protected void sendData() {
        home.addValidLectureToSubject(lectureToAdd);
    }

    @Override
    protected void showErrorMsj() {
        errorMsjLabel.setVisible(true);
    }

    @Override
    protected boolean validateUserInput() {
        //(pab, room, day, time, teacher)
        //(pab, room, day, time)
        //(day, time, teacher)
        //(day, time)
        //(teacher) ... then all lessons available are assigned to the same teacher
        //(day)
        //(Other cases are prohibited)
        this.lectureToAdd = null;
        ClassroomDTO room = (ClassroomDTO)roomCB.getValue();
        String date = dayCB.getValue();
        int lectureStartAt = -1;
        String teacher = teacherText.getText();

        if(startTimeCB.getValue() != null && !startTimeCB.getValue().equals("")) {
            String tmp = ((String) startTimeCB.getValue());
            lectureStartAt = Integer.parseInt(tmp.substring(0, tmp.indexOf(":")));
        }

        LectureDTO lectureToAdd = new LectureDTO(subjectId);

        if(room != null)                        lectureToAdd.setRoomId(room.getIdRoom());
        if(date != null && !date.equals(""))    lectureToAdd.setDayOfWeek(DayOfWeek.valueOf(date));
        if(teacher != null && !teacher.equals(""))                     lectureToAdd.setTeacher(teacher);
        if(lectureStartAt > -1)                lectureToAdd.setStartTime(lectureStartAt);
/*
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("Materia: "+ lectureToAdd.getIdSubject());
        System.out.println("Clase: "+ lectureToAdd.getIdLecture());
        System.out.println("Dia de la semana: "+ lectureToAdd.getDayOfWeek());
        System.out.println("hora: "+ lectureToAdd.getDesiredTimeSlot());
        System.out.println("Ticher: "+ lectureToAdd.getTeacher());
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::");
*/
        SubjectDAO dao = new SubjectSQLiteDAO();
        if(date != null && !date.equals("") || (teacher != null && !teacher.equals("")))
            this.lectureToAdd = dao.createLecture(lectureToAdd);

        if (this.lectureToAdd != null)
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