package model;

import java.io.Serializable;

public class Range implements Serializable, Showable {
    private String day;//por que el dia?? No es el mismo para todos los dias?
    private String start;
    private Double duration;

    public Range(){
    }

    @Override
    public String toString() {
        return "hora: " + start + " (" + duration + "horas)";
    }
}
