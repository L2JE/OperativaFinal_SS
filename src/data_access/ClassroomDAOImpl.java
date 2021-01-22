package data_access;

import data_transfer.ClassroomDTO;

import java.util.ArrayList;

public class ClassroomDAOImpl implements ClassroomDAO {
    ArrayList<ClassroomDTO> cache = new ArrayList<>();
    static ClassroomDAOImpl instance;

    public static ClassroomDAOImpl getInstance(){
        if (instance == null)
            instance = new ClassroomDAOImpl();

        return instance;
    }

    private ClassroomDAOImpl() {

    }

    @Override
    public ClassroomDTO getRoomById(int idRoom) {
        for (ClassroomDTO dto : cache)
            if (dto.getIdRoom() == idRoom)
                return dto;

        return null;
    }

    @Override
    public ClassroomDTO getRoomByName(String roomFullName) {
        for (ClassroomDTO dto : cache)
            if (dto.getRoomName().equals(roomFullName))
                return dto;

        return null;
    }

    @Override
    public ArrayList<ClassroomDTO> getRoomsOnLocation(String location) {
        ArrayList<ClassroomDTO> dtos = new ArrayList<>();
        for (ClassroomDTO dto : cache)
            if (dto.getPabName().equals(location))
                dtos.add(dto);

        if (dtos.size() > 0)
            return dtos;

        return null;

    }

    @Override
    public ArrayList<String> getAllLocations() {
        ArrayList<String> locations = new ArrayList<>();
        for (ClassroomDTO dto : cache)
            if (!locations.contains(dto.getPabName()))
                locations.add(dto.getPabName());

        return locations;
    }

    @Override
    public ArrayList<ClassroomDTO> getAll() {

        return (ArrayList<ClassroomDTO>) cache.clone();
    }

    @Override
    public void setClassroom(ClassroomDTO classroom) {
        for (ClassroomDTO dto : cache)
            if (dto.getIdRoom() == classroom.getIdRoom())
                return;

        cache.add(classroom);
    }
}
