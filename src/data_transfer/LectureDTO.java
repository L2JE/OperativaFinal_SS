package data_transfer;

import service.Showable;

public class LectureDTO extends Showable {
    private int idSubject;
    private int idLecture = -1;

    private int desiredTimeSlot = -1;
    private DayOfWeek dayOfWeek = null;
    private int roomId = -1;
    private String teacher = null;

    public LectureDTO(int idSubject) {
        super();
        this.idSubject = idSubject;
    }

    public int getDesiredTimeSlot() {
        return desiredTimeSlot;
    }

    public void setDesiredTimeSlot(int desiredTimeSlot) {
        this.desiredTimeSlot = desiredTimeSlot;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
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
