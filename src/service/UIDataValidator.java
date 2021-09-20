package service;

import data_access.*;
import data_transfer.CareerDTO;
import data_transfer.ClassroomDTO;
import data_transfer.SubjectDTO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
        CareerDAO dao = new CareerSQLiteDAO();
        if(dao.getCareerByName(careerName) != null)
            return null;

        return new CareerDTO(careerName, duration, chosenStartTime, chosenEndTime);
    }

    public static ClassroomDTO roomValidator(TextField newRoomField, Showable selectedPab) {
        String desiredRoomName = newRoomField.getText();

        if(desiredRoomName.equals("") || selectedPab == null)
            return null;

        ClassroomDTO newRoomCandidate = new ClassroomDTO(((ClassroomDTO)selectedPab).getIdPab());

        newRoomCandidate.setRoomName(desiredRoomName);

        return newRoomCandidate;
    }

    public static boolean isValidPabName(TextField pab){
        //TODO: Use regex to validate pabname
        String desiredPabName = pab.getText();
        boolean isValid = true;

        if(desiredPabName == null || desiredPabName.equals(""))
            isValid = false;

        desiredPabName = desiredPabName.substring(0,1).toUpperCase() +
                         desiredPabName.substring(1);

        pab.setText(desiredPabName);

        return isValid;

    }

    public static SubjectDTO subjectValidator(TextField newSubjectField) {
        String subjectName = newSubjectField.getText();

        if(subjectName == null && subjectName.length() < 5)
            return null;

        SubjectDAO dao = new SubjectSQLiteDAO();
        if(dao.getSubjectByName(subjectName) != null)
            return null;

        return new SubjectDTO(subjectName);
    }
}
