package data_access;

import data_transfer.CareerInstance;
import data_transfer.LectureDTO;
import data_transfer.SubjectDTO;

import java.util.LinkedList;
import java.util.List;

public class SubjectSQLiteDAO implements SubjectDAO{

    ///////SUBJECTS
    @Override
    public SubjectDTO createSubject(SubjectDTO subject) {
        return null;
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<SubjectDTO> subjects = new LinkedList<>();

        return subjects;
    }

    @Override
    public SubjectDTO getSubjectByName(String subjectName) {
        return null;
    }

    ///////LECTURES
    @Override
    public LectureDTO createLecture(int subjectId, LectureDTO lectureToAdd) {
        return null;
    }

    @Override
    public List<LectureDTO> getLectures(int idSubject) {
        List<LectureDTO> lectures = new LinkedList<>();

        return lectures;
    }

    @Override
    public int removeLecture(LectureDTO lecture) {
        return 0;
    }

    ///////CAREERS
    @Override
    public CareerInstance createCInstance(int subjectId, CareerInstance careerToAdd) {
        return null;
    }

    @Override
    public List<CareerInstance> getCareers(int idSubject) {
        List<CareerInstance> careers = new LinkedList<>();

        return careers;
    }

    @Override
    public int removeCareer(int idSubject, CareerInstance careerInstance) {
        return 0;
    }
}
