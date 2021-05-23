package data_access;

import data_transfer.CareerCompDTO;
import data_transfer.CareerDTO;

import java.util.ArrayList;

public interface CareerCompDAO {
    public abstract ArrayList<CareerCompDTO> getSubjects(int idCareer);
    public abstract ArrayList<CareerCompDTO> getAll();

    public abstract void setComposition(CareerCompDTO composition);

    public abstract CareerDTO getCareerByName(String careerName);
    public abstract CareerDTO getCareerById(int id);
    public abstract CareerDTO createCareer(CareerDTO career);
    public abstract CareerDTO deleteCareer(int idCareer);

}
