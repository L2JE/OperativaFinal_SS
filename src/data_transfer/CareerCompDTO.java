package data_transfer;

public class CareerCompDTO {
    private String idSubject;
    private int idCareer;

    public CareerCompDTO(String idSubject, int idCareer) {
        this.idSubject = idSubject;
        this.idCareer = idCareer;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public int getIdCareer() {
        return idCareer;
    }

    public void setIdCareer(int idCareer) {
        this.idCareer = idCareer;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new CareerCompDTO(idSubject, idCareer);
    }
}
