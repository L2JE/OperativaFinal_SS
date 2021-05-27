package view.obserbableLogic;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ObservableProgressProperty {
    DoubleProperty progress = new SimpleDoubleProperty(0.0);

    public ObservableProgressProperty(){

    }

    protected double updateProgress(double addedValue){

        return progress.get();
    }

    protected void setIncrement(double increment){

    }

    void addListener(ChangeListener<Number> changeListener){
        progress.addListener(changeListener);
    }

    void removeListener(ChangeListener<Number> changeListener){
        progress.removeListener(changeListener);
    }

}
