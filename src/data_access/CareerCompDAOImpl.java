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
        /**
         * idCareer is supposed to be already in the storage
         */

        ArrayList<CareerCompDTO> result = new ArrayList<>();
        for(CareerCompDTO dto : compositionCache)
            if(dto.getIdCareer() == idCareer) {
                try {
                    result.add((CareerCompDTO) dto.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        if(result.size() > 0)
            return result;

        return null;
    }

    @Override
    public ArrayList<CareerCompDTO> getAll() {
        return (ArrayList<CareerCompDTO>)compositionCache.clone();
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
        for(CareerDTO dto : careerCache)
            if(dto.getName().equals(careerName)) {
                try {
                    return (CareerDTO) dto.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    @Override
    public CareerDTO getCareerById(int id) {
        if(id > -1)
            try {
                return (CareerDTO) careerCache.get(id).clone();
            } catch (IndexOutOfBoundsException e){
                return null;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public CareerDTO createCareer(CareerDTO career) throws CloneNotSupportedException {
        //Ver de guardar una copia en lugar del original
        for(CareerDTO dto : careerCache)
            if(dto.getIdCareer() == career.getIdCareer())
                return null;

        CareerDTO copy = (CareerDTO) career.clone();
        careerCache.add(copy);
        int id = careerCache.indexOf(copy);

        copy.setIdCareer(id);
        career.setIdCareer(id);
        return career;
    }
}
