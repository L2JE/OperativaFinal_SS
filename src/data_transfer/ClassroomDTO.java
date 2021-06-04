package data_transfer;

import service.Showable;

public class ClassroomDTO implements Showable {
    private int idRoom = -1;
    private String pabName;
    private String roomName;

    public ClassroomDTO(String pabName, String roomName) {
        this.pabName = pabName;
        this.roomName = roomName;
    }

    public ClassroomDTO(int idRoom, String pabName, String roomName) {
        this.idRoom = idRoom;
        this.pabName = pabName;
        this.roomName = roomName;
    }

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        ClassroomDTO copy = new ClassroomDTO(idRoom,pabName,roomName);

        return copy;
    }



    @Override
    public String toString() {
        if (roomName == null)
            return pabName;
        return "Pabellon: " + pabName + " Aula: "+ roomName;
    }
}
