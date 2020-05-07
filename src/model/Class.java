package model;

import java.io.Serializable;

public class Class implements Serializable, Showable {
    private String classroom;
    private Integer duration;
    private Range range;

    public Class() {
    }

    @Override
    public String toString() {
        return classroom;
    }
}
