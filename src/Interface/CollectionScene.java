package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import Interface.searchCard.SearchCardScene;
import TradeCenter.Card.Card;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CollectionScene{
    static Customer cust;
    static String user;

    //static HBox hbox;

    /**
     * Shows the collection of the customer
     * @param customer Customer logged
     * @param username Username of the customer
     * @return BorderPane with the collection
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static BorderPane display(Customer customer, String username) throws IOException, ClassNotFoundException {


        cust=SearchUserScene.retrieveCustomer(customer.getUsername());
        user=username;
        ScrollPane scroll;
        HBox hbox;
        BorderPane border = new BorderPane();
        JFXButton buttonAdd= new JFXButton("Add Card \uD83C\uDCCF");
        buttonAdd.setButtonType(JFXButton.ButtonType.RAISED);
        hbox = new HBox();
        hbox.setPadding(new Insets(5));
        hbox.setSpacing(580);
        hbox.setStyle("-fx-background-color: orange");

        TextFlow textFlow = new TextFlow();
        textFlow.setPadding(new Insets(5));
        Text text = new Text(username +"'s collection");
        text.setStyle("-fx-font-weight: bold");
        textFlow.getChildren().add(text);
        textFlow.setPrefWidth(200);
        hbox.getChildren().add(textFlow);
        hbox.getChildren().add(buttonAdd);


        FlowPane flow = new FlowPane();

        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);
        for(Card card : cust.getCollection()){

            BorderPane pane = new BorderPane();

            pane.setPadding(new Insets(5,0,0,5));

            Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(),null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(285);
            ImageView imageCopy = new ImageView(image);
            imageCopy.setPreserveRatio(true);
            imageCopy.setFitHeight(285);

            pane.setCenter(imageView);

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(imageCopy, "collection"));
                        }
                    };

            imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

            imageView.setOnMouseEntered(event -> {
                ScaleTransition translation = addScale(imageView);
                translation.play();
            });

            imageView.setOnMouseExited(event -> {
                ScaleTransition translation = removeScale(imageView);
                translation.play();
            });
            ScaleTransition ft = new ScaleTransition(Duration.millis(500), pane);
            ft.setFromX(0);
            ft.setFromY(0);
            ft.setToX(1);
            ft.setToY(1);
            ft.setAutoReverse(true);
            ft.play();
            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(5, 0, 5, 0));
        }
        buttonAdd.setOnAction(event -> {
            Image image = new Image("Interface/imagePack/PackOpening.jpg");
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(600);
            MainWindow.refreshDynamicContent(imageView);
            MainWindow.addDynamicContent(AddCardScene.display(customer));

        });
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(scroll);
        border.setTop(hbox);
        canPack(buttonAdd);
        return border;
    }

    /**
     * Adds scale animation
     * @param node Node to add animation
     * @return The transition
     */
    public static ScaleTransition addScale(Node node){
        ScaleTransition translation = new ScaleTransition(Duration.millis(100), node);
        translation.setFromX(1);
        translation.setFromY(1);
        translation.setToX(1.05);
        translation.setToY(1.05);
        translation.setAutoReverse(true);
        translation.setCycleCount(1);
        return translation;
    }

    /**
     * Adds scale animation
     * @param node Node to add animation
     * @return The transition
     */
    public static ScaleTransition removeScale(Node node){
        ScaleTransition translation = new ScaleTransition(Duration.millis(100), node);
        translation.setFromX(1.05);
        translation.setFromY(1.05);
        translation.setToX(1);
        translation.setToY(1);
        translation.setAutoReverse(true);
        translation.setCycleCount(1);
        return translation;
    }

    /**
     * Refresh the scene
     * @return BorderPane with the collection
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static BorderPane refresh() throws IOException, ClassNotFoundException {
        return display(cust ,user);
    }

    private static void canPack(JFXButton button){
        if(cust.getCollection().getSet().size()>7){
            button.setDisable(true);
        }
    }

}

