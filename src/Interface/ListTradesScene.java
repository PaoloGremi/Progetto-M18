package Interface;

import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Trade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;


public class ListTradesScene {

    static BorderPane mainPane;
    static ScrollPane scrollArea;


    static BorderPane display(Customer myCustomer){
        mainPane = new BorderPane();
        scrollArea = new ScrollPane();

        TradeCenter tradeCenter = new TradeCenter();
        ArrayList<Trade> userTrades = tradeCenter.showUserTrades(myCustomer);   //todo porcata poi far cambiare

//todo vedere come mettere nome ai bottoni che si creano
        ObservableList<Trade> trades = FXCollections.observableArrayList();
        trades.addAll(userTrades);

        //todo mettere listener che quando schiaccio un trade mi fa vedere o il trade (se attivo), o altro se finito--> fare relativa scena
        ListView<Trade> tradeList = new ListView<Trade>(trades);
        tradeList.setPrefSize(200, 250);
        tradeList.setEditable(true);

        //todo addare dei trade e vedere se funziona

        scrollArea.setContent(tradeList);
        mainPane.setCenter(scrollArea);
        return mainPane;
    }
}
