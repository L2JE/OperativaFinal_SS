package data_transfer;

import service.Showable;

public class CareerDTO extends Showable {
    private int idCareer = -1;
    private String name;
    private int years;
    private int preferredStart;
    private int preferredEnd;

    public CareerDTO(String name, int years, int preferredStart, int preferredEnd) {
        super();
        this.name = name;
        this.years = years;
        this.preferredStart = preferredStart;
        this.preferredEnd = preferredEnd;
    }
    public CareerDTO(int idCareer, String name, int years, int preferredStart, int preferredEnd) {
        super();
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        CareerDTO copy = new CareerDTO(idCareer, name, years, preferredStart, preferredEnd);

        return copy;
    }
}
