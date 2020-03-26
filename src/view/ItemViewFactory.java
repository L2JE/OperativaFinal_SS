package view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ItemViewFactory implements Callback<ListView<String>, ListCell<String>> {
    @Override
    public ListCell<String> call(ListView<String> param) {
        return new ItemView();
    }
}
