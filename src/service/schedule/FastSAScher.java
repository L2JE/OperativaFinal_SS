package service.schedule;

import data_transfer.OccupationDTO;
import service.schedule.ScheduleStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * TODO: clase que lleva logica de negocio de la aplicacion!!!!!!!
 * Scheduler based on the following papper:
 * A fast simulated annealing algorithm for the examination timetabling problem, Nuno Leite, Fernando Melicio, Agostinho C Rosa
 */
public class FastSAScher extends ScheduleStrategy {

    /**
     * 6 days * (21:00 - 8:00) hours available = 78timeslots
     */
    private final int timeSlots = 78;

    /**
     * It will be given in the constructor
     */
    private final int numOfExams = 1000;

    /**
     * Timetable matrix[row][col]:
     * row index is the lecture
     * col index is the timeSlot
     * timetable[lecture][timeslot] gives the room index in which is assigned to
     */
    private int[][] timetable = new int[numOfExams][timeSlots];

    private Queue<Integer> lectures = new PriorityQueue<>(Comparator.comparingInt(this::availablePeriods));

    /**
     * computes the available periods according to hard constraints
     * @param lectureId
     * @return the number of available periods for the given lecture
     */
    private int availablePeriods(Integer lectureId) {
        return 0;
    }

    public FastSAScher() {
    }

    /**
     * Uses a variant of the saturation degree heuristic
     */
    public void generateInitialSolution(){

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
