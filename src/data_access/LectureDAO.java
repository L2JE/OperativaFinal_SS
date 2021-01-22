package data_access;

import data_transfer.LectureDTO;

import java.util.ArrayList;

public interface LectureDAO {
    public abstract void getLecture(int idLecture);
    public abstract void setLecture(LectureDTO lecture);
    public abstract ArrayList<LectureDTO> getTeacherLectures(String teacher);
}
