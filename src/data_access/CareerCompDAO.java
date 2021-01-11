package data_access;

import java.util.ArrayList;

public interface CareerCompDAO {
    public abstract ArrayList<CareerCompDTO> getSubjects(String idCareer);
    public abstract ArrayList<CareerCompDTO> getCareers(String idSubject);

    public abstract void setComposition(CareerCompDTO composition);
    public abstract void setCompositionList(ArrayList<CareerCompDTO> composition);
}
