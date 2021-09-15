package data_access;

import data_transfer.ClassroomDTO;

import java.util.List;

public interface ClassroomDAO {
    ClassroomDTO createPab(String pabName);
    ClassroomDTO createClassroom(ClassroomDTO classroom);

    List<ClassroomDTO> getAllRooms();
    List<ClassroomDTO> getAllPabs();

    List<ClassroomDTO> getRoomsOnPab(int idPab);

    int removeClassroom(int idRoom);
    int removePab(int idPab);
}
