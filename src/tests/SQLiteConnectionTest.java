package tests;

import data_access.CareerSQLiteDAO;
import data_transfer.CareerDTO;

public class SQLiteConnectionTest {
    private static CareerSQLiteDAO dao;

    public static void main(String[] args) {
        should_create_a_new_career_and_return_with_id();
    }

    private static void should_create_a_new_career_and_return_with_id(){
        dao = new CareerSQLiteDAO();

        CareerDTO beforeInsertion = new CareerDTO("Carrera 2", 5, 8, 12);

        //showCareer(beforeInsertion);

        CareerDTO afterInsertion = dao.createCareer(beforeInsertion);

        //showCareer(afterInsertion);

        assert ((afterInsertion != null) && (
                afterInsertion.getIdCareer() > 0 &&
                afterInsertion.getName().equals(beforeInsertion.getName()) &&
                afterInsertion.getYears() == beforeInsertion.getYears() &&
                afterInsertion.getPreferredStart() == beforeInsertion.getPreferredStart() &&
                afterInsertion.getPreferredEnd() == beforeInsertion.getPreferredEnd()
                )) : "\n[NO SE INSERTO CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                     "Valor de retorno del create: " + afterInsertion;

        System.out.println("TEST PASSED: SQLiteConnectionTest::should_create_a_new_career_and_return_with_id");

    }

    private static void showCareer(CareerDTO dto){
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
