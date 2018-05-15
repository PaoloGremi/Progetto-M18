package Interface;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

public  class Demo {

    static Pane display(Node e, String scene){
        BorderPane pane = new BorderPane();
        e.setScaleX(pane.getScaleX() * 1.7);
        e.setScaleY(pane.getScaleY() * 1.7);
        HBox hBox = new HBox();
        Button button = new Button("\u2B8C");
        hBox.getChildren().add(button);
        hBox.setPadding(new Insets(10));
        hBox.setStyle("-fx-background-color: orange;");
        pane.setStyle("-fx-background-color: DAE6A2;");
        pane.setCenter(e);
        pane.setTop(hBox);

        button.setOnAction(event -> {
            switch (scene){
                case "wish": {MainWindow.refreshDynamicContent(WishListScene.refresh());
                             break;}
                case "collection": {MainWindow.refreshDynamicContent(CollectionScene.refresh());
                            break;}
                case "other_user": {MainWindow.refreshDynamicContent(OtherUserProfileScene.refresh());
                    break;}
            }
        });



        return  pane;
    }
}
