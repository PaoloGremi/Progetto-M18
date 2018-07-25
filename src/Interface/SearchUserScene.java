package Interface;


import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Card.Card;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Interface for the Searching of Users
 */


public class SearchUserScene {

    static Customer myProfile;

    /**
     * display the scene
     * @param mySelf the customer itself
     * @return the scene
     */
    static BorderPane display(Customer mySelf){
        myProfile = mySelf;
        BorderPane scene = new BorderPane();
        JFXTextField searchString = new JFXTextField();
        searchString.setPrefWidth(800);
        JFXButton search = new JFXButton("Search");
        search.setButtonType(JFXButton.ButtonType.RAISED);
        searchString.setStyle("-fx-background-color: white;");
        VBox results = new VBox();
        ScrollPane resultsArea = new ScrollPane();
        FlowPane flow = new FlowPane();
        Pane pane = new Pane();

        flow.setStyle("-fx-background-color: DAE6A2;");
        results.setStyle("-fx-background-color: DAE6A2;");
        pane.setStyle("-fx-background-color: DAE6A2;");

        search.setOnAction(event -> {
            results.getChildren().removeAll(results.getChildren());
            String searchText = searchString.getText();
            if(searchText == null) searchText = "";     //handling null string

            try {
                Socket socket = new Socket(ServerIP.ip, ServerIP.port);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject(new MessageServer(MessageType.SEARCHUSER, searchText, mySelf.getUsername()));
                ArrayList<String> users = (ArrayList<String>)(inputStream.readObject());
                socket.close();
                if(users != null && !users.isEmpty()){
                    ObservableList<String> customers = FXCollections.observableArrayList();
                    customers.addAll(users);
                    JFXListView<String> usersList = new JFXListView<>();
                    usersList.getItems().addAll(users);
                    if(!customers.isEmpty()){
                        EventHandler<MouseEvent> eventHandlerBox = mouseEvent(usersList);
                        usersList.setOnMouseClicked(eventHandlerBox);
                    }
                    usersList.setEditable(true);
                    results.setAlignment(Pos.CENTER);
                    results.setPadding(new Insets(5));
                    results.setFillWidth(true);
                    flow.getChildren().removeAll(flow.getChildren());
                    flow.getChildren().add(results);
                    resultsArea.setFitToHeight(true);
                    resultsArea.setFitToWidth(true);
                    resultsArea.setPadding(new Insets(5));
                    resultsArea.setStyle("-fx-background-color: DAE6A2;");
                    resultsArea.setContent(usersList);
                    scene.setCenter(resultsArea);
                } else {
                    MainWindow.addDynamicContent(InfoScene.display("No Customers Found", "Interface/imagePack/2000px-Simple_Alert.svg.png", true));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            });

        HBox topScene = new HBox();
        topScene.setAlignment(Pos.CENTER);
        topScene.setStyle("-fx-background-color: orange");
        topScene.setSpacing(5.0);
        topScene.setPadding(new Insets(5));
        topScene.getChildren().addAll(searchString, search);
        scene.setTop(topScene);
        scene.setCenter(pane);
        return scene;
    }
/**
 * a method that handle the mouse event when you click on the cell of the listview
 */
    public static EventHandler<MouseEvent> mouseEvent(JFXListView<String> usersList){
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String otherCustomer = usersList.getSelectionModel().getSelectedItem();
                if(otherCustomer != null) {
                    MainWindow.refreshDynamicContent(OtherUserProfileScene.display(retrieveCustomer(myProfile.getUsername()), retrieveCustomer(otherCustomer)));
                }
            }

        };
        return event;
    }

    /**
     * a method that find a customer
     * @param customer the searched customer
     * @return the customer
     */
    public static Customer retrieveCustomer(String customer){
        Customer updatedCustomer = null;
        Socket socket = null;
        try {
            socket = new Socket(ServerIP.ip, ServerIP.port);
            socket.setTcpNoDelay(true);
            //socket.setKeepAlive(true);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, customer));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            updatedCustomer = (Customer)is.readObject();
            os.flush();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return updatedCustomer;
    }

}
