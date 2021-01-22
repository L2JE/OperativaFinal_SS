package view.customView;


import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import service.Showable;

public class ItemViewFactory implements Callback<ListView<Showable>, ListCell<Showable>> {
    @Override
    public ListCell<Showable> call(ListView<Showable> listView){
        return new ItemView();
    }
}