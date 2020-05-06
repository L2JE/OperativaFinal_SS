package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable, Showable{
    private String name;
    private String commission;
    private String professor;
    private List<Year> years;
    private List<Class> classes;

    public Course(){
        years = new ArrayList<Year>();
        classes = new ArrayList<Class>();
    }

    public Course(String name, String commission){
        this.name = name;
        this.commission = commission;
    }

    @Override
    public String toString() {
        return name +" [" +commission + "]";
    }
}
