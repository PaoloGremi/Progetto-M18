package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import TradeCenter.Card.Card;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class AddCardScene {


    static BorderPane display(Customer customer){

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: DAE6A2;");
        JFXButton open = new JFXButton("Open Pack");
        borderPane.setCenter(open);
        ScrollPane scroll= new ScrollPane();
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);
        open.setOnAction(event -> {

            try {
                Socket socket = new Socket("localhost", 8889);
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                System.out.println("Ok");
                os.writeObject(new MessageServer(MessageType.ADDCARD, customer));
                ArrayList<Card> cards = (ArrayList<Card>) is.readObject();
                Thread.sleep(500);
                socket.close();
                for(Card card : cards){
                    BorderPane pane = new BorderPane();
                    pane.setPadding(new Insets(5,0,0,5));
                    Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(),null);
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(285);
                    pane.setCenter(imageView);
                    flow.getChildren().add(pane);
                }
                borderPane.setCenter(scroll);
                JFXButton back = new JFXButton("Back to Collection");
                back.setOnAction(event1 -> {
                    try {
                        MainWindow.refreshDynamicContent(CollectionScene.display(customer,customer.getUsername(), false));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                HBox hBox = new HBox();
                hBox.setStyle("-fx-background-color: #b35e00;");
                hBox.setAlignment(Pos.BASELINE_RIGHT);
                hBox.getChildren().add(back);
                hBox.setPadding(new Insets(5));
                borderPane.setBottom(hBox);

            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        return borderPane;
    }

}
