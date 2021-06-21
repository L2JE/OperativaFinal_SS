package service;

import javafx.beans.Observable;
import javafx.util.Callback;

/**
 * Callback que se llama ante cualquier cambio en Showable
 */
public class ShowableChangeFXCb implements Callback<Showable,Observable[]>{

    public ShowableChangeFXCb(){

    }

    @Override
    public Observable[] call(Showable param) {
        //Se listan todas las propiedades de Showable que pueden cambiar para llamar al callback
        if(param!=null)
            return new Observable[]{
                    param.isDeleted()
            };

        return null;
    }

}
