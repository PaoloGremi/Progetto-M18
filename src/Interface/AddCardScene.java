package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Card.Card;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *Interface of the Card Scene
 */

public class AddCardScene {


    static BorderPane display(Customer customer){
        ProgressIndicator bar = new ProgressIndicator();
        bar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        ScrollPane scroll= new ScrollPane();
        FlowPane flow = new FlowPane();
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: rgba(255,140,2,0.58);");
        bar.setPrefSize(200, 24);
        BorderPane pokeBorder = new BorderPane();
        ImageView pokePack = new ImageView(new Image("Interface/imagePack/PokemonPack.JPG"));
        pokePack.setPreserveRatio(true);
        pokePack.setFitHeight(350);
        pokeBorder.setCenter(pokePack);
        pokeBorder.setPadding(new Insets(20,0,0,0));
        BorderPane yugiBorder = new BorderPane();
        ImageView yugiPack = new ImageView(new Image("Interface/imagePack/YuGiOhPack.jpg"));
        yugiPack.setPreserveRatio(true);
        yugiPack.setFitHeight(350);
        yugiBorder.setCenter(yugiPack);
        yugiBorder.setPadding(new Insets(20,0,0,0));
        HBox packBox = new HBox();
        packBox.setAlignment(Pos.CENTER);
        packBox.setSpacing(100);
        packBox.getChildren().addAll(pokeBorder,yugiBorder);
        packBox.setPadding(new Insets(100,0,0,0));
        borderPane.setCenter(packBox);



        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        pokePack.setOnMouseClicked(event -> {
            Timeline task = loading(customer,vBox,scroll,borderPane,bar,MessageType.ADDCARDPOKEMON);
            task.playFromStart();

        });

        pokePack.setOnMouseMoved(event -> {
            pokePack.setFitHeight(400);
            yugiPack.setFitHeight(350);
        });

        yugiPack.setOnMouseClicked(event -> {
            Timeline task = loading(customer,vBox,scroll,borderPane,bar,MessageType.ADDCARDYUGI);
            task.playFromStart();
        });

        yugiPack.setOnMouseMoved(event -> {
            pokePack.setFitHeight(350);
            yugiPack.setFitHeight(400);
        });
        return borderPane;
    }



    private static FlowPane populateFlow(Customer customer, MessageType type) throws IOException, ClassNotFoundException, InterruptedException {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");
        Socket socket = new Socket(ServerIP.ip, ServerIP.port);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        System.out.println("Ok");
        os.writeObject(new MessageServer(type, customer.getId()));
        ArrayList<Card> cards = (ArrayList<Card>) is.readObject();
        Thread.sleep(100);
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

    private static HBox backButton(Customer customer){
        JFXButton back = new JFXButton("Back to Collection");
        back.setOnAction(event1 -> {
            try {
                MainWindow.refreshDynamicContent(CollectionScene.display(customer,customer.getUsername()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #b35e00;");
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.getChildren().addAll(back);
        hBox.setPadding(new Insets(5));

        return hBox;
    }

    /**
     * Method for the loading animation and the trade center's call for the opening of the pack
     * @param customer the customer
     * @param vBox
     * @param scroll
     * @param borderPane
     * @param bar
     * @param messageType
     * @return a time line in wich there are the loading's animation and the trade center's call for the openings of the packages
     */
    private static Timeline loading(Customer customer, VBox vBox, ScrollPane scroll, BorderPane borderPane, ProgressIndicator bar, MessageType messageType ){

        Timeline task = new Timeline(

                new KeyFrame(
                        Duration.ZERO,
                        event -> {

                            vBox.setAlignment(Pos.CENTER);
                            vBox.setFillWidth(true);
                            bar.setMaxSize(100,100);
                            bar.setMinSize(100,100);
                            Label label = new Label("Loading");
                            label.setScaleX(label.getScaleX()*1.5);
                            label.setScaleY(label.getScaleY()*1.5);
                            label.setAlignment(Pos.CENTER);
                            vBox.getChildren().addAll(bar, label);
                            MainWindow.addDynamicContent(vBox);
                        }
                ),

                new KeyFrame(
                        Duration.millis(500),
                        event -> {
                            try {
                                MainWindow.removeDynamicContent(vBox);
                                scroll.setContent(populateFlow(customer, messageType));
                                borderPane.setCenter(scroll);
                                borderPane.setBottom(backButton(customer));
                            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                )
        );

        return task;
    }



}
