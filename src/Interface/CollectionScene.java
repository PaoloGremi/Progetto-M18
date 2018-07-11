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

    static BorderPane display(Customer customer1, String username, boolean searchFlag) throws IOException, ClassNotFoundException {

        System.out.println("welcome client");
        Socket socket = new Socket(ServerIP.ip, ServerIP.port);
        socket.setTcpNoDelay(true);
        System.out.println("Client connected");
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Ok");
        os.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, username));
        ObjectInputStream is1 = new ObjectInputStream(socket.getInputStream());
        Customer returnMessage1 = (Customer) is1.readObject();
        socket.close();


        cust=returnMessage1;
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
        if(!searchFlag) {
            hbox.getChildren().add(buttonAdd);
        }

        FlowPane flow = new FlowPane();

        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        for(Card file2 : cust.getCollection()){
            BorderPane pane = new BorderPane();

            pane.setPadding(new Insets(5,0,0,5));

            Image image3 = SwingFXUtils.toFXImage(file2.getDescription().getPic(),null);
            ImageView card = new ImageView();
            card.setImage(image3);
            card.setPreserveRatio(true);
            card.setFitHeight(285);

            pane.setCenter(card);

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(card, "collection"));
                        }
                    };

            card.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

            if(searchFlag){
                HBox hbox1 = new HBox();
                hbox1.setPadding(new Insets(10));
                hbox1.setSpacing(10);
                hbox1.setAlignment(Pos.CENTER);
                Button buUser = new Button(username);
                Button bTrade = new Button("Trade");
                hbox1.getChildren().addAll(buUser, bTrade);
                hbox1.setStyle("-fx-background-color: orange");
                pane.setBottom(hbox1);
                buUser.setOnAction(event -> {
                    try {
                        os.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, username));
                        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                        Customer returnMessage = (Customer) is.readObject();
                        MainWindow.refreshDynamicContent(OtherUserProfileScene.display(customer1, returnMessage));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });

            }

            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(5, 0, 5, 0));
        }
        buttonAdd.setOnAction(event -> {

            MainWindow.refreshDynamicContent(AddCardScene.display(customer1));

        });
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(scroll);
        border.setTop(hbox);
        return border;
    }

    static BorderPane refresh() throws IOException, ClassNotFoundException {
        return display(cust ,user,false);
    }

}

