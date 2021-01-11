package controller;

import data_access.LectureDTO;
import data_access.OccupationDTO;

import java.util.ArrayList;

/**
 * TODO: clase que lleva logica de negocio de la aplicacion!!!!!!!
 */
public class DualColorGraphScher implements ScheduleStrategy{
    private ArrayList<LectureDTO> lectures;
    private ArrayList<OccupationDTO> roomsOccupation;

    public DualColorGraphScher(ArrayList<LectureDTO> lectures, ArrayList<OccupationDTO> roomsOccupation) {
        this.lectures = lectures;
        this.roomsOccupation = roomsOccupation;
    }


    @Override
    public void execute() {
        /**
         * IMPLEMENT
         */
    }

    public ArrayList<OccupationDTO> getSchedule(){
        ArrayList<OccupationDTO> res = new ArrayList<OccupationDTO>();
        /**
         * IMPLEMENT
         */
        return res;
    }
}
