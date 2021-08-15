package data_access;

import data_transfer.CareerDTO;

import java.util.ArrayList;

public interface CareerDAO {
    public abstract ArrayList<CareerDTO> getCareers();
    public abstract CareerDTO getCareerByName(String careerName);
    public abstract CareerDTO getCareerById(int id);
    public abstract CareerDTO createCareer(CareerDTO career);
    public abstract CareerDTO deleteCareer(int idCareer);

}
