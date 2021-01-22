package data_transfer;

public class OccupationDTO {
    private String day;
    private int hour;
    private int idRoom;
    private int idLecture;

    public OccupationDTO(String day, int hour, int idRoom, int idLecture) {
        this.day = day;
        this.hour = hour;
        this.idRoom = idRoom;
        this.idLecture = idLecture;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }
}
