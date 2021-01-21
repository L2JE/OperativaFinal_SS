package data_access;

import java.util.ArrayList;

public interface CareerCompDAO {
    public abstract ArrayList<CareerCompDTO> getSubjects(int idCareer);

    public abstract void setComposition(CareerCompDTO composition);
}
