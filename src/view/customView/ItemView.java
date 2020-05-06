package view.customView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Showable;
/**
 * PARA GENERALIZAR EXTENDER DE LISTCELL<Object> o LISTCELL<MiClase>
 * SOLO FUNCIONA CON LISTVIEW<String>
 *
 */
public class ItemView extends ListCell<Showable> {
    HBox hBox = new HBox();
    Label label = new Label("vacio");
    Pane p = new Pane();
    Button infoButton = new Button();
    Button deleteButton = new Button();

    public ItemView(){
        hBox.setSpacing(5.0);
        hBox.getChildren().addAll(label, p, infoButton, deleteButton);
        HBox.setHgrow(p, Priority.ALWAYS);

        infoButton.getStylesheets().add(this.getClass().getResource("../../content/stylesheets/infoButton.css").toExternalForm());
        infoButton.setTooltip(new Tooltip("Info"));
        deleteButton.getStylesheets().add(this.getClass().getResource("../../content/stylesheets/deleteButton.css").toExternalForm());
        deleteButton.setTooltip(new Tooltip("Borrar"));

        deleteButton.setOnAction(event -> {
            ListView<Showable> cont = getListView();
            cont.getItems().remove(getIndex());
            cont.refresh();
        });
    }

    @Override
    public void updateItem(Showable item, boolean empty)
    {
        super.updateItem(item, empty);

        // Format name
        if (item == null || empty)
        {
            setGraphic(null);
        }
        else
        {
            label.setText(item.toString());
            setGraphic(hBox);
        }

    }
}
