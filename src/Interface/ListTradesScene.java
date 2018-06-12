package Interface;

import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Trade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class ListTradesScene {

    private static BorderPane mainPane;
    private static Text title;
    //static Text activeTitle;
    //static Text doneTitle;
    private static ScrollPane scrollableList;



    static BorderPane display(Customer myCustomer){
        mainPane = new BorderPane();
        title = new Text(myCustomer.getUsername() + "'s Trades");
        //activeTitle = new Text("Active Trades");
        //doneTitle = new Text("Done Trades");
        scrollableList = new ScrollPane();


        TradeCenter tradeCenter = new TradeCenter();
        tradeCenter.fakeTrades(myCustomer);
        ArrayList<Trade> userActiveTrades = tradeCenter.showUserActiveTrades(myCustomer);   //todo porcata togliere i TradeCenter abusivi
        ArrayList<Trade> userDoneTrades = tradeCenter.showUserDoneTrades(myCustomer);
        ArrayList<Trade> userTrades = new ArrayList<>();
        userTrades.addAll(userActiveTrades);
        userTrades.addAll(userDoneTrades);


        ObservableList<Trade> trades = FXCollections.observableArrayList();
        trades.addAll(userTrades);

        //todo mettere listener che quando schiaccio un trade mi fa vedere o il trade (se attivo), o altro se finito--> fare relativa scena
        ListView<Trade> tradeList = new ListView<>(trades);
        EventHandler<MouseEvent> eventHandlerBox =
                new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent e) {
                        Trade trade = tradeList.getSelectionModel().getSelectedItem();
                        MainWindow.refreshDynamicContent(TradeScene.display(trade, myCustomer, trade.getCustomer2(),true));
                    }
                };

        //todo se funziona listener modificare tradeScene (magari metodo refresh)in modo che si apra come l'ultima offerta fatta e non come fosse la prima

        //tradeList.setOnMousePressed(eventHandlerBox);
        tradeList.setOnMouseClicked(eventHandlerBox);
        tradeList.setEditable(true);

        scrollableList.setContent(tradeList);
        scrollableList.setFitToHeight(true);
        scrollableList.setFitToWidth(true);

        mainPane.setTop(title);
        mainPane.setCenter(scrollableList);

        //todo mettere a posto stile del css
        mainPane.getStylesheets().add("Interface/ListTradesCSS.css"); //nei css usare i percorsi relativi
        return mainPane;
    }
}
