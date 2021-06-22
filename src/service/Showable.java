package service;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Showable {
    private BooleanProperty deleted = new SimpleBooleanProperty(false);

    public BooleanProperty isDeleted(){
        return this.deleted;
    }

    public void setDeleted(boolean mustBeDeleted){
        this.deleted.set(mustBeDeleted);
    }

    abstract public String toString();

}
