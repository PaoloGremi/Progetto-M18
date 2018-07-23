
package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * the interface of the Whishlist view
 */

public class WishListScene{

    static ArrayList<Description> wish;
    static Customer user;

    /**
     * constructor of the scene
     * @param wishList a whishlist
     * @param customer the customer
     * @return the scene
     */
    public static BorderPane display(ArrayList<Description> wishList, Customer customer)  {

        user=customer;
        wish=wishList;

        BorderPane border = new BorderPane();
        FlowPane flow = new FlowPane();
        HBox titleBox = new HBox();
        titleBox.setPadding(new Insets(10));
        titleBox.setSpacing(10);
        titleBox.setStyle("-fx-background-color: orange");
        titleBox.setAlignment(Pos.CENTER);
        TextFlow textFlow = new TextFlow();
        Text text = new Text(customer.getUsername() +"'s wish list");
        text.setStyle("-fx-font-weight: bold");
        textFlow.getChildren().add(text);
        titleBox.getChildren().add(textFlow);


        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        for(Description description : wishList){
            BorderPane pane = new BorderPane();
            Pane pane2 = new Pane();
            pane.setPadding(new Insets(0));

            HBox buttonBox = new HBox();
            buttonBox.setPadding(new Insets(10));
            buttonBox.setSpacing(10);
            buttonBox.setStyle("-fx-background-color: orange");

            JFXButton remove = new JFXButton("Remove "+ "\uD83D\uDD71");
            remove.setButtonType(JFXButton.ButtonType.RAISED);
            remove.setPrefSize(100, 20);
            buttonBox.getChildren().add(remove);

            JFXButton search = new JFXButton("Search " + "\uD83D\uDD0D");
            search.setButtonType(JFXButton.ButtonType.RAISED);
            remove.setPrefSize(100, 20);
            buttonBox.getChildren().add(search);

            
            Image image = SwingFXUtils.toFXImage(description.getPic(),null);
            ImageView card = new ImageView();
            card.setImage(image);
            pane2.getChildren().add(card);
            card.setPreserveRatio(true);
            card.setFitHeight(313);
            ImageView cardCopy = new ImageView(image);
            cardCopy.setPreserveRatio(true);
            cardCopy.setFitHeight(313);

            card.setOnMouseEntered(event -> {
                TranslateTransition translation = new TranslateTransition(Duration.millis(100), card);
                translation.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                translation.setByY(-50);
                translation.setAutoReverse(true);
                translation.setCycleCount(2);
                translation.play();
            });
            card.setOnMouseExited(event -> {
                TranslateTransition translation = new TranslateTransition(Duration.millis(100), card);
                translation.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                translation.setFromY(card.getY());
                translation.setToY(-card.getY());
                translation.setAutoReverse(true);
                translation.setCycleCount(1);
                translation.play();
            });
            pane.setCenter(card);
            pane.setBottom(buttonBox);
            remove.setOnAction(event -> {
                flow.getChildren().remove(pane);
                Socket socket;
                try {
                    socket = new Socket(ServerIP.ip, ServerIP.port);
                    System.out.println("Client connected");
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("Ok");
                    os.writeObject(new MessageServer(MessageType.REMOVEWISH, customer.getId(), description));
                    try {
                        Thread.sleep(55);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(cardCopy, "wish"));
                        }
                    };

            card.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

            search.setOnAction(event -> {
                System.out.println("welcome client");
                Socket socket = null;
                try {
                    socket = new Socket(ServerIP.ip, ServerIP.port);
                    System.out.println("Client connected");
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("Ok");
                    os.writeObject(new MessageServer(MessageType.SEARCHDESCRIPTION, description, customer.getUsername()));
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    ArrayList<String> returnMessage = (ArrayList<String>) is.readObject();
                    MainWindow.refreshDynamicContent(SearchDescriptionScene.display(description ,returnMessage, customer));
                    socket.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            });

            ScaleTransition st = new ScaleTransition(Duration.millis(500),card);
            st.setFromX(0);
            st.setFromY(0);
            st.setToX(1);
            st.setToY(1);
            st.setAutoReverse(true);
            st.play();
            FadeTransition ft = new FadeTransition(Duration.millis(700), buttonBox);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(true);
            ft.play();
            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(5, 0, 5, 0));
        }
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(scroll);
        border.setTop(titleBox);
        return border;
    }


    /**
     * method to go back at the first view
     * @return display the scene
     */
    static BorderPane refresh(){
        return display(wish, user);
    }

}
