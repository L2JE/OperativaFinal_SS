package view.customView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * PARA GENERALIZAR EXTENDER DE LISTCELL<Object> o LISTCELL<MiClase>
 * SOLO FUNCIONA CON LISTVIEW<String>
 *
 */
public class ItemView extends ListCell<String> {
    HBox hBox = new HBox();
    Label label = new Label("vacio");
    Pane p = new Pane();
    Button infoB = new Button("(i)");
    Button delB = new Button("(-)");

    public ItemView(){
        hBox.setSpacing(5.0);
        hBox.getChildren().addAll(label, p, infoB,  delB);
        HBox.setHgrow(p, Priority.ALWAYS);

        delB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView<String> cont = getListView();
                cont.getItems().remove(getIndex());
                cont.refresh();
            }
        });
    }

    @Override
    public void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);

        int index = this.getIndex();

        // Format name
        if (item == null || empty)
        {
            setGraphic(null);
        }
        else
        {
            label.setText(index + ". " + item);
            setGraphic(hBox);
        }

    }
}
