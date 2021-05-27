package view.obserbableLogic;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ObservableProgressProperty{
    DoubleProperty progress = new SimpleDoubleProperty(0.0);
    Lock lock = new ReentrantLock();
    double increment = 0.1;

    public ObservableProgressProperty(){
        System.out.println("AsyncObs constructor");
    }

    protected void updateProgress(){
        lock.lock();
        progress.set(increment + progress.get());
        lock.unlock();

    }

    protected void setIncrement(double increment){
        this.increment = increment;
    }

    public void addListener(ChangeListener<Number> changeListener){
        progress.addListener(changeListener);
    }
}
