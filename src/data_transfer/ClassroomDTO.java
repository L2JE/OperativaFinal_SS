package data_transfer;

import service.Showable;

public class ClassroomDTO extends Showable {
    private int idRoom = -1;

    private int idPab = -1;
    private String pabName = null;
    private String roomName = null;

    public ClassroomDTO(){}

    public ClassroomDTO(int idPab, String pabName){
        this.idPab = idPab;
        this.pabName = pabName;
    }

    public ClassroomDTO(int idPab){
        this.idPab = idPab;
    }

    @Deprecated
    public ClassroomDTO(String pabName, String roomName) {
        super();
        this.pabName = pabName;
        this.roomName = roomName;
    }

    @Deprecated
    public ClassroomDTO(int idRoom, String pabName, String roomName) {
        super();
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

    public int getIdPab() {
        return idPab;
    }

    public void setIdPab(int idPab) {
        this.idPab = idPab;
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

        return new ClassroomDTO(idRoom,pabName,roomName);
    }

    @Override
    public String toString() {
        if (roomName == null)
            return pabName;
        else
            if(pabName == null)
                return roomName;

        return "Pabellon: " + pabName + " Aula: "+ roomName + " (id: "+ idRoom +")";
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj))
            return true;

        if(obj instanceof ClassroomDTO){
            boolean equals = ((ClassroomDTO)obj).idPab == idPab && ((ClassroomDTO)obj).idRoom == idRoom;

            if (idPab > -1 && ((ClassroomDTO)obj).pabName != null)
                equals = equals && ((ClassroomDTO)obj).pabName.equals(pabName);

            if (idRoom > -1)
                equals = equals && ((ClassroomDTO)obj).roomName.equals(roomName);

            return equals;
        }
        return false;
    }
}
