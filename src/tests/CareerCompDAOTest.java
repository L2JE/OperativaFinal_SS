package tests;

import data_access.CareerDAO;
import data_access.CareerDAOImpl;
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

        CareerDAO dao = CareerDAOImpl.getInstance();
        CareerDTO insertedDTO = null;

        insertedDTO = dao.createCareer(dto);

        System.out.println("Antiguo: "+ dto + " id: " + dto.getIdCareer() + ", inic: " +
                dto.getPreferredStart() + ", fin: " + dto.getPreferredEnd());

        System.out.println("Insertado: "+ insertedDTO + " id: " + insertedDTO.getIdCareer() + ", inic: " +
                insertedDTO.getPreferredStart() + ", fin: " + insertedDTO.getPreferredEnd());

        if(dto != insertedDTO) System.out.println("SON DISTINTOS");
        else System.out.println("Son el mismo!!");


    }
    public static void should_return_null_on_not_existing_career(){
        CareerDAO dao = CareerDAOImpl.getInstance();
        CareerDTO result = dao.getCareerById(0);

        if(result != null) System.err.println("CACHE ESTA VACIA Y NO DEVUELVE NULL");
        else System.out.println("El objeto no existe xq no fue insertado");

        CareerDTO createdDTO = null;
        createdDTO = dao.createCareer(new CareerDTO("Ingenieria Civil", 5, 0, 5));
        System.out.println("id: "+createdDTO.getIdCareer()+"Carrera: "+ createdDTO);

        result = dao.getCareerById(0);

        if(result != null) System.out.println("Cache con un elemento y lo devuelve");
        else    System.err.println("CACHE  Y NO DEVUELVE NULL");

        if ((createdDTO != null) && (result == createdDTO))
            System.err.println("NO DEVUELVE UNA COPIA");
        else
            System.out.println("DEVUELVE UNA COPIA");

        System.out.println("Por nombre" + dao.getCareerByName("Ingenieria Civil") + "id: "+ dao.getCareerByName("Ingenieria Civil").getIdCareer());

    }

}
