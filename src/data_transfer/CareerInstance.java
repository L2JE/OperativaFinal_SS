package data_transfer;

import service.Showable;

public class CareerInstance extends Showable {
    private final int idCareer;
    private final String name;
    private final int year;

    public CareerInstance(int idCareer, String name, int year){
        this.idCareer = idCareer;
        this.name = name;
        this.year = year;
    }

    public int getIdCareer() {
        return idCareer;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString(){
        String suffix;
        switch (year){
            case 1:
            case 3:
                suffix = "ro";
                break;
            case 2: suffix = "do";
                break;
            case 4:
            case 5:
            case 6: suffix = "to";
                break;
            case 7:
            case 10: suffix = "mo";
                break;
            case 8: suffix = "vo";
                break;
            case 9: suffix = "no";
                break;
            default: suffix = "avo";
                break;
        }

        return this.name + ", año: "+ this.year + suffix;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CareerInstance)
            return ((CareerInstance)obj).idCareer == this.idCareer &&
                    ((CareerInstance)obj).name.equals(this.name) &&
                    ((CareerInstance)obj).year == this.year;

        return false;
    }
}
