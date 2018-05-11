package Interface;

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

public class Demo {

    static Pane display(Node e){
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: DAE6A2;");

        pane.setCenter(e);
        //pane.setScaleX(pane.getScaleX() /0.5);
        //pane.setScaleY(pane.getScaleY() / 0.5);


        return  pane;
    }
}
