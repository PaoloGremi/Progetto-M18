package Interface;


import ClientServer.MessageServer;
import ClientServer.MessageType;
import TradeCenter.Customers.Customer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class SearchUserScene {

    static BorderPane scene;
    static TextField searchString;
    static Button search;
    static VBox results;
    static ScrollPane resultsArea;
    static FlowPane flow;
    static Pane pane;


    static Customer myProfile;

    static BorderPane display(Customer mySelf){
        myProfile = mySelf;
        scene = new BorderPane();
        searchString = new TextField();
        searchString.setPrefWidth(800);
        search = new Button("Search");
        results = new VBox();
        resultsArea = new ScrollPane();
        flow = new FlowPane();
        pane = new Pane();

        flow.setStyle("-fx-background-color: #beff8e;");
        results.setStyle("-fx-background-color: green;");
        pane.setStyle("-fx-background-color: #beff8e;");

        search.setOnAction(event -> {
            int count = 1;
            results.getChildren().removeAll(results.getChildren());
            String searchText = searchString.getText();
            if(searchText == null) searchText = "";     //handling null string

            try {
                Socket socket = new Socket("localhost", 8889);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject(new MessageServer(MessageType.SEARCHUSER, searchText, mySelf));
                ArrayList<Customer> users = (ArrayList<Customer>)(inputStream.readObject());
                socket.close();
                if(users != null){
                    for(Customer customer : users){
                        TextField user = new TextField();
                        HBox hBox = new HBox();
                        //Text useraname = new Text();
                        if(count % 2 == 0){
                            //useraname.setText(customer.getUsername());
                            user.setText(customer.getUsername());
                            user.setStyle("-fx-background-color: DAE6A2;");
                            hBox.getChildren().add(user);
                            hBox.setStyle("-fx-background-color: DAE6A2;");
                            count++;
                        }
                        else {
                            user.setText(customer.getUsername());
                            user.setStyle("-fx-background-color: orange;");
                            hBox.getChildren().add(user);
                            hBox.setStyle("-fx-background-color: orange;");
                            count++;
                        }


                        EventHandler<MouseEvent> eventHandlerBox =
                                new EventHandler<javafx.scene.input.MouseEvent>() {

                                    @Override
                                    public void handle(javafx.scene.input.MouseEvent e) {
                                        MainWindow.refreshDynamicContent(OtherUserProfileScene.display(myProfile, customer));
                                    }
                                };

                        user.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);
                        user.setAlignment(Pos.CENTER);
                        user.setPrefSize(100,10);
                        //hBox.setFillHeight(true);
                        hBox.setPadding(new Insets(5,400,5,397));
                        results.getChildren().add(hBox);
                    }
                    results.setAlignment(Pos.CENTER);
                    results.setPadding(new Insets(5));
                    results.setFillWidth(true);
                    flow.getChildren().removeAll(flow.getChildren());
                    flow.getChildren().add(results);
                    resultsArea.setFitToHeight(true);
                    resultsArea.setFitToWidth(true);
                    resultsArea.setPadding(new Insets(5));
                    resultsArea.setStyle("-fx-background-color: #beff8e;");
                    resultsArea.setContent(flow);
                    scene.setCenter(flow);
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
        scene.setCenter(pane);
        return scene;
    }

}
