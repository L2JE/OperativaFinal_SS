package data_transfer;

import service.Showable;

public class LectureDTO extends Showable {
    private int idSubject;

    private int idLecture = -1;

    private int startTime = -1;

    private DayOfWeek dayOfWeek = null;
    private int roomId = -1;
    private String teacher = null;

    public LectureDTO(int idSubject) {
        super();
        this.idSubject = idSubject;
    }

    public LectureDTO(int idSubject, int idLecture, int startTime, DayOfWeek dayOfWeek, int roomId, String teacher) {
        super();
        this.idSubject = idSubject;
        this.idLecture = idLecture;
        this.startTime = startTime;
        this.dayOfWeek = dayOfWeek;
        this.roomId = roomId;
        this.teacher = teacher;
    }



    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
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
        this.teacher = (teacher != null && !teacher.equals(""))? teacher : null;
    }


    @Override
    public String toString() {
        String out = "";

        if (dayOfWeek != null && startTime > -1)
            out += dayOfWeek+ ", "+ startTime + "hs. ";
        else if (dayOfWeek != null)
                out += dayOfWeek + ". ";
            else if (startTime > -1)
                    out += startTime + "hs. ";


        if (roomId > -1){
            out += "idAula_"+roomId;
            if (teacher != null)
                out += ", ";
        }
        if (teacher != null)
            out += "Doc.: "+ teacher;

        return out;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;

        if(obj instanceof LectureDTO){
            LectureDTO other = (LectureDTO)obj;
            boolean result = (other.idSubject == this.idSubject &&
                    other.idLecture == this.idLecture &&
                    other.startTime == this.startTime &&
                    other.roomId == this.roomId);

            if (teacher != null)
                result = result && other.teacher.equals(this.getTeacher());

            if (other.dayOfWeek != null && this.dayOfWeek != null)
                return result && other.dayOfWeek.ordinal() == this.dayOfWeek.ordinal();
            else if (other.dayOfWeek == null && this.dayOfWeek == null)
                    return result;
        }
        return false;
    }
}
