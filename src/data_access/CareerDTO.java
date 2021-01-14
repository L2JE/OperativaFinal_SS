package data_access;

import model.Showable;

public class CareerDTO implements Showable {
    private int idCareer;
    private String name;
    private int years;
    private int preferredStart;
    private int preferredEnd;

    public CareerDTO(int idCareer, String name, int years, int preferredStart, int preferredEnd) {
        this.idCareer = idCareer;
        this.name = name;
        this.years = years;
        this.preferredStart = preferredStart;
        this.preferredEnd = preferredEnd;
    }

    public int getIdCareer() {
        return idCareer;
    }

    public void setIdCareer(int idCareer) {
        this.idCareer = idCareer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getPreferredStart() {
        return preferredStart;
    }

    public int getPreferredEnd() {
        return preferredEnd;
    }

    public void setPreferredBand(int preferredStart, int preferredEnd){
        this.preferredStart = preferredStart;
        this.preferredEnd = preferredEnd;
    }

    @Override
    public String toString() {
        return name +" ("+ years + "años)";
    }
}
