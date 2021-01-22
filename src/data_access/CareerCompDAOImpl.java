package data_access;

import java.util.ArrayList;
import java.util.Hashtable;

public class CareerCompDAOImpl implements CareerCompDAO{
    private ArrayList<CareerCompDTO> cache = new ArrayList<>();
    private static CareerCompDAOImpl instance;


    private CareerCompDAOImpl(){

    }

    public static CareerCompDAOImpl getInstance(){
        if(instance == null)
            instance = new CareerCompDAOImpl();
        return instance;
    }

    @Override
    public ArrayList<CareerCompDTO> getSubjects(int idCareer) {
        ArrayList<CareerCompDTO> result = new ArrayList<>();

        for(CareerCompDTO dto : cache)
            if(dto.getIdCareer() == idCareer)
                result.add(dto);

        if(result.size() > 0)
            return result;

        return null;
    }

    @Override
    public ArrayList<CareerCompDTO> getAll() {
        ArrayList<CareerCompDTO> result = new ArrayList<>();

        for(CareerCompDTO dto : cache)
            result.add(dto);

        return result;
    }

    @Override
    public void setComposition(CareerCompDTO composition) {
        for(CareerCompDTO dto : cache)
            if((dto.getIdCareer() == composition.getIdCareer())&&
               (dto.getIdSubject().equals(composition.getIdSubject())))
                return;
        cache.add(composition);
    }
}
