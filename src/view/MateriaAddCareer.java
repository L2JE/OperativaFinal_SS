package view;

import data_access.CareerDAO;
import data_access.CareerDAOImpl;
import data_transfer.CareerDTO;
import data_transfer.CareerInstance;
import data_transfer.SubjectDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import service.Showable;
import java.util.ArrayList;

public class MateriaAddCareer extends SendableFilling {

    @FXML
    private ComboBox<Showable> careerCB;
    @FXML
    private ComboBox<Integer> yearCB;
    @FXML
    private Label errorMsjLabel;

    private HomeWindowCntlr homeCntlr;
    private Showable careerToAdd;

    public void initialize() {
        this.careerCB.setVisibleRowCount(5);
        this.yearCB.setVisibleRowCount(5);

        CareerDAO dao = CareerDAOImpl.getInstance();
        ArrayList<CareerDTO> careers = dao.getCareers();
        if(careers == null)
            careers = new ArrayList<>();

        this.careerCB.getItems().clear();
        this.careerCB.getItems().addAll(careers);

        System.gc();

        this.careerCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                int years = ((CareerDTO)newValue).getYears();
                ObservableList<Integer> items = yearCB.getItems();
                items.clear();
                for(int i = 1; i <= years; i++)
                    items.add(i);
            }
        });

    }

    @Override
    public void setFillingReceiver(HomeWindowCntlr homeCntlr) {
        this.homeCntlr = homeCntlr;
    }

    @Override
    protected boolean validateUserInput() {

        Integer year = yearCB.getValue();
        CareerDTO career = (CareerDTO) careerCB.getValue();

        if(year == null || career == null)
            return false;

        careerToAdd = new CareerInstance(career.getIdCareer(), career.getName(), year);

        return true;

    }

    @Override
    public void setParam(Object param) {
    }

    @Override
    protected void sendData() {
        homeCntlr.addValidCareerToSubject(careerToAdd);
    }

    @Override
    protected void showErrorMsj() {
        errorMsjLabel.setVisible(true);
    }
}
