package Interface;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


public class OtherUserProfileScene {

    static StackPane cardList;
    static BorderPane borderPane;
    static ScrollPane cardGrid;
    static HBox hBox;
    static boolean watchingWishlist = false;

    static Customer otherCustomer;


    static BorderPane display(Customer myCustomer, Customer otherUser) {
        final Customer otherCustomer = otherUser;
        borderPane = new BorderPane();
        cardList = new StackPane();

        hBox = new HBox();
        hBox.setPadding(new Insets(15, 20, 10, 20));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #aa12ff");
        Button collection = new Button("Collection");
        Button wishlist = new Button("Wishlist");
        Button trade = new Button("Trade");
        hBox.getChildren().addAll(collection, wishlist, trade);

        borderPane.setCenter(displayCollection(otherCustomer));
        borderPane.setBottom(hBox);


        collection.setOnAction(event -> {
            watchingWishlist = false;
            cardList.getChildren().removeAll(cardList.getChildren());
            cardList.getChildren().add(displayCollection(otherCustomer));
            borderPane.setCenter(cardList);
            MainWindow.refreshDynamicContent(borderPane);
        });
        wishlist.setOnAction(event -> {
            watchingWishlist = true;
            cardList.getChildren().removeAll(cardList.getChildren());
            cardList.getChildren().add(displayWishlist(otherCustomer));
            borderPane.setCenter(cardList);
            MainWindow.refreshDynamicContent(borderPane);
        });
        //todo mettere listener sul bottone

        trade.setOnAction(event -> {
            MainWindow.refreshDynamicContent(TradeScene.display(myCustomer, otherCustomer));// todo mettere i parametri, della fuznione
        });

        return borderPane;
    }

    private static ScrollPane displayCollection(Customer customer){
        FlowPane flowPane = new FlowPane();
        cardGrid = new ScrollPane();
        flowPane.setStyle("-fx-background-color: #fbff2e");

        for (Card card: customer.getCollection()){
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(300);
            cardPane.setCenter(imageView);
            EventHandler<MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(imageView, "other_user"));
                        }
                    };

            imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);
            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }

        cardGrid.setFitToWidth(true);
        cardGrid.setFitToHeight(true);
        cardGrid.setContent(flowPane);
        cardGrid.setStyle("-fx-background-color: #fffd14");
        return cardGrid;
    }
    private static ScrollPane displayWishlist(Customer customer){
        FlowPane flowPane = new FlowPane();
        cardGrid = new ScrollPane();
        flowPane.setStyle("-fx-background-color: #fff910");

        for (Description card: customer.getWishList()){
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(300);
            cardPane.setCenter(imageView);
            EventHandler<MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(imageView, "other_user"));
                        }
                    };

            imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);
            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }
        cardGrid.setFitToWidth(true);
        cardGrid.setFitToHeight(true);
        cardGrid.setContent(flowPane);
        cardGrid.setStyle("-fx-background-color: #fffb48");
        return cardGrid;
    }

    public static BorderPane refresh(){
        cardList.getChildren().removeAll(cardList.getChildren());
        if(watchingWishlist){
            //return to whishlist
            cardList.getChildren().add(displayWishlist(otherCustomer));
        }else{
            cardList.getChildren().add(displayCollection(otherCustomer));
        }
        borderPane.setCenter(cardList);
        return borderPane;
    }
}