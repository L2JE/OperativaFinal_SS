package data_access;

import data_transfer.ClassroomDTO;

import java.util.List;

public interface ClassroomDAO {
    ClassroomDTO getRoomById(int idRoom);
    ClassroomDTO getRoomByName(String pabName, String roomName);
    List<ClassroomDTO> getRoomsOnPab(int idPab);

    List<ClassroomDTO> getAllRooms();
    List<ClassroomDTO> getAllPabs();

    ClassroomDTO createClassroom(ClassroomDTO classroom);
    int removeClassroom(int idRoom);

    ClassroomDTO createPab(String pabName);
    int removePab(int idPab);


    ClassroomDTO getPabByName(String pabName);
}
