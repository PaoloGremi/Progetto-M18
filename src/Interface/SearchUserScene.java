package Interface;


import ClientServer.MessageServer;
import ClientServer.MessageType;
import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class SearchUserScene {

    static BorderPane scene;
    static TextField searchString;
    static Button search;
    static FlowPane results;
    static ScrollPane resultsArea;

    static Customer myProfile;

    static BorderPane display(Customer mySelf){
        myProfile = mySelf;
        scene = new BorderPane();
        searchString = new TextField();
        searchString.setPrefWidth(800);
        search = new Button("Search");
        results = new FlowPane();
        resultsArea = new ScrollPane();


        search.setOnAction(event -> {
            String searchText = searchString.getText();
            if(searchText == null) searchText = "";     //handling null string

            try {
                Socket socket = new Socket("localhost", 8889);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject(new MessageServer(MessageType.SEARCHUSER, searchText));
                ArrayList<Customer> users = (ArrayList<Customer>)(inputStream.readObject());
                socket.close();
                if(users != null){
                    for(Customer customer : users){
                        Label user = new Label(customer.getUsername());

                        EventHandler<MouseEvent> eventHandlerBox =
                                new EventHandler<javafx.scene.input.MouseEvent>() {

                                    @Override
                                    public void handle(javafx.scene.input.MouseEvent e) {
                                        MainWindow.refreshDynamicContent(OtherUserProfileScene.display(myProfile, customer));
                                    }
                                };

                        user.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

                        results.getChildren().add(user);
                    }
                    resultsArea.setContent(results);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            });
        HBox topScene = new HBox();
        topScene.setStyle("-fx-background-color: #aa12ff");
        topScene.setSpacing(5.0);
        topScene.setPadding(new Insets(5));
        topScene.getChildren().addAll(searchString, search);

        scene.setTop(topScene);
        scene.setCenter(resultsArea);
        return scene;
    }

}
