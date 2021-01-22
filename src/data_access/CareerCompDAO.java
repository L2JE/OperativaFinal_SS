package data_access;

import data_transfer.CareerCompDTO;

import java.util.ArrayList;

public interface CareerCompDAO {
    public abstract ArrayList<CareerCompDTO> getSubjects(int idCareer);
    public abstract ArrayList<CareerCompDTO> getAll();

    public abstract void setComposition(CareerCompDTO composition);
}
