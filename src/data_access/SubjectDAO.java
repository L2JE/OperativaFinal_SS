package data_access;

import data_transfer.CareerInstance;
import data_transfer.LectureDTO;
import data_transfer.SubjectDTO;

import java.util.List;

public interface SubjectDAO {
    ///////SUBJECTS
    SubjectDTO createSubject(SubjectDTO subject);

    List<SubjectDTO> getAllSubjects();

    SubjectDTO getSubjectByName(String subjectName);

    ///////LECTURES
    LectureDTO createLecture(LectureDTO lectureToAdd);

    List<LectureDTO> getLectures(int idSubject);

    int removeLecture(int lectureId);

    ///////CAREERS
    int createCInstance(int subjectId, CareerInstance careerToAdd);

    List<CareerInstance> getCareers(int idSubject);

    int removeCareer(int idSubject, CareerInstance careerInstance);
}
