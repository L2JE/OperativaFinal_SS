package service.dataAdaptation;

import data_transfer.OccupationDTO;
import javafx.beans.value.ChangeListener;
import service.ScheduleStrategy;

import java.util.LinkedList;

public abstract class GraphAdapterService {
    Thread thread;

    protected boolean mustBeAsync = false;
    protected Callback readyStateMethod;
    protected ScheduleStrategy strategy;

    public GraphAdapterService(ScheduleStrategy strategy){
        this.strategy = strategy;
    }

    public void setDataReadyListener(Callback readyStateMethod){
        this.readyStateMethod = readyStateMethod;
        this.mustBeAsync = true;
    }

    public void setProgressListener(ChangeListener<Number> changeListener){
        strategy.addListener(changeListener);
        this.mustBeAsync = true;
    }

    public void startSchedule(){
        if(this.mustBeAsync){
            this.thread = new Thread(() -> {
                strategy.execute();
                if(readyStateMethod != null)
                    readyStateMethod.call();
            });
            this.thread.start();
        }
        else
            this.strategy.execute();



    }
    public abstract LinkedList<OccupationDTO> getScheduleResult();
}
