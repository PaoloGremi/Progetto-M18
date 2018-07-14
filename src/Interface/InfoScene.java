package Interface;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;



public class InfoScene {

    /**
     * Displays a Info Scene over the current Scene
     * @param infoString Message to display
     * @param url Url of the image to display with the message
     * @param removable Set if the customer can remove the info scene clicking on it
     * @return BorderPane with InfoScene
     */
    public static BorderPane display(String infoString, String url, Boolean removable){
        BorderPane pane = new BorderPane();
        VBox vBox = new VBox();
        Label text = new Label("\n"+ infoString);
        Text alert = new Text("\u26A0");
        alert.setStyle("-fx-text-fill: red");
        alert.setStyle("-fx-font-weight: bold");
        Image image = new Image(url);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);
        //text.setText(alert.getText()+"\n"+text.getText());
        text.setAlignment(Pos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setStyle("-fx-font-size: 30 -fx-font");
        text.setScaleX(text.getScaleX()*3);
        text.setScaleY(text.getScaleY()*3);
        //text.setTextFill(Color.web("#ffffff"));
        text.setStyle("-fx-font-weight: bold");
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(imageView, text);
        pane.setCenter(vBox);
        if(removable) {
            pane.setOnMouseClicked(event -> {
                MainWindow.removeDynamicContent(pane);
            });
        }
        pane.setStyle("-fx-background-color: rgba(235,255,235,0.62);");
        return pane;
    }
}
