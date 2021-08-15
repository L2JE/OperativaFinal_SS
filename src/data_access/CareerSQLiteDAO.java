package data_access;

import data_transfer.CareerDTO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CareerSQLiteDAO implements CareerDAO{
    private static final String urlToDB = "jdbc:sqlite:.data.dt";
    private static Connection conn = null;

    private static final String createStr = "insert into carrera(name, duration, h_inic, h_fin) " +
                                            "values (?,?,?,?)";

    private static final String readByNameStr = "select id,duration,h_inic,h_fin from carrera where name=?";

    private static final String readByIdStr = "select name,duration,h_inic,h_fin from carrera where id=?";

    private static final String deleteStr = "delete from carrera where id=?";

    public CareerSQLiteDAO(){
        establishConnection();
    }

    private void establishConnection(){
        try {
            if(conn == null || conn.isClosed()){
                try {
                    conn = DriverManager.getConnection(urlToDB);
                } catch (SQLException exception) {
                    System.err.println("ERROR AL CONECTAR CON LA BASE DE DATOS");
                    exception.printStackTrace();
                }
            }
        } catch (SQLException exception) {
            System.err.println("ERROR AL VERIFICAR SI LA CONEXION ESTA CERRADA.");
        }
    }

    private void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException exception) {
                System.err.println("ERROR AL INTENTAR DESCONECTAR DE LA BASE DE DATOS.");
                System.err.println(exception.getMessage());
            }
        }
    }

    @Override
    public List<CareerDTO> getAllCareers() {
        final String readAllStr = "select id,name,duration,h_inic,h_fin from carrera order by id";
        List<CareerDTO> allCareersList = new LinkedList<>();

        establishConnection();

        try (PreparedStatement readSt = conn.prepareStatement(readAllStr)) {
            ResultSet res = readSt.executeQuery();

            if (res != null)
                while (res.next()){
                    allCareersList.add(new CareerDTO(
                            res.getInt("id"),
                            res.getString("name"),
                            res.getInt("duration"),
                            res.getInt("h_inic"),
                            res.getInt("h_fin"))
                    );
                }
        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
            allCareersList = null;
        } finally {
            closeConnection();
        }

        return allCareersList;
    }

    @Override
    public CareerDTO getCareerByName(String careerName) {
        establishConnection();

        CareerDTO career = null;

        try (PreparedStatement readSt = conn.prepareStatement(readByNameStr)) {
            readSt.setString(1, careerName);
            ResultSet res = readSt.executeQuery();

            if (res != null && res.next()){
                career = new CareerDTO(
                        res.getInt("id"),
                        careerName,
                        res.getInt("duration"),
                        res.getInt("h_inic"),
                        res.getInt("h_fin"));
            }


        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
        } finally {
            closeConnection();
        }

        return career;
    }

    @Override
    public CareerDTO getCareerById(int id) {
        establishConnection();

        CareerDTO career = null;

        try (PreparedStatement readSt = conn.prepareStatement(readByIdStr)) {
            readSt.setInt(1, id);
            ResultSet res = readSt.executeQuery();

            if (res != null && res.next()){
                career = new CareerDTO(
                        id,
                        res.getString("name"),
                        res.getInt("duration"),
                        res.getInt("h_inic"),
                        res.getInt("h_fin"));
            }

        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
        } finally {
            closeConnection();
        }

        return career;
    }

    @Override
    public CareerDTO createCareer(CareerDTO career) {
        establishConnection();

        CareerDTO careerToReturn = null;


        //(name, duration, h_inic, h_fin)
        try (PreparedStatement createSt = conn.prepareStatement(createStr)) {
            conn.setAutoCommit(false);

            createSt.setString(1, career.getName());
            createSt.setInt(2, career.getYears());
            createSt.setInt(3, career.getPreferredStart());
            createSt.setInt(4, career.getPreferredEnd());

            createSt.executeUpdate();
            conn.commit();

            careerToReturn = this.getCareerByName(career.getName());
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

        return careerToReturn;
    }

    @Override
    public int deleteCareer(int idCareer) {
        establishConnection();

        try (PreparedStatement deleteSt = conn.prepareStatement(deleteStr)) {
            conn.setAutoCommit(false);

            deleteSt.setInt(1, idCareer);

            deleteSt.executeUpdate();
            conn.commit();
        }catch (SQLException e) {
            try {
                System.err.print("ERROR AL ELIMINAR EL REGISTRO: ");
                if (conn != null) {
                    System.err.println(e.getMessage());
                    System.err.println("INTENTADO HACER ROLLBACK");
                    conn.rollback();
                }
            } catch (SQLException excep) {
                System.err.print("ERROR AL INTENTAR HACER ROLLBACK");
                excep.printStackTrace();
            }finally {
                closeConnection();
            }
            return 400;
        }finally {
            closeConnection();
        }
        return 200;
    }
}