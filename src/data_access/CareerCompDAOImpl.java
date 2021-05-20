package data_access;

import data_transfer.CareerCompDTO;
import data_transfer.CareerDTO;

import java.util.ArrayList;

public class CareerCompDAOImpl implements CareerCompDAO{
    private ArrayList<CareerCompDTO> compositionCache = new ArrayList<>();
    private ArrayList<CareerDTO> careerCache = new ArrayList<>();

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

        for(CareerCompDTO dto : compositionCache)
            if(dto.getIdCareer() == idCareer)
                result.add(dto);

        if(result.size() > 0)
            return result;

        return null;
    }

    @Override
    public ArrayList<CareerCompDTO> getAll() {
        ArrayList<CareerCompDTO> result = new ArrayList<>();

        for(CareerCompDTO dto : compositionCache)
            result.add(dto);

        return result;
    }

    @Override
    public void setComposition(CareerCompDTO composition) {
        if(careerCache.get(composition.getIdCareer()) != null){
            for(CareerCompDTO dto : compositionCache)
                if((dto.getIdCareer() == composition.getIdCareer())&&
                        (dto.getIdSubject().equals(composition.getIdSubject())))
                    return;
            compositionCache.add(composition);
        }
    }

    @Override
    public CareerDTO getCareerByName(String careerName) {
        return null;
    }
}
