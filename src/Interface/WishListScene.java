
package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
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

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class WishListScene{

    static ArrayList<Description> wish;
    static Customer user;

    public static BorderPane display(ArrayList<Description> wishList, Customer customer)  {

        user=customer;
        wish=wishList;

        BorderPane border = new BorderPane();
        FlowPane flow = new FlowPane();
        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10));
        hBox2.setSpacing(10);
        hBox2.setStyle("-fx-background-color: orange");
        hBox2.setAlignment(Pos.CENTER);
        TextFlow textFlow = new TextFlow();
        Text text = new Text(customer.getUsername() +"'s wish list");
        text.setStyle("-fx-font-weight: bold");
        textFlow.getChildren().add(text);
        hBox2.getChildren().add(textFlow);


        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        HBox hbox1;


        for(Description file2 : wishList){
            BorderPane pane = new BorderPane();
            Pane pane2 = new Pane();
            pane.setPadding(new Insets(0));

            hbox1 = new HBox();
            hbox1.setPadding(new Insets(10));
            hbox1.setSpacing(10);
            hbox1.setStyle("-fx-background-color: orange");

            Button button1 = new Button("Remove "+ "\uD83D\uDD71");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button1);

            Button button2 = new Button("Search " + "\uD83D\uDD0D");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button2);

            
            Image image3 = SwingFXUtils.toFXImage(file2.getPic(),null);
            ImageView card = new ImageView();
            card.setImage(image3);
            pane2.getChildren().add(card);
            card.setPreserveRatio(true);
            card.setFitHeight(313);

            pane.setCenter(card);
            pane.setBottom(hbox1);

            button1.setOnAction(event -> {
                flow.getChildren().remove(pane);
                Socket socket;
                try {
                    socket = new Socket(ServerIP.ip, ServerIP.port);
                    System.out.println("Client connected");
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("Ok");
                    os.writeObject(new MessageServer(MessageType.REMOVEWISH, customer.getId(), file2));
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
                            MainWindow.refreshDynamicContent(Demo.display(card, "wish"));
                        }
                    };

            card.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

            button2.setOnAction(event -> {
                System.out.println("welcome client");
                Socket socket = null;
                try {
                    socket = new Socket(ServerIP.ip, ServerIP.port);
                    System.out.println("Client connected");
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("Ok");
                    os.writeObject(new MessageServer(MessageType.SEARCHDESCRIPTION, file2));
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    ArrayList<String> returnMessage = (ArrayList<String>) is.readObject();
                    MainWindow.refreshDynamicContent(SearchDescriptionScene.display(file2 ,returnMessage, customer));
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            });

            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(5, 0, 5, 0));
        }
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(scroll);
        border.setTop(hBox2);
        return border;
    }



    static BorderPane refresh(){
        return display(wish, user);
    }

}
