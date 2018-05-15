package Interface;

import TradeCenter.Card.Card;
import TradeCenter.Customers.Customer;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;


public class OtherUserProfileScene {

    static BorderPane borderPane;
    static ScrollPane cardGrid;
    static HBox hBox;

    static BorderPane display(Customer customer) {
        borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();


        hBox = new HBox();
        hBox.setPadding(new Insets(15, 20, 10, 20));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #aa12ff");
        Button collection = new Button("Collection");
        Button wishlist = new Button("Wishlist");
        Button trade = new Button("Trade");
        hBox.getChildren().addAll(collection, wishlist, trade);

        for (Card card: customer.getCollection()){
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(300);
            cardPane.setCenter(imageView);


            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }

        cardGrid = new ScrollPane();
        cardGrid.setContent(flowPane);
        cardGrid.setStyle("-fx-background-color: #aa12ff");
        borderPane.setCenter(cardGrid);
        borderPane.setBottom(hBox);

        return borderPane;

    }
}
