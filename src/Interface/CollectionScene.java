package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import Interface.searchCard.SearchCardScene;
import TradeCenter.Card.Card;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXScrollPane;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
        Button buttonAdd= new Button("Add Card \uD83C\uDCCF");
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

            pane.setCenter(imageView);

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(imageView, "collection"));
                        }
                    };

            imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(5, 0, 5, 0));
        }
        buttonAdd.setOnAction(event -> {

            MainWindow.refreshDynamicContent(AddCardScene.display(customer));

        });
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(scroll);
        border.setTop(hbox);
        return border;
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

}

