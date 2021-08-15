package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import service.Showable;

public abstract class SendableFilling {
    public abstract void setFillingReceiver(HomeWindowCntlr homeCntlr);
    public abstract void setParam(Object param);
    protected abstract boolean validateUserInput();

    //Stores data un persistanse and send it to parent window
    protected abstract void sendData();
    protected abstract void showErrorMsj();

    @FXML
    protected void addPressed(Event event) {

        System.out.println("Hubo un Evento en Agregar");
        if(buttonWasPressed(event)){

            if(validateUserInput()){
                sendData();
                closeWindow(event);
            }
            else
                showErrorMsj();
        }
    }
    @FXML
    protected void cancelPressed(Event event) {
        System.out.println("Hubo un Evento en Cancelar");
        if(buttonWasPressed(event)) {
            closeWindow(event);
        }
    }

    protected boolean buttonWasPressed(Event event){
        String eventType = event.getEventType().getName();
        if(eventType.equals("KEY_PRESSED") &&
                ((KeyEvent) event).getCode() == KeyCode.ENTER)
            return true;

        if(eventType.equals("ACTION"))
            return true;

        return false;
    }
    protected void closeWindow(Event event){
        Window w = ((Node)event.getSource()).getScene().getWindow();
        ((Stage)w).close();
    }
}
