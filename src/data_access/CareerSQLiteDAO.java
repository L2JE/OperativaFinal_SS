package data_access;

import data_transfer.CareerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CareerSQLiteDAO implements CareerDAO{
    private static final String urlToDB = "jdbc:sqlite:.data.dt";
    private static Connection conn = null;

    private static final String createStr = "insert into carrera(name, duration, h_inic, h_fin) " +
                                            "values (?,?,?,?)";

    private static final String readByNameStr = "select id,duration,h_inic,h_fin from carrera where name=?";

    private static final String updateStr = "";

    private static final String deleteStr = "";

    public CareerSQLiteDAO(){
        try {
            conn = DriverManager.getConnection(urlToDB);
        } catch (SQLException exception) {
            System.err.println("ERROR AL CONECTAR CON LA BASE DE DATOS");
            exception.printStackTrace();
        }
    }

    @Override
    public ArrayList<CareerDTO> getCareers() {
        return null;
    }

    @Override
    public CareerDTO getCareerByName(String careerName) {

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
        }

        return career;
    }

    @Override
    public CareerDTO getCareerById(int id) {
        return null;
    }

    @Override
    public CareerDTO createCareer(CareerDTO career) {
        //(name, duration, h_inic, h_fin)
        try (PreparedStatement createSt = conn.prepareStatement(createStr)) {
            conn.setAutoCommit(false);

            createSt.setString(1, career.getName());
            createSt.setInt(2, career.getYears());
            createSt.setInt(3, career.getPreferredStart());
            createSt.setInt(4, career.getPreferredEnd());

            createSt.executeUpdate();
            conn.commit();
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
                return null;
        }

        return this.getCareerByName(career.getName());
    }

    @Override
    public CareerDTO deleteCareer(int idCareer) {
        return null;
    }
}
