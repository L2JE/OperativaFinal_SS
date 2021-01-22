package tests;

import data_access.CareerCompDAO;
import data_access.CareerCompDAOImpl;
import data_transfer.CareerCompDTO;

import java.util.ArrayList;

public class CareerCompDAOTest {
    public static void main(String[] args){
        CareerCompDAO dao = CareerCompDAOImpl.getInstance();
        ArrayList<CareerCompDTO> composition = dao.getSubjects(1);
        dao.setComposition(new CareerCompDTO("Ingenieria", 1));
        dao.setComposition(new CareerCompDTO("Mate", 1));
        dao.setComposition(new CareerCompDTO("Chan", 2));
        dao.setComposition(new CareerCompDTO("Pepe", 1));
        dao.setComposition(new CareerCompDTO("Alba", 1));

        composition = dao.getSubjects(3);

        if(composition != null)
        for(CareerCompDTO dto : composition)
            System.out.println(dto.getIdSubject() + dto.getIdCareer());
    }
}
