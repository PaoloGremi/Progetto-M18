package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Interface to see other user's account
 */
public class OtherUserProfileScene {

    static StackPane cardList;
    static BorderPane borderPane;
    static ScrollPane cardGrid;


    static boolean watchingWishlist = false;

    static Customer otherCustomer;
    static String displayed = "Collection";

    /**
     * method to display the scene
     * @param myCustomer the customer itself
     * @param otherUser the other customer
     * @return the scene
     */
    public static BorderPane display(Customer myCustomer, Customer otherUser) {
        otherCustomer = otherUser;
        borderPane = new BorderPane();
        cardList = new StackPane();

        HBox buttons= new HBox();
        buttons.setPadding(new Insets(10));
        buttons.setSpacing(10);
        Button collection = new Button("Collection");
        Button wishlist = new Button("Wishlist");
        Button trade = new Button("Trade");
        buttons.getChildren().addAll(collection, wishlist, trade);
        HBox titleBox = new HBox();
        Label title = new Label(otherUser.getUsername() + "'s" + displayed);
        titleBox.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-weight: bold");
        title.setScaleX(1.35);
        title.setScaleY(1.35);
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(10));
        title.setAlignment(Pos.CENTER);


        HBox hBox= new HBox();
        hBox.getChildren().addAll(buttons, titleBox);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(300);
        hBox.setStyle("-fx-background-color: #cc003a");

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
        trade.setOnAction(event -> {
            try {

                if(possibleTrade(myCustomer.getId(),otherCustomer.getId())){
                    MainWindow.refreshDynamicContent(TradeScene.display(null, myCustomer, otherCustomer,false, false));
                }else{

                    MainWindow.addDynamicContent(InfoScene.display("The trade with "+ otherCustomer.getUsername() +" is already started\nsee it in My Trades", "Interface/imagePack/2000px-Simple_Alert.svg.png", false));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        return borderPane;
    }

    /**
     * method used to see the other user's collection
     * @param customer the other customer
     * @return the pane
     */
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
            imageView.setFitHeight(285);
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

    /**
     * Method used to show the other customer's whishlist
     * @param customer the other customer
     * @return the pane
     */
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
            EventHandler<MouseEvent> eventHandlerBox = mouseEvent(imageView);
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

    /**
     * method that handle the mouse event
     * @param imageView the card's image
     * @return the event of clicked card
     */
    public static EventHandler<MouseEvent> mouseEvent(ImageView imageView){
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                MainWindow.refreshDynamicContent(Demo.display(imageView, "other_user"));
            }
        };
        return event;
    }

    /**
     * method that talls if a trade is possible
     * @param myId the id
     * @param otehrId the other id
     * @return if it's possible or not
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static boolean possibleTrade(String myId, String otehrId) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(ServerIP.ip, ServerIP.port);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(new MessageServer(MessageType.POSSIBLETRADE, myId, otehrId));
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        boolean flag = (boolean)(is.readObject());
        socket.close();
        return flag;
    }

    /**
     * method that display the first view
     * @return the pane
     */
    public static BorderPane refresh(){
        cardList.getChildren().removeAll(cardList.getChildren());
        if(watchingWishlist){
            //return to whishlist
            displayed = "Wishlist";
            cardList.getChildren().add(displayWishlist(otherCustomer));
        }else{
            displayed = "Collection";
            cardList.getChildren().add(displayCollection(otherCustomer));
        }
        borderPane.setCenter(cardList);
        return borderPane;
    }
}