package service;

import view.observableLogic.ObservableProgressProperty;

public abstract class ScheduleStrategy extends ObservableProgressProperty {
    public abstract void execute();
}
