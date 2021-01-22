package data_access;

import data_transfer.ClassroomDTO;

import java.util.ArrayList;

public interface ClassroomDAO {
    public abstract ClassroomDTO getRoomById(int idRoom);
    public abstract ClassroomDTO getRoomByName(String roomFullName);
    public abstract ArrayList<ClassroomDTO> getRoomsOnLocation(String location);
    public abstract ArrayList<String> getAllLocations();
    public abstract ArrayList<ClassroomDTO> getAll();

    public abstract void setClassroom(ClassroomDTO classroom);
}
