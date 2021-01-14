package controller;

import data_access.CareerCompDAO;
import data_access.CareerCompDTO;
import data_access.CareerDTO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

        CareerDTO validCareer = new CareerDTO(id, careerName, duration, chosenStartTime, chosenEndTime);


        return validCareer;
    }
    /*
    public void initCareerDAO(){
        if(careerAccess==null)
            careerAccess
    }
     */

}
