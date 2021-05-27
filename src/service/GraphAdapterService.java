package service;

import data_transfer.OccupationDTO;

import java.util.LinkedList;

public abstract class GraphAdapterService {
    protected ScheduleStrategy strategy;

    public GraphAdapterService(ScheduleStrategy strategy){
        this.strategy = strategy;
    }

    public void startSchedule(){
        this.strategy.execute();
    }
    public abstract LinkedList<OccupationDTO> getScheduleResult();
}
