package data_access;

import data_transfer.CareerDTO;

import java.util.List;

public interface CareerDAO {
    List<CareerDTO> getAllCareers();
    CareerDTO getCareerByName(String careerName);
    CareerDTO getCareerById(int id);
    CareerDTO createCareer(CareerDTO career);
    int deleteCareer(int idCareer);

}
