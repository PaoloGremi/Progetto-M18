package Interface.searchCard;
import ClientServer.MessageServer;
import ClientServer.MessageType;
import Interface.MainWindow;
import Interface.OtherUserProfileScene;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

public class DescriptionFounded {


    /**
     *
     * @param user :user logged
     * @param map :
     * @return
     */
    static ScrollPane display(Customer user, HashMap<Description,ArrayList<String>> map) {
        ScrollPane scrollPane=new ScrollPane();
        BorderPane scene = new BorderPane();
        if(map.isEmpty()){//TODO controlla
            Label l=new Label("  No Descriptions Founded  ");
            scrollPane.setContent(l);
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
                        socket1 = new Socket("localhost", 8889);
                        System.out.println("Client connected: Adding to WishList");
                        ObjectOutputStream os = new ObjectOutputStream(socket1.getOutputStream());
                        System.out.println("Ok");
                        os.writeObject(new MessageServer(MessageType.ADDDESCRTOWHISLIST, currentDescr,user.getUsername()));
                        Thread.sleep(130);
                        socket1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    figureContainer.getChildren().remove(addToWhishList);
                    figureContainer.getChildren().add(new Button("!Added!"));
                    //MainWindow.refreshDynamicContent(WishListScene.display(user.getWishList(),user));

                }
            });

            figureContainer.getChildren().addAll(cardV,addToWhishList);

            descriptionBox.getChildren().add(figureContainer);
            //Button
            ObservableList<String> customersList = FXCollections.observableArrayList();
            customersList.addAll(customers);
            JFXListView<String> usersList = new JFXListView<>();
            usersList.getItems().addAll(customers);
            if (!customersList.isEmpty()) {
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
