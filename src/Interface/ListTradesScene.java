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
                        MainWindow.refreshDynamicContent(TradeScene.refresh());
                    }
                };

        //todo se funziona listener modificare tradeScene (magari metodo refresh)in modo che si apra come l'ultima offerta fatta e non come fosse la prima
        //tradeList.setFixedCellSize(70.0);
        //tradeList.setStyle("-fx-control-inner-background: #feff2e;");

        //todo trovare come fare colore diverso dei bordi della label, o fare spaziatura in modo da colorare il fondo con setBackground

        //tradeList.setOnMousePressed(eventHandlerBox);
        tradeList.setOnMouseClicked(eventHandlerBox);
        //todo la nullpointer exception probabilmente è perche non esiste il secondo customer, vedere con marco come fixare
        //TODO infatti se prima si apre il trade con l'utente l'eccezione non si genera, ma Altro PROBLEMA
        //todo fixare che qualunque item schiaccio fa vedere la trade con l'ultimo utente e non con quello schiacciato--->Modificare TradeScene
        tradeList.setEditable(true);

        scrollableList.setContent(tradeList);
        scrollableList.setFitToHeight(true);
        scrollableList.setFitToWidth(true);

        mainPane.setTop(title);
        mainPane.setCenter(scrollableList);
        //todo mettere a posto css
        mainPane.getStylesheets().add("Interface/ListTradesCSS.css"); //nei css usare i percorsi relativi
        return mainPane;
    }
}
