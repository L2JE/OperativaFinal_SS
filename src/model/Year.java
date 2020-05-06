package model;

import java.io.Serializable;

public class Year implements Serializable, Showable {
    private String career;
    private Integer year;

    public Year(){
    }


    @Override
    public String toString() {
        return career;
    }
}
