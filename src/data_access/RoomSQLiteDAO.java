package data_access;

import data_transfer.ClassroomDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RoomSQLiteDAO extends SQLiteDAO implements ClassroomDAO{

    private static final String createPabStr = "insert into pabellon (pab_name) values (?);";
    private static final String createRoomStr = "insert into aula (pab, room) values (?,?);";
    private static final String readRoomsInPabStr = "select a.id id, a.room name\n" +
            "from aula a join pabellon p on a.pab = p.id\n" +
            "where p.id=1\n" +
            "order by id;";

    public RoomSQLiteDAO(){
        super();
    }

    public RoomSQLiteDAO(String urlToDB){
        super(urlToDB);
    }

    @Override
    public ClassroomDTO createClassroom(ClassroomDTO classroom) {
        establishConnection();

        ClassroomDTO classroomToReturn = null;

        //(idPab, roomName)
        try (PreparedStatement createSt = conn.prepareStatement(createRoomStr, Statement.RETURN_GENERATED_KEYS)) {
            int idRoom = -1;
            conn.setAutoCommit(false);

            createSt.setInt(1, classroom.getIdPab());
            createSt.setString(2, classroom.getRoomName());

            createSt.executeUpdate();

            ResultSet res = createSt.getGeneratedKeys();
            if (res != null && res.next())
                idRoom =  res.getInt(1);

            conn.commit();

            classroomToReturn = classroom;
            classroomToReturn.setIdRoom(idRoom);
        }catch (SQLException e) {
            try {
                System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                System.err.print("ERROR AL CREAR EL REGISTRO: ");
                if (conn != null) {
                    System.err.println(e.getMessage());
                    System.err.println("INTENTADO HACER ROLLBACK");
                    System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                    conn.rollback();
                }
            } catch (SQLException excep) {
                System.err.print("ERROR AL INTENTAR HACER ROLLBACK");
                excep.printStackTrace();
            }

        }finally {
            closeConnection();
        }

        return classroomToReturn;
    }

    @Override
    public ClassroomDTO deleteClassroom(int idRoom) {
        return null;
    }

    @Override
    public ArrayList<ClassroomDTO> getAllRooms() {
        return null;
    }

    @Override
    public ClassroomDTO getRoomById(int idRoom) {
        return null;
    }

    @Override
    public ClassroomDTO getRoomByName(String pabName, String roomName) {
        return null;
    }

    @Override
    public ClassroomDTO createPab(String pabName) {
        establishConnection();

        ClassroomDTO pabToReturn = null;

        //(id, pab_name)
        try (PreparedStatement createSt = conn.prepareStatement(createPabStr, Statement.RETURN_GENERATED_KEYS)) {
            int idPab = -1;
            conn.setAutoCommit(false);

            createSt.setString(1, pabName);

            createSt.executeUpdate();

            ResultSet res = createSt.getGeneratedKeys();
            if (res != null && res.next())
                idPab =  res.getInt(1);

            conn.commit();

            pabToReturn = new ClassroomDTO(idPab, pabName);
        }catch (SQLException e) {
            try {
                System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                System.err.print("ERROR AL CREAR EL REGISTRO: ");
                if (conn != null) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    System.err.println("INTENTADO HACER ROLLBACK");
                    System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                    conn.rollback();
                }
            } catch (SQLException excep) {
                System.err.print("ERROR AL INTENTAR HACER ROLLBACK");
                excep.printStackTrace();
            }

        }finally {
            closeConnection();
        }

        return pabToReturn;
    }

    @Override
    public ClassroomDTO deletePab(String pabName) {
        return null;
    }

    @Override
    public List<ClassroomDTO> getAllPabs() {
        final String readAllStr = "select id,pab_name from pabellon order by id";
        List<ClassroomDTO> allPabsList = new LinkedList<>();

        establishConnection();

        try (PreparedStatement readSt = conn.prepareStatement(readAllStr)) {
            ResultSet res = readSt.executeQuery();

            if (res != null)
                while (res.next())
                    allPabsList.add(new ClassroomDTO(res.getInt("id"), capitalizeWords(res.getString("pab_name"))));

        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
            allPabsList = null;
        } finally {
            closeConnection();
        }

        return allPabsList;
    }

    @Override
    public List<ClassroomDTO> getRoomsOnPab(int idPab) {
        List<ClassroomDTO> allPabsList = new LinkedList<>();

        establishConnection();

        try (PreparedStatement readSt = conn.prepareStatement(readRoomsInPabStr)) {
            ResultSet res = readSt.executeQuery();

            if (res != null)
                while (res.next()){
                    ClassroomDTO dto = new ClassroomDTO();
                    dto.setIdPab(idPab);
                    dto.setIdRoom(res.getInt("id"));
                    dto.setRoomName(capitalizeWords(res.getString("name")));
                    allPabsList.add(dto);
                }

        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
            allPabsList = null;
        } finally {
            closeConnection();
        }

        return allPabsList;
    }

    @Override
    public ClassroomDTO getPabByName(String pabName) {
        return null;
    }
}
