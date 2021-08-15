package data_access;

import data_transfer.CareerDTO;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class CareerSQLiteDAO implements CareerDAO{
    private static final String urlToDB = "jdbc:sqlite:.data.dt";
    private static Connection conn = null;

    private static final String createStr = "insert into carrera(name, duration, h_inic, h_fin) " +
                                            "values (?,?,?,?)";

    private static final String readStr = "select id,duration,h_inic,h_fin from carrera where name=?";

    private static final String updateStr = "";

    private static final String deleteStr = "delete from carrera where 1=1";

    public CareerSQLiteDAO(){
        try {
            conn = DriverManager.getConnection(urlToDB);
            System.out.println("Conexion Establecida!!!");
        } catch (SQLException exception) {
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

        try (PreparedStatement readSt = conn.prepareStatement(readStr)) {
            readSt.setString(1, careerName);
            ResultSet res = readSt.executeQuery();

            if (res != null && res.next()){
                System.out.println("id = " + res.getInt("id"));
                System.out.println("duration = " + res.getInt("duration"));
                System.out.println("h_Inic = " + res.getInt("h_inic"));
                System.out.println("h_Fin = " + res.getInt("h_fin"));

                career = new CareerDTO(
                        res.getInt("id"),
                        careerName,
                        res.getInt("duration"),
                        res.getInt("h_inic"),
                        res.getInt("h_fin"));
            }


        } catch (SQLException exception) {
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
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }

        return this.getCareerByName(career.getName());
    }

    @Override
    public CareerDTO deleteCareer(int idCareer) {
        return null;
    }
}
