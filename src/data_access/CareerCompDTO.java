package data_access;

public class CareerCompDTO {
    private String idSubject;
    private String idCareer;

    public CareerCompDTO(String idSubject, String idCareer) {
        this.idSubject = idSubject;
        this.idCareer = idCareer;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getIdCareer() {
        return idCareer;
    }

    public void setIdCareer(String idCareer) {
        this.idCareer = idCareer;
    }
}
