package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import Interface.searchCard.SearchCardScene;
import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;
import com.jfoenix.controls.JFXListView;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Button;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchDescriptionScene {



    static ArrayList<String> array;
    static Customer user;
    static Description cardDescription;


    public static BorderPane display(Description description, ArrayList<String> customers, Customer customerThis){

        if(customers.isEmpty()){
            System.out.println("questo motivo");
        }
        array = customers;
        user=customerThis;
        cardDescription = description;
        BorderPane border = new BorderPane();
        HBox hbox = new HBox();
        Button bBack = new Button("\u2B8C");
        Button bSearch = new Button("Global Search");
        hbox.setPadding(new Insets(5));
        hbox.setSpacing(600);
        hbox.getChildren().addAll(bBack, bSearch);
        hbox.setStyle("-fx-background-color: orange");

        bSearch.setOnAction(event -> {
            MainWindow.refreshDynamicContent(SearchCardScene.display(customerThis));
        });

        bBack.setOnAction(event -> {
            MainWindow.refreshDynamicContent(WishListScene.refresh());
        });

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #80c73f");
        BorderPane pane = new BorderPane();
        Image image3 = SwingFXUtils.toFXImage(description.getPic(),null);
        ImageView cardV = new ImageView();
        cardV.setImage(image3);
        cardV.setPreserveRatio(true);
        cardV.setFitHeight(285);
        pane.setCenter(cardV);

        ScrollPane scroll = new ScrollPane();


        JFXListView<String> customerList = new JFXListView<>();
        customerList.getItems().addAll(customers);

        if(!customers.isEmpty()){
            EventHandler<MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {
                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            String otherCustomer = customerList.getSelectionModel().getSelectedItem();

                            MainWindow.refreshDynamicContent(OtherUserProfileScene.display(SearchUserScene.retrieveCustomer(customerThis.getUsername()), SearchUserScene.retrieveCustomer(otherCustomer)));
                        }
                    };

            //todo se funziona listener modificare tradeScene (magari metodo refresh)in modo che si apra come l'ultima offerta fatta e non come fosse la prima

            customerList.setOnMouseClicked(eventHandlerBox);
        }

        vBox.getChildren().addAll(pane, scroll);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(customerList);
        scroll.setMinSize(280,280);
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(vBox);
        border.setTop(hbox);
        return border;
    }

    static BorderPane refresh() throws IOException {
        return display(cardDescription, array, user);
    }
}
