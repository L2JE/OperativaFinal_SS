package data_access;

import model.Showable;

public class ClassroomDTO implements Showable {
    private int idRoom;
    private String pabName;

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getPabName() {
        return pabName;
    }

    public void setPabName(String pabName) {
        this.pabName = pabName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    private String roomName;

    public ClassroomDTO(int idRoom, String pabName, String roomName) {
        this.idRoom = idRoom;
        this.pabName = pabName;
        this.roomName = roomName;
    }


    @Override
    public String toString() {
        return "Pabellon: " + pabName + " Aula: "+ roomName;
    }
}
