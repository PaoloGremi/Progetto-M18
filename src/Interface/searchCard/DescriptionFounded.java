package Interface.searchCard;
import TradeCenter.Card.Description;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DescriptionFounded {


    /**
     *
     * @param user:user logged
     * @param map:
     * @return
     */
    static BorderPane display(Customer user, HashMap<PokemonDescription,ArrayList<String>> map) {
        BorderPane scene = new BorderPane();
        if(map.isEmpty()){
            System.out.println("Nessun user ha questa description");
        }
        ScrollPane scrollPane=new ScrollPane();
        VBox mainVbox=new VBox();

        for (Map.Entry<PokemonDescription, ArrayList<String>> entry : map.entrySet()) {
            Description currentDescr=entry.getKey();
            ArrayList<String> customers=entry.getValue();

            //interfaceElement
            HBox descriptionBox = new HBox();
            descriptionBox.setPadding(new Insets(5,0,0,5));
            //Image
            Image image3 = SwingFXUtils.toFXImage(currentDescr.getPic(),null);
            ImageView cardV = new ImageView();
            cardV.setImage(image3);
            cardV.setPreserveRatio(true);
            cardV.setFitHeight(285);

            descriptionBox.getChildren().add(cardV);
            //Button
            ObservableList<String> customersList = FXCollections.observableArrayList();
            customersList.addAll(customers);
            JFXListView<String> usersList = new JFXListView<>();
            usersList.getItems().addAll(customers);
            usersList.setEditable(true);

            descriptionBox.getChildren().add(usersList);

            mainVbox.getChildren().add(descriptionBox);


               /* if(!customers.isEmpty()){
                    EventHandler<MouseEvent> eventHandlerBox =
                            new EventHandler<javafx.scene.input.MouseEvent>() {
                                @Override
                                public void handle(javafx.scene.input.MouseEvent e) {
                                    String otherCustomer = usersList.getSelectionModel().getSelectedItem();

                                    MainWindow.refreshDynamicContent(OtherUserProfileScene.display(retrieveCustomer(mySelf.getUsername()), retrieveCustomer(otherCustomer)));


                            };

                    //todo se funziona listener modificare tradeScene (magari metodo refresh)in modo che si apra come l'ultima offerta fatta e non come fosse la prima

                    usersList.setOnMouseClicked(eventHandlerBox);*/

            }
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        /*scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);*/
        //scrollPane.setPrefSize(405,233);
        scrollPane.setPadding(new Insets(5));
        scrollPane.setStyle("-fx-background-color: #beff8e;");

        scrollPane.setContent(mainVbox);


        scene.getChildren().addAll(scrollPane);
        return scene;


        }


    }

