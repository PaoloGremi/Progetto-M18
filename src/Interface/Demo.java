package Interface;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public  class Demo {
    /**
     * Zoom of the card
     * @param cardImage Card to zoom
     * @param scene Scene to go back
     * @return BorderPane with the card
     */
    static Pane display(Node cardImage, String scene){
        HashMap<String, Runnable> methodMap = new HashMap<>();
        methodMap.put("wish", () -> {
            try {
                Wish();
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        methodMap.put("collection", Demo::Collection);
        methodMap.put("other_user", Demo::OtherUser);
        methodMap.put("searchDescription", Demo::SearchDescription);
        methodMap.put("trade", Demo::Trade);
        BorderPane pane = new BorderPane();
        ScaleTransition ft = new ScaleTransition(Duration.millis(500), cardImage);
        ft.setFromX(0.5);
        ft.setFromY(0.5);
        if(!scene.equals("trade")) {
            ft.setToX(1.7);
            ft.setToY(1.7);
        }
        else {
            ft.setToX(3);
            ft.setToY(3);
        }
        cardImage.setOnMouseDragEntered(null);
        cardImage.setOnMouseDragExited(null);
        HBox hBox = new HBox();
        JFXButton button = new JFXButton("\u2B8C");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        hBox.getChildren().add(button);
        hBox.setPadding(new Insets(10));
        hBox.setStyle("-fx-background-color: orange;");
        pane.setStyle("-fx-background-color: DAE6A2;");
        ft.setAutoReverse(true);
        ft.play();
        pane.setCenter(cardImage);
        pane.setTop(hBox);

        button.setOnAction(event -> {

            methodMap.get(scene).run();

        });



        return  pane;
    }

    /**
     * Back to the wishlist
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void Wish() throws IOException, ClassNotFoundException {
        MainWindow.refreshDynamicContent(WishListScene.refresh());
    }

    /**
     * Back to the collection
     */
    private static void Collection(){
        try {
            MainWindow.refreshDynamicContent(CollectionScene.refresh());
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Back to the other user scene
     */
    private static void OtherUser(){
        MainWindow.refreshDynamicContent(OtherUserProfileScene.refresh());
    }

    /**
     * Back to search card description
     */
    private static void SearchDescription(){
        try {
            MainWindow.refreshDynamicContent(SearchDescriptionScene.refresh());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Back to trade scene
     */
    private static void Trade(){
        MainWindow.refreshDynamicContent(TradeScene.refresh());
    }
}
