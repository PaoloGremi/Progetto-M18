package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import TradeCenter.Card.Card;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;

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
        ImageView pokePack = new ImageView(new Image("Interface/PokemonPack.JPG"));
        pokePack.setPreserveRatio(true);
        pokePack.setFitHeight(350);
        ImageView yugiPack = new ImageView(new Image("Interface/YuGiOhPack.jpg"));
        yugiPack.setPreserveRatio(true);
        yugiPack.setFitHeight(350);
        HBox packBox = new HBox();
        packBox.setAlignment(Pos.CENTER);
        packBox.setSpacing(50);
        packBox.getChildren().addAll(pokePack,yugiPack);
        borderPane.setCenter(packBox);
        ScrollPane scroll= new ScrollPane();
        FlowPane flow = new FlowPane();


        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        pokePack.setOnMouseClicked(event -> {
            try {
                scroll.setContent(populateFlow(customer, MessageType.ADDCARDPOKEMON));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            borderPane.setCenter(scroll);
            borderPane.setBottom(backButton(customer));
        });

        yugiPack.setOnMouseClicked(event -> {
            try {
                scroll.setContent(populateFlow(customer, MessageType.ADDCARDYUGI));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            borderPane.setCenter(scroll);
            borderPane.setBottom(backButton(customer));
        });
        /*open.setOnAction(event -> {


            try {
                scroll.setContent(populateFlow(customer));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
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

        });*/
        return borderPane;
    }



    static FlowPane populateFlow(Customer customer, MessageType type) throws IOException, ClassNotFoundException {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");
        Socket socket = new Socket("localhost", 8889);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        System.out.println("Ok");
        os.writeObject(new MessageServer(type, customer));
        ArrayList<Card> cards = (ArrayList<Card>) is.readObject();
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
        return flow;
    }

    public static HBox backButton(Customer customer){
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

        return hBox;
    }



}
