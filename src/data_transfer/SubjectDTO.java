package data_transfer;

import service.Showable;

import java.util.LinkedList;
import java.util.List;

public class SubjectDTO extends Showable {
    private int id = -1;
    private String name;
    private List<CareerInstance> careers = new LinkedList<>();

    public SubjectDTO(String subjectName) {
        this.name = subjectName;
    }

    public int getIdSubject() {
        return id;
    }

    public void setIdSubject(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addCareerInstance(CareerInstance careerInstance) {
        careers.add(careerInstance);
    }

    public void setCareers(List<CareerInstance> careers){
        this.careers = careers;
    }

    public List<CareerInstance> getCareers() {
        return careers;
    }

    public void removeCareerInstance(CareerInstance careerInstance) {
        careers.remove(careerInstance);
    }
}
