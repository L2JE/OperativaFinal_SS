package tests;

import data_access.CareerDAO;
import data_access.CareerSQLiteDAO;
import data_transfer.CareerDTO;

import java.util.LinkedList;
import java.util.List;

public class SQLiteConnectionTest {

    public static void main(String[] args) {
        should_create_a_new_career_and_return_with_id();
        should_delete_career_by_given_id();
        should_return_careers_list_consistant_with_external_script();


    }

    private static void should_return_careers_list_consistant_with_external_script() {
        List<CareerDTO> list = new LinkedList<>();
        list.add(new CareerDTO(2, "Carrera 1", 5, 8, 13));
        list.add(new CareerDTO(4, "Carrera 2", 5, 8, 13));
        list.add(new CareerDTO(6, "Carrera 3", 5, 8, 13));
        list.add(new CareerDTO(8, "Carrera 4", 5, 8, 13));
        list.add(new CareerDTO(10, "Carrera 5", 5, 8, 13));
        list.add(new CareerDTO(12, "Carrera 6", 5, 8, 13));
        list.add(new CareerDTO(14, "Carrera 7", 5, 8, 13));
        list.add(new CareerDTO(16, "Carrera 8", 5, 8, 13));

        CareerDAO dao = new CareerSQLiteDAO();
        List<CareerDTO> returned = dao.getAllCareers();
        returned.remove(returned.size() - 1);

        assert returned != null &&
               returned.equals(list)
               :"\n[NO SE RECUPERARON CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno: " + returned;

        System.out.println("TEST PASSED: SQLiteCareerDAO::should_return_careers_list_consistant_with_external_script");
    }

    private static void should_delete_career_by_given_id() {
        final int idToBeDeleted = 256;

        CareerDAO dao = new CareerSQLiteDAO();
        int removedCareer = dao.deleteCareer(idToBeDeleted);

        assert removedCareer == 200
               : "\n[NO SE ELIMINO CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno del DELETE: " + removedCareer;
        System.out.println("TEST PASSED: SQLiteCareerDAO::should_delete_career_by_given_id");

    }

    private static void should_create_a_new_career_and_return_with_id(){
        CareerDAO dao = new CareerSQLiteDAO();

        CareerDTO beforeInsertion = new CareerDTO("Carrera Prueba", 5, 8, 12);

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

        System.out.println("TEST PASSED: SQLiteCareerDAO::should_create_a_new_career_and_return_with_id");

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
