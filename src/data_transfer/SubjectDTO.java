package data_transfer;

import service.Showable;

import java.util.LinkedList;
import java.util.List;

public class SubjectDTO extends Showable {
    private int id = -1;
    private String name;

    public SubjectDTO(String subjectName) {
        this.name = subjectName;
    }

    public SubjectDTO(int id, String subjectName) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SubjectDTO)
            return super.equals(obj) || (((SubjectDTO)obj).id == this.id &&
                                         ((SubjectDTO)obj).name.equals(this.name));
        return false;
    }
}
