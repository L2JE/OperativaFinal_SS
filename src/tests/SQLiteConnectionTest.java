package tests;

import data_access.CareerSQLiteDAO;
import data_transfer.CareerDTO;

public class SQLiteConnectionTest {
    private static CareerSQLiteDAO dao;

    public static void main(String[] args) {
        dao = new CareerSQLiteDAO();

        CareerDTO dto = dao.getCareerByName("Introduccion a la Joda");

        if(dto != null){
            System.out.println(":::::::::MOSTRANDO DTO:::::::::::::");
            System.out.println("id = " + dto.getIdCareer());
            System.out.println("name = " + dto.getName());
            System.out.println("duration = " + dto.getYears());
            System.out.println("h_Inic = " + dto.getPreferredStart());
            System.out.println("h_Fin = " + dto.getPreferredEnd());
            System.out.println(":::::::::FIN MOSTRANDO DTO:::::::::::::");
        }
    }
}
