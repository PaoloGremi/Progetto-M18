package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import Interface.searchCard.SearchCardScene;
import TradeCenter.Card.Card;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchDescriptionScene {



    static ArrayList<HashMap<Customer, Collection>> array;
    static Customer user;


    public static BorderPane display(ArrayList<HashMap<Customer, Collection>> cards, Customer customerThis){

        if(cards.isEmpty()){
            System.out.println("questo motivo");
        }
        array = cards;
        user=customerThis;
        FlowPane flow1 = new FlowPane();
        flow1.setStyle("-fx-background-color: DAE6A2;");
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

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow1);

        for(HashMap<Customer, Collection> collection : cards){

            for(Customer customer : collection.keySet()){

                if(customer.getUsername().equals(user.getUsername())) continue;

                Collection coll = collection.get(customer);

                for(Card card : coll){

                    BorderPane pane = new BorderPane();

                    pane.setPadding(new Insets(5,0,0,5));

                    Image image3 = SwingFXUtils.toFXImage(card.getDescription().getPic(),null);
                    ImageView cardV = new ImageView();
                    cardV.setImage(image3);
                    cardV.setPreserveRatio(true);
                    cardV.setFitHeight(285);

                    pane.setCenter(cardV);

                    HBox hbox1 = new HBox();
                    hbox1.setPadding(new Insets(10));
                    hbox1.setSpacing(10);
                    hbox1.setAlignment(Pos.CENTER);
                    Button buUser = new Button(customer.getUsername());
                    Button bTrade = new Button("Trade");
                    hbox1.getChildren().addAll(buUser, bTrade);
                    hbox1.setStyle("-fx-background-color: orange");
                    pane.setBottom(hbox1);

                    EventHandler<MouseEvent> eventHandlerBox =
                            new EventHandler<javafx.scene.input.MouseEvent>() {

                                @Override
                                public void handle(javafx.scene.input.MouseEvent e) {
                                    MainWindow.refreshDynamicContent(Demo.display(cardV, "searchDescription"));
                                }
                            };

                    cardV.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

                    buUser.setOnAction(event -> {
                        try {
                            System.out.println("welcome client");
                            Socket socket = new Socket("localhost", 8889);

                            System.out.println("Client connected");
                            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                            System.out.println("Ok");
                            os.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, customer.getUsername()));
                            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                            Customer returnMessage = (Customer) is.readObject();
                            MainWindow.refreshDynamicContent(OtherUserProfileScene.display(user, returnMessage));
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    });

                    bTrade.setOnAction(event -> {
                        System.out.println("welcome client");
                        Socket socket = null;
                        try {
                            socket = new Socket("localhost", 8889);
                            System.out.println("Client connected");
                            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                            System.out.println("Ok");
                            os.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, customer.getUsername()));
                            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                            Customer returnMessage = (Customer) is.readObject();
                            MainWindow.refreshDynamicContent(TradeScene.display(null,user,returnMessage,false));
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }


                    });



                    flow1.getChildren().add(pane);
                    flow1.setMargin(pane, new Insets(5, 0, 5, 0));

                }


            }


        }

        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(scroll);
        border.setTop(hbox);
        return border;
    }

    static BorderPane refresh() throws IOException {
        return display(array, user);
    }
}
