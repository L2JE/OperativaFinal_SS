package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersistenceFile implements Serializable {
    private List<Year> careers;
    private List<String> classrooms;
    private List<Course> courses;

    public PersistenceFile(){
        careers = new ArrayList<Year>();
        classrooms = new ArrayList<String>();
        courses = new ArrayList<Course>();
    }
}
