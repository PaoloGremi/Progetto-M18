package Interface.searchCard;
import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import Interface.*;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Interface.SearchUserScene.retrieveCustomer;

public class DescriptionFound {


    /**
     * Display Descriptions found and their Customers
     *
     * @param user :user logged
     * @param map : Descriptions found and Customers who have them
     * @return
     */
    static ScrollPane display(Customer user, HashMap<Description,ArrayList<String>> map) {
        ScrollPane scrollPane=new ScrollPane();
        BorderPane scene = new BorderPane();
        if(map.isEmpty()){
            MainWindow.addDynamicContent(InfoScene.display("No Descriptions Found","Interface/imagePack/infoSign.png", true));
            return scrollPane;

        }
        VBox mainVbox=new VBox();
        mainVbox.setStyle("-fx-background-color: #beff8e;");
        mainVbox.setPrefHeight(100);

        for (Map.Entry<Description, ArrayList<String>> entry : map.entrySet()) {
            Description currentDescr = entry.getKey();
            ArrayList<String> customers = entry.getValue();

            //interfaceElement
            HBox descriptionBox = new HBox();
            VBox figureContainer=new VBox();
            figureContainer.setAlignment(Pos.CENTER);
            figureContainer.setStyle("-fx-background-color: orange");
            descriptionBox.setPadding(new Insets(5, 0, 0, 5));
            //Image
            Image image3 = SwingFXUtils.toFXImage(currentDescr.getPic(), null);
            ImageView cardV = new ImageView();
            cardV.setImage(image3);
            cardV.setPreserveRatio(true);
            cardV.setFitHeight(285);
            //Add to Whishlist button
            Button addToWhishList=new Button("Add to Whishlist");
            //addToWhishList.setAlignment(Pos.CENTER);
            addToWhishList.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Socket socket1;
                    try {
                        socket1 = new Socket(ServerIP.ip,ServerIP.port);
                        System.out.println("Client connected: Adding to WishList");
                        ObjectOutputStream os = new ObjectOutputStream(socket1.getOutputStream());
                        os.writeObject(new MessageServer(MessageType.ADDDESCRTOWHISLIST, currentDescr,user.getUsername()));
                        Thread.sleep(600);
                        socket1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Customer customerUpdated=SearchUserScene.retrieveCustomer(user.getUsername());
                    MainWindow.refreshDynamicContent(WishListScene.display(customerUpdated.getWishList(),customerUpdated));

                }
            });
            HBox buttonContainer=new HBox();
            buttonContainer.setPadding(new Insets(5));
            buttonContainer.getChildren().add(addToWhishList);
            buttonContainer.setAlignment(Pos.CENTER);

            figureContainer.getChildren().addAll(cardV,buttonContainer);

            descriptionBox.getChildren().add(figureContainer);
            //List of customers

            ObservableList<String> customersList = FXCollections.observableArrayList();
            customersList.addAll(customers);
            JFXListView<String> usersList = new JFXListView<>();
            if (!customersList.isEmpty()) {
                usersList.getItems().addAll(customers);
                EventHandler<MouseEvent> eventHandlerBox =
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(javafx.scene.input.MouseEvent e) {
                                String otherCustomer = usersList.getSelectionModel().getSelectedItem();

                                MainWindow.refreshDynamicContent(OtherUserProfileScene.display(retrieveCustomer(user.getUsername()), retrieveCustomer(otherCustomer)));
                            }
                        };
                usersList.setOnMouseClicked(eventHandlerBox);
            }
            else {
                usersList.getItems().add("       No User found");
            }
            usersList.setEditable(true);
            descriptionBox.getChildren().add(usersList);

            mainVbox.getChildren().add(descriptionBox);
            }
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setPadding(new Insets(5));
            scrollPane.setStyle("-fx-background-color: #beff8e;");
            scrollPane.setContent(mainVbox);

            scene.getChildren().addAll(scrollPane);
            return scrollPane;
            }
        }
