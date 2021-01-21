package tests;

import data_access.ClassroomDAO;
import data_access.ClassroomDAOImpl;
import data_access.ClassroomDTO;

import java.util.ArrayList;

public class ClassroomDAOTest {
    public static void main(String[] args){
        ClassroomDAO dao = ClassroomDAOImpl.getInstance();
        System.out.println("Test INICIADO");

        int max = 5;
        ClassroomDTO[] dtos = new ClassroomDTO[max];

        for(int i = 0 ; i < max; i++)
            dtos[i] = new ClassroomDTO(i, "Pab"+i , "Esp"+i);

        dao.setClassroom(dtos[16%max]);

        for(int i = 0 ; i < max ; i++){
            ClassroomDTO dto = dao.getRoomById(dtos[i].getIdRoom());
            System.out.println("Probando existencia de Aula: "+ dtos[i]);
            System.out.println("Existe: " + dto);

            ArrayList<ClassroomDTO> returned = dao.getRoomsOnLocation(dtos[i].getPabName());
            System.out.println("Aulas en el pabellon:");

            if( returned != null) {
                for (ClassroomDTO aula : returned)
                    System.out.println(aula);
            }

            System.out.println(":::::::::::::::::::::::::::");
        }



    }

}
