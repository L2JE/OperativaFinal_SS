package controller;

import data_access.*;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * TODO: initialize careerAccess object!
 */

public class UIDataValidator {
    private static CareerCompDAO careerAccess;
    public static CareerDTO careerValidator(ComboBox yearsCBCareer, TextField nameFieldCareer, ComboBox startTimeCBCareer, ComboBox endTimeCBCareer) {
        int id = 0;
        int duration = -1;
        String careerName = "";
        int chosenStartTime = -1;
        int chosenEndTime = -1;

        ///validate duration
        if(yearsCBCareer.getValue() != null)
            duration = Integer.parseInt((String) yearsCBCareer.getValue());
        else
            return null;

        ///Validate careerName
        if(nameFieldCareer.getText() != null){
            careerName = nameFieldCareer.getText();
            if(careerName.length() < 10)///TIENE QUE SER UNA PALABRA MINIMO
                return null;
        }else
            return null;

        ///Validate Band
        if(startTimeCBCareer.getValue() != null){
            String tmp = ((String)startTimeCBCareer.getValue());
            chosenStartTime = Integer.parseInt(tmp.substring(0,tmp.indexOf(":")));
        }else
            return null;

        if(endTimeCBCareer.getValue() != null){
            String tmp = ((String)endTimeCBCareer.getValue());
            chosenEndTime = Integer.parseInt(tmp.substring(0,tmp.indexOf(":")));
        }else
            return null;

        if(chosenEndTime <= chosenStartTime)
            return null;
        /*
        Verifica que la carrera no existe
        if(careerAccess.getSubjects(id) != null)
            return null;
        */

        return new CareerDTO(id, careerName, duration, chosenStartTime, chosenEndTime);
    }

    public static ClassroomDTO roomValidator(ComboBox<String> pabCBRoom, ComboBox<String> roomCBRoom) {
        int id = 0;
        String location = pabCBRoom.getValue();
        String room = roomCBRoom.getValue();

        if((location.length() < 4)||(room.length() < 4))
            return null;

        return new ClassroomDTO(id, location, room);
    }

    public static ArrayList<String> locationValidator(ComboBox<String> pabCBRoom){
        String enteredText = pabCBRoom.getValue();

        if((enteredText != null) && (enteredText.length() > 3)){

        ArrayList<String> availableRooms = new ArrayList<>();
        /*
        ClassroomDAO classroomDAO = null;
        ArrayList<ClassroomDTO> roomsOnPab = classroomDAO.getRoomsOnLocation(enteredText);
        if(roomsOnPab != null) {
            for (ClassroomDTO dto : roomsOnPab)
                availableRooms.add(dto.getRoomName());
        }else{
            classroomDAO.setLocation(enteredText);
            pabCBRoom.getItems().add(enteredText);
        }
*/
            availableRooms.add("1");
            availableRooms.add("2");
            availableRooms.add("3");
            availableRooms.add("4");

            return availableRooms;

        }
        return null;
    }

    /*
    public void initCareerDAO(){
        if(careerAccess==null)
            careerAccess
    }
     */

}
