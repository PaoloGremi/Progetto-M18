package Interface;

import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Trade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class ListTradesScene {

    static BorderPane mainPane;
    static Text title;
    //static Text activeTitle;
    //static Text doneTitle;
    static ScrollPane scrollableList;



    static BorderPane display(Customer myCustomer){
        mainPane = new BorderPane();
        title = new Text(myCustomer.getUsername() + "'s Trades");
        //activeTitle = new Text("Active Trades");
        //doneTitle = new Text("Done Trades");
        scrollableList = new ScrollPane();


        TradeCenter tradeCenter = new TradeCenter();
        tradeCenter.fakeTrades(myCustomer);
        ArrayList<Trade> userActiveTrades = tradeCenter.showUserActiveTrades(myCustomer);   //todo porcata poi far cambiare
        ArrayList<Trade> userDoneTrades = tradeCenter.showUserDoneTrades(myCustomer);
        ArrayList<Trade> userTrades = new ArrayList<>();
        userTrades.addAll(userActiveTrades);
        userTrades.addAll(userDoneTrades);



        //todo allargare le label che si creano
        ObservableList<Trade> trades = FXCollections.observableArrayList();
        trades.addAll(userTrades);

        //todo mettere listener che quando schiaccio un trade mi fa vedere o il trade (se attivo), o altro se finito--> fare relativa scena
        ListView<Trade> tradeList = new ListView<>(trades);
        tradeList.setEditable(true);

        scrollableList.setContent(tradeList);
        scrollableList.setFitToHeight(true);
        scrollableList.setFitToWidth(true);

        mainPane.setTop(title);
        mainPane.setCenter(scrollableList);
        return mainPane;
    }
}
