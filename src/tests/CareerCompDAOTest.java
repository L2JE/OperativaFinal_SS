package tests;

import data_access.CareerCompDAO;
import data_access.CareerCompDAOImpl;
import data_transfer.CareerDTO;

public class CareerCompDAOTest {
    public static void main(String[] args) {

        //should_return_same_dto_but_diff_ID();
        should_return_null_on_not_existing_career();
    }

    public static void should_return_same_dto_but_diff_ID() {
        CareerDTO dto = new CareerDTO(-1, "Ingenieria Civil", 5, 0, 5);
        System.out.println(dto + " id: " + dto.getIdCareer() + ", inic: " +
                           dto.getPreferredStart() + ", fin: " + dto.getPreferredEnd());

        CareerCompDAO dao = CareerCompDAOImpl.getInstance();
        CareerDTO insertedDTO = null;

        try {
            insertedDTO = dao.createCareer(dto);
        } catch (CloneNotSupportedException e) {
            System.err.println("NO SOPORTADO EL CLONE");
            e.printStackTrace();
        }

        System.out.println("Antiguo: "+ dto + " id: " + dto.getIdCareer() + ", inic: " +
                dto.getPreferredStart() + ", fin: " + dto.getPreferredEnd());

        System.out.println("Insertado: "+ insertedDTO + " id: " + insertedDTO.getIdCareer() + ", inic: " +
                insertedDTO.getPreferredStart() + ", fin: " + insertedDTO.getPreferredEnd());

        if(dto != insertedDTO) System.out.println("SON DISTINTOS");
        else System.out.println("Son el mismo!!");


    }
    public static void should_return_null_on_not_existing_career(){
        CareerCompDAO dao = CareerCompDAOImpl.getInstance();
        CareerDTO result = dao.getCareerById(0);

        if(result != null) System.err.println("CACHE ESTA VACIA Y NO DEVUELVE NULL");
        else System.out.println("El objeto no existe xq no fue insertado");

        CareerDTO createdDTO = null;
        try {
            createdDTO = dao.createCareer(new CareerDTO("Ingenieria Civil", 5, 0, 5));
            System.out.println(createdDTO);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        result = dao.getCareerById(0);

        if(result != null) System.out.println("Cache con un elemento y lo devuelve");
        else    System.err.println("CACHE  Y NO DEVUELVE NULL");

        if ((createdDTO != null) && (result == createdDTO))
            System.err.println("NO DEVUELVE UNA COPIA");

    }

}
