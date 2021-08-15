package data_access;

import data_transfer.CareerCompDTO;
import data_transfer.CareerDTO;

import java.util.ArrayList;

public class CareerDAOImpl implements CareerDAO {
    private ArrayList<CareerCompDTO> compositionCache = new ArrayList<>();
    private ArrayList<CareerDTO> careerCache = new ArrayList<>();
    private int lastCareerId = -1;

    private static CareerDAOImpl instance;


    private CareerDAOImpl(){

    }

    public static CareerDAOImpl getInstance(){
        if(instance == null)
            instance = new CareerDAOImpl();
        return instance;
    }

    @Override
    public ArrayList<CareerDTO> getCareers() {
        return (ArrayList<CareerDTO>)careerCache.clone();
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
            for(CareerDTO dto : careerCache)
                if(dto.getIdCareer() == id) {
                    try {
                        return (CareerDTO) dto.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
        return null;
    }

    @Override
    public CareerDTO createCareer(CareerDTO career) {
        for(CareerDTO dto : careerCache)
            if(dto.getIdCareer() == career.getIdCareer())
                return null;
        CareerDTO copy;
        try{

            copy = (CareerDTO) career.clone();
            careerCache.add(copy);
            int id = this.lastCareerId + 1;
            copy.setIdCareer(id);
            career.setIdCareer(id);
            this.lastCareerId = id;

            return career;

        }catch (CloneNotSupportedException e){
            System.err.println("No se puede Clonar"); //deberia hacer un log
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public CareerDTO deleteCareer(int idCareer) {
        int cacheSize = careerCache.size();
        for(int careerIndex = 0; careerIndex<cacheSize ; careerIndex++)
            if(careerCache.get(careerIndex).getIdCareer() == idCareer){
                CareerDTO removed = careerCache.remove(careerIndex);

                for(int compIndex = 0; compIndex<compositionCache.size(); compIndex++){
                    if(compositionCache.get(compIndex).getIdCareer() == idCareer)
                        compositionCache.remove(compIndex);
                }

                return removed;
            }
        return null;
    }

    @Override
    public String toString() {
        String salida = "Lista de Carreras";
        for(CareerDTO c : careerCache)
            salida += "\n " + c.getIdCareer()+". "+c.getName();

        salida += "\nLista de Materias por Carrera";
        for(CareerCompDTO c : compositionCache)
            salida += "\n Carrera:" + c.getIdCareer()+", Materia: "+c.getIdSubject();


        return salida;
    }
}