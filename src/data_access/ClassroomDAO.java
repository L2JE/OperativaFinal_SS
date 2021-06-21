package data_access;

import data_transfer.ClassroomDTO;

import java.util.ArrayList;

public interface ClassroomDAO {
    public abstract ClassroomDTO getRoomById(int idRoom);
    public abstract ClassroomDTO getRoomByName(String pabName, String roomName);
    public abstract ArrayList<ClassroomDTO> getRoomsOnPab(String location);

    public abstract ArrayList<ClassroomDTO> getAllRooms();
    public abstract ArrayList<ClassroomDTO> getAllPabs();

    public abstract ClassroomDTO createClassroom(ClassroomDTO classroom);
    public ClassroomDTO deleteClassroom(int idRoom);

    public ClassroomDTO createPab(String pabName);
    public ClassroomDTO deletePab(String pabName);


    ClassroomDTO getPabByName(String pabName);
}
