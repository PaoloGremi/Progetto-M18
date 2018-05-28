package Interface;


import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;


public class SearchUserScene {

    /*
    static GridPane pannello = new GridPane();
    static TextField usernameCercato = new TextField();
    static Button cerca = new Button();
    static Button risultatiRicerca = new Button("Risultato della ricerca");
    static boolean trovato = false;


    static GridPane display() {
        pannello.setAlignment(Pos.TOP_CENTER);
        pannello.setEffect(new SepiaTone());
        pannello.prefWidth(600);
        pannello.prefHeight(331);

        pannello.addColumn(0);
        pannello.getColumnConstraints().get(0).setHgrow(Priority.SOMETIMES);
        pannello.getColumnConstraints().get(0).setMinWidth(10);
        pannello.getColumnConstraints().get(0).setMaxWidth(100);

        pannello.addRow(0);
        pannello.getRowConstraints().get(0).setMinHeight(36);
        pannello.getRowConstraints().get(0).setPrefHeight(46);
        pannello.getRowConstraints().get(0).setMaxHeight(63);

        pannello.addRow(1);
        pannello.getRowConstraints().get(1).setMinHeight(0);
        pannello.getRowConstraints().get(1).setPrefHeight(31);
        pannello.getRowConstraints().get(1).setMaxHeight(141);

        pannello.addRow(2);
        pannello.getRowConstraints().get(2).setVgrow(Priority.SOMETIMES);
        pannello.getRowConstraints().get(2).setMinHeight(0);
        pannello.getRowConstraints().get(2).setPrefHeight(254);
        pannello.getRowConstraints().get(2).setMaxHeight(257);


        cerca.setText("Cerca");
        cerca.setEffect(new SepiaTone());
        cerca.setMnemonicParsing(false);
        cerca.setPrefHeight(30);
        cerca.setPrefWidth(88);
        pannello.add(cerca, 0, 0);



        usernameCercato.setText("Inserisci lo username che vuoi cercare");
        usernameCercato.setPrefHeight(25);
        usernameCercato.setPrefWidth(321);
        pannello.add(usernameCercato, 0, 1);


        risultatiRicerca.setPrefHeight(200);
        risultatiRicerca.setPrefWidth(200);
        pannello.add(risultatiRicerca, 0, 2);


        pannello.setBackground(Background.EMPTY);


        cerca.setOnAction(event -> {
            String username = usernameCercato.getText();
            if(TradeCenter.searchCustomer(username)){
                trovato = true;
                risultatiRicerca.setText(username);
            }
        });


        risultatiRicerca.setOnAction(event -> {
            if (trovato){
                MainWindow.refreshDynamicContent(OtherUserProfileScene.display(TradeCenter.getCustomers().get(usernameCercato.getText())));
            }

        });


        return pannello;
    }

    */
    static BorderPane scene;
    static TextField searchString;
    static Button search;
    static FlowPane results;
    static ScrollPane resultsArea;

    static Customer myProfile;

    static BorderPane display(Customer mySelf){
        myProfile = mySelf;
        TradeCenter tradeCenter = new TradeCenter();        //todo vaccata, cambiare
        scene = new BorderPane();
        searchString = new TextField();
        searchString.setPrefWidth(800);
        search = new Button("Search");
        results = new FlowPane();
        resultsArea = new ScrollPane();


        search.setOnAction(event -> {
            String searchText = searchString.getText();
            if(searchText == null) searchText = "";     //handling null string

            ArrayList<Customer> users = tradeCenter.searchUsers(searchText);
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
