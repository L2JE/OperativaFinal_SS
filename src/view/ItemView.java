package view;

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
 * ItemView con botonInfo y delete
 * SOLO SIRVE PARA LISTVIEW<STRING>
 */
public class ItemView extends ListCell<String> {

    private HBox hBox = new HBox();
    private Label label = new Label("empty");
    private String lastItem;
    private Pane pane = new Pane();
    private Button infoB = new Button();
    private Button delB = new Button();

    public ItemView() {
        super();
        hBox.getChildren().addAll(label, pane, infoB, delB);
        HBox.setHgrow(pane, Priority.ALWAYS);

        infoB.setOnAction(event -> System.out.println(lastItem));

        delB.setOnAction(event -> {
            //Sure? yes/No
            int index = this.getIndex();
            ListView<String> cont = (ListView<String>) this.getParent();
            cont.getItems().remove(index);
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            label.setText(item!=null ? item : "<null>");
            setGraphic(hBox);
        }
    }

}
