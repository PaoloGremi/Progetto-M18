package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ListTradesScene {

    private static BorderPane mainPane;
    private static Text title;
    private static ScrollPane scrollableList;



    static BorderPane display(Customer myCustomer){
        mainPane = new BorderPane();
        title = new Text(myCustomer.getUsername() + "'s Trades");
        scrollableList = new ScrollPane();
        ArrayList<Trade> userTrades = new ArrayList<>();

        ObservableList<Trade> trades = FXCollections.observableArrayList();
        try {
            Socket socket = new Socket("localhost", 8889);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHOFFER, myCustomer));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            userTrades = (ArrayList<Trade>) (is.readObject());
            socket.close();
            trades.addAll(userTrades);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        //todo mettere listener che quando schiaccio un trade mi fa vedere o il trade (se attivo), o altro se finito--> fare relativa scena
        JFXListView<Trade> tradeList = new JFXListView<>();
        tradeList.getItems().addAll(userTrades);
        if(!trades.isEmpty()){
            EventHandler<MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {
                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            Trade trade = tradeList.getSelectionModel().getSelectedItem();
                            if (trade.isDoneDeal()) {
                                System.out.println("done");
                            } else {

                                //todo devo capire come passare i customer, cosi non vengono scambiati, mettere direzione come booleano e poi rifare
                                if(myCustomer.getUsername().equals(trade.getCustomer1().getUsername())) {
                                    MainWindow.refreshDynamicContent(TradeScene.display(trade, myCustomer, trade.getCustomer2(), true));
                                }else{
                                    MainWindow.refreshDynamicContent(TradeScene.display(trade, myCustomer, trade.getCustomer1(), true));
                                }
                            }
                        }
                    };

            //todo se funziona listener modificare tradeScene (magari metodo refresh)in modo che si apra come l'ultima offerta fatta e non come fosse la prima

            tradeList.setOnMouseClicked(eventHandlerBox);
        }

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
