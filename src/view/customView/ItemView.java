package view.customView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
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
    Button infoB = new Button();
    Button delB = new Button();

    public ItemView(){
        hBox.setSpacing(5.0);
        hBox.getChildren().addAll(label, p, infoB,  delB);
        HBox.setHgrow(p, Priority.ALWAYS);

        infoB.getStylesheets().add(this.getClass().getResource("../stylesheets/info-button.css").toExternalForm());
        infoB.setTooltip(new Tooltip("Info"));
        delB.getStylesheets().add(this.getClass().getResource("../stylesheets/del-button.css").toExternalForm());
        delB.setTooltip(new Tooltip("Borrar"));

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

        // Format name
        if (item == null || empty)
        {
            setGraphic(null);
        }
        else
        {
            label.setText(item);
            setGraphic(hBox);
        }

    }
}
