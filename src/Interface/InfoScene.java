package Interface;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InfoScene {

    public static BorderPane display(String infoString){
        BorderPane pane = new BorderPane();
        TextFlow text = new TextFlow(new Text(infoString));
        text.setStyle("-fx-font-size: 30 -fx-font");
        pane.setCenter(text);
        pane.setOnMouseClicked(event -> {
            MainWindow.removeDynamicContent(pane);
        });
        //todo centrare scritta, mettere blur sotto, fare in  modo che fitta tutto lo schermo(vedi in basso)
        pane.setStyle("-fx-background-color: rgba(176,176,178,0.81)");
        return pane;
    }
}
