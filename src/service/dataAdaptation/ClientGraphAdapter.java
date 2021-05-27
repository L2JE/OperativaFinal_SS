package service.dataAdaptation;

import data_transfer.CareerDTO;
import data_transfer.ClassroomDTO;
import data_transfer.LectureDTO;
import data_transfer.OccupationDTO;
import service.ScheduleStrategy;

import java.util.ArrayList;
import java.util.LinkedList;

public class ClientGraphAdapter extends GraphAdapterService {

    public ClientGraphAdapter(ScheduleStrategy strategy,
                              ArrayList<CareerDTO> careers,
                              ArrayList<ClassroomDTO> classrooms,
                              ArrayList<LectureDTO> lectures) {
        super(strategy);

    }

    @Override
    public LinkedList<OccupationDTO> getScheduleResult() {
        LinkedList<OccupationDTO> classroomOccupations = new LinkedList<>();

        return classroomOccupations;
    }


}
