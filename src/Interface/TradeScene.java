package Interface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TradeScene {

    static HBox buttonsBox;
    static GridPane mainGrid;
    static BorderPane mainPane;

    static BorderPane display(){
        mainPane = new BorderPane();
        //griglia
        mainGrid = new GridPane();
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        //todo mettere le griglie ecc...
        TextArea myCollection = new TextArea("myCollection");
        TextArea myOffer = new TextArea("myOffer");
        TextArea otherCollection = new TextArea("otherCollection");
        TextArea otherOffer = new TextArea("otherOffer");

        //bottoni
        buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(15, 20, 10, 20));
        buttonsBox.setSpacing(10);
        buttonsBox.setStyle("-fx-background-color: #aa12ff");
        Button refuse = new Button("Refuse");
        Button raise = new Button("Raise");
        Button accept = new Button("Accept");
        buttonsBox.getChildren().addAll(refuse, raise, accept);

        mainPane.setCenter(mainGrid);
        mainPane.setBottom(buttonsBox);

        return mainPane;
    }
}
