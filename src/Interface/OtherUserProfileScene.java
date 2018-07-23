package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;

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
        JFXButton collection = new JFXButton("Collection");
        collection.setButtonType(JFXButton.ButtonType.RAISED);
        JFXButton wishlist = new JFXButton("Wishlist");
        wishlist.setButtonType(JFXButton.ButtonType.RAISED);
        JFXButton trade = new JFXButton("Trade");
        trade.setButtonType(JFXButton.ButtonType.RAISED);
        buttons.getChildren().addAll(collection, wishlist, trade);
        HBox titleBox = new HBox();
        Label title = new Label(otherUser.getUsername() + "'s " + displayed);
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
        hBox.setStyle("-fx-background-color: orange");

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

                    MainWindow.addDynamicContent(InfoScene.display("The trade with "+ otherCustomer.getUsername() +"\nis already started\nsee it in My Trades", "Interface/imagePack/2000px-Simple_Alert.svg.png", false));
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
        flowPane.setStyle("-fx-background-color: DAE6A2;");

        for (Card card: customer.getCollection()){
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(285);
            ImageView imageCopy = new ImageView(image);
            imageCopy.setPreserveRatio(true);
            imageCopy.setFitHeight(285);
            cardPane.setCenter(imageView);
            imageView.setOnMouseEntered(event -> {
                ScaleTransition st = CollectionScene.addScale(imageView);
                st.play();
            });
            imageView.setOnMouseExited(event -> {
                ScaleTransition st = CollectionScene.removeScale(imageView);
                st.play();
            });
            EventHandler<MouseEvent> eventHandlerBox = mouseEvent(imageCopy);

            imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);
            FadeTransition ft = new FadeTransition(Duration.millis(500), cardPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(true);
            ft.play();
            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }

        cardGrid.setFitToWidth(true);
        cardGrid.setFitToHeight(true);
        cardGrid.setContent(flowPane);
        cardGrid.setStyle("-fx-background-color: DAE6A2;");
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
        flowPane.setStyle("-fx-background-color: DAE6A2;");

        for (Description card: customer.getWishList()){
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(300);
            cardPane.setCenter(imageView);
            ImageView imageCopy = new ImageView(image);
            imageCopy.setPreserveRatio(true);
            imageCopy.setFitHeight(285);
            imageView.setOnMouseEntered(event -> {
                ScaleTransition st = CollectionScene.addScale(imageView);
                st.play();
            });
            imageView.setOnMouseExited(event -> {
                ScaleTransition st = CollectionScene.removeScale(imageView);
                st.play();
            });
            EventHandler<MouseEvent> eventHandlerBox = mouseEvent(imageCopy);
            imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);
            FadeTransition ft = new FadeTransition(Duration.millis(500), cardPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(true);
            ft.play();
            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }
        cardGrid.setFitToWidth(true);
        cardGrid.setFitToHeight(true);
        cardGrid.setContent(flowPane);
        cardGrid.setStyle("-fx-background-color: DAE6A2;");
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