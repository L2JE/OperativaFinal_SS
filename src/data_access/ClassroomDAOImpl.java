package data_access;

import data_transfer.ClassroomDTO;

import java.util.ArrayList;

public class ClassroomDAOImpl implements ClassroomDAO {
    ArrayList<String> cachePabs = new ArrayList<>();
    ArrayList<ClassroomDTO> cacheRooms = new ArrayList<>();

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
        for (ClassroomDTO dto : cacheRooms)
            if (dto.getIdRoom() == idRoom) {
                try {
                    return (ClassroomDTO) dto.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

        return null;
    }

    @Override
    public ClassroomDTO getRoomByName(String roomFullName) {
        for (ClassroomDTO dto : cacheRooms)
            if (dto.getRoomName().equals(roomFullName)) {
                try {
                    return (ClassroomDTO) dto.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

        return null;
    }

    @Override
    public ArrayList<ClassroomDTO> getRoomsOnPab(String pabName) {
        //Assumes Pab exists
        ArrayList<ClassroomDTO> dtos = new ArrayList<>();
        for (ClassroomDTO dto : cacheRooms)
            if (dto.getPabName().equals(pabName)) {
                try {
                    dtos.add((ClassroomDTO) dto.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }finally {
                    dtos.clear();
                }
            }

        if (dtos.size() > 0)
            return dtos;

        return null;

    }

    @Override
    public ArrayList<String> getAllPabs() {
        ArrayList<String> pabs = new ArrayList<>();
        for (ClassroomDTO dto : cacheRooms){
            String pabName = dto.getPabName();
            if (!pabs.contains(pabName))
                pabs.add(pabName);
        }

        return pabs;
    }

    @Override
    public ArrayList<ClassroomDTO> getAll() {
        return (ArrayList<ClassroomDTO>) cacheRooms.clone();
    }

    @Override
    public ClassroomDTO createClassroom(ClassroomDTO classroom) {
        if (!cachePabs.contains(classroom.getPabName()))
            return null;

        for (ClassroomDTO dto : cacheRooms)
            if (dto.getPabName().equals(classroom.getPabName()) &&
                dto.getRoomName().equals(classroom.getRoomName()))
                return null;

        try {
            cacheRooms.add((ClassroomDTO) classroom.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ClassroomDTO deleteClassroom(int idRoom){
        int cantRooms = cacheRooms.size();
        for (int i = 0; i < cantRooms; i++){
            ClassroomDTO room = cacheRooms.get(i);
            if (room.getIdRoom() == idRoom){
                cacheRooms.remove(i);
                return room;
            }
        }
        return null;
    }


    @Override
    public ClassroomDTO createPab(String pabName) {
        if(cachePabs.contains(pabName))
            return null;

        cachePabs.add(pabName);
        return new ClassroomDTO(pabName,null);
    }

    @Override
    public ClassroomDTO deletePab(String pabName) {

    }

    @Override
    public ClassroomDTO getPabByName(String pabName) {
        for (String pab : cachePabs)
            if(pab.equals(pabName))
                return new ClassroomDTO(pabName, null);
        return null;
    }

    @Override
    public String toString() {
        String salida = "Lista de Aulas";
        for(ClassroomDTO c : cacheRooms)
            salida += "\n (id: " + c.getIdRoom()+"), "+ c.getPabName()+". "+c.getRoomName();

        return salida;
    }
}
