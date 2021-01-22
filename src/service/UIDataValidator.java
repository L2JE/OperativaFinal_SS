package service;

import data_access.*;
import data_transfer.CareerDTO;
import data_transfer.ClassroomDTO;
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

        if((location.length() < 4)||(room.length() < 1))
            return null;

        ClassroomDTO addedClassroom = new ClassroomDTO(id, location, room);
        /*
        ClassroomDAO classroomDAO = null;
        if(classroomDAO.getRoomByName(location+room) == null)// Si el aula no existe se agrega
            classroomDAO.setClassroom(addedClassroom);
         */


        return addedClassroom;
    }

    public static ArrayList<String> locationValidator(ComboBox<String> pabCBRoom){
        String enteredText = pabCBRoom.getValue();

        if((enteredText != null) && (enteredText.length() > 3)){

        ArrayList<String> availableRooms = new ArrayList<>();
        /*
        ClassroomDAO classroomDAO = null;
        ArrayList<ClassroomDTO> roomsOnPab = classroomDAO.getRoomsOnLocation(enteredText);
        if(roomsOnPab != null) { //Si el pabellon existe obtenemos las aulas
            for (ClassroomDTO dto : roomsOnPab)
                availableRooms.add(dto.getRoomName());
        }else{ //Si el pabellon no existe agregamos el nuevo pab a la vista y al modelo
            classroomDAO.setLocation(enteredText);
            pabCBRoom.getItems().add(enteredText);
        }
*/
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
