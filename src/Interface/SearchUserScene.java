package Interface;


import TradeCenter.TradeCenter;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.SepiaTone;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.ArrayList;


public class SearchUserScene {

    GridPane pannello = new GridPane();
    TextField usernameCercato = new TextField();
    Button cerca = new Button();
    Button risultatiRicerca = new Button("Risultato della ricerca");



    public GridPane display() {
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
            if(TradeCenter.getCustomers().containsKey(username)){
                risultatiRicerca.setText(username);
            }
        });

        // A questo punto bisogna che il pulsante "risultatiRicerca" apra la scena OtherUserProfileScene, fatelo come meglio credete
        risultatiRicerca.setOnAction(event -> {

        });


        return pannello;
    }

}
