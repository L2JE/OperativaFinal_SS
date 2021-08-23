package data_access;

import data_transfer.ClassroomDTO;

import java.util.ArrayList;
import java.util.List;

public class ClassroomDAOImpl implements ClassroomDAO {
    private final ArrayList<String> cachePabs = new ArrayList<>();
    private final ArrayList<ClassroomDTO> cacheRooms = new ArrayList<>();
    private int lastClassroomId = -1;

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
    public ClassroomDTO getRoomByName(String pabName, String roomName) {

        for (ClassroomDTO dto : cacheRooms)
            if (dto.getPabName().equals(pabName) &&
                dto.getRoomName().equals(roomName)) {
                try {
                    return (ClassroomDTO) dto.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

        return null;
    }

    @Override
    public List<ClassroomDTO> getRoomsOnPab(int pabName) {
        //Assumes Pab exists
        ArrayList<ClassroomDTO> dtos = new ArrayList<>();
        for (ClassroomDTO dto : cacheRooms)
            if (dto.getPabName().equals(pabName)) {
                try {
                    dtos.add((ClassroomDTO) dto.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

        return dtos;
    }

    @Override
    public ArrayList<ClassroomDTO> getAllRooms() {
        return (ArrayList<ClassroomDTO>) this.cacheRooms.clone();
    }

    @Override
    public List<ClassroomDTO> getAllPabs() {
        ArrayList<ClassroomDTO> pabs = new ArrayList<>();
        for (String pab : cachePabs) {
            pabs.add(new ClassroomDTO(pab, null));
        }

        return pabs;
    }

    @Override
    public ClassroomDTO createClassroom(ClassroomDTO classroom) {
        if (!cachePabs.contains(classroom.getPabName()))
            return null;

        for (ClassroomDTO dto : cacheRooms)
            if (dto.getPabName().equals(classroom.getPabName()) &&
                dto.getRoomName().equals(classroom.getRoomName()))
                return null;

        this.lastClassroomId++;
        classroom.setIdRoom(lastClassroomId);
        try {
            cacheRooms.add((ClassroomDTO) classroom.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


        return classroom;
    }

    @Override
    public int removeClassroom(int idRoom){
        int cantRooms = cacheRooms.size();
        for (int i = 0; i < cantRooms; i++){
            ClassroomDTO room = cacheRooms.get(i);
            if (room.getIdRoom() == idRoom){
                cacheRooms.remove(i);
                return room.getIdRoom();
            }
        }
        return -1;
    }


    @Override
    public ClassroomDTO createPab(String pabName) {
        if(cachePabs.contains(pabName))
            return null;

        cachePabs.add(pabName);
        return new ClassroomDTO(pabName,null);
    }

    @Override
    public int removePab(int idPab) {
        int pabIndex = cachePabs.indexOf(idPab);
        if(pabIndex < 0)
            return -1;

        ClassroomDTO removed = new ClassroomDTO(cachePabs.get(pabIndex), null);
        cachePabs.remove(pabIndex);

        for (int roomIndex = cacheRooms.size() - 1; roomIndex > -1 ; roomIndex--)
            if(cacheRooms.get(roomIndex).getPabName().equals(idPab))
                cacheRooms.remove(roomIndex);

        return removed.getIdPab();
    }

    @Override
    public ClassroomDTO getPabByName(String pabName) {
        if(cachePabs.contains(pabName))
            return new ClassroomDTO(pabName, null);
        return null;
    }

    @Override
    public String toString() {
        String salida = "Lista de Pabellones";
        for(String pab : cachePabs) {
            salida += "\n "+ pab;
        }
        salida += "\n:::::::::::::::::::::::::::::\nLista de Aulas";
        for(ClassroomDTO c : cacheRooms) {
            salida += "\n (id: " + c.getIdRoom()+"), "+ c.getPabName()+". "+c.getRoomName();
        }

        return salida;
    }
}
