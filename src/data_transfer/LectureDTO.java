package data_transfer;

import service.Showable;

public class LectureDTO extends Showable {
    private int idLecture;
    private int idCareers[];
    private String idSubject;
    private String teacher;

    public LectureDTO(int idLecture, String idSubject, String teacher) {
        super();
        this.idCareers = new int[]{};
        this.idLecture = idLecture;
        this.idSubject = idSubject;
        this.teacher = teacher;
    }

    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    @Override
    public String toString() {
        return idLecture + ", " + idSubject +", "+ teacher;
    }
}
