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
    public static CareerDTO careerValidator(ComboBox yearsCBCareer, TextField nameFieldCareer, ComboBox startTimeCBCareer, ComboBox endTimeCBCareer) {

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

        //Verifica que la carrera no existe
        if(CareerCompDAOImpl.getInstance().getCareerByName(careerName) != null)
            return null;

        return new CareerDTO(careerName, duration, chosenStartTime, chosenEndTime);
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

    public static ClassroomDTO pabValidator(TextField pab){
        String desiredPabName = pab.getText();
        if(desiredPabName.equals(""))
            return null;
        desiredPabName = desiredPabName.substring(0,1).toUpperCase() +
                         desiredPabName.substring(1);

        ClassroomDAOImpl dao = ClassroomDAOImpl.getInstance();
        if (dao.getPabByName(desiredPabName) != null)
            return null;

        return dao.createPab(desiredPabName);

    }

    /*
    public void initCareerDAO(){
        if(careerAccess==null)
            careerAccess
    }
     */

}
