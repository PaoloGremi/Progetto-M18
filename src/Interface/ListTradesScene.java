package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ListTradesScene {

    private static Customer user;
    private static Trade currentTrade;

    static BorderPane display(Customer myCustomer){
        user = myCustomer;
        BorderPane mainPane = new BorderPane();
        HBox mainHbox = new HBox();
        mainHbox.setPadding(new Insets(5));
        mainHbox.setSpacing(10);
        ScrollPane scrollableActive = new ScrollPane();
        ScrollPane scrollableDone = new ScrollPane();
        BorderPane activePane = new BorderPane();
        BorderPane donePane = new BorderPane();
        TextFlow activeTitle = new TextFlow(new Text("Active Trades"));
        HBox activeTitleBox = new HBox();
        activeTitleBox.setPadding(new Insets(5));
        activeTitleBox.getChildren().add(activeTitle);
        activeTitleBox.setStyle("-fx-background-color: #ffe37e");
        TextFlow doneTitle = new TextFlow(new Text("Done Trades"));
        HBox doneTitleBox = new HBox();
        doneTitleBox.setPadding(new Insets(5));
        doneTitleBox.getChildren().add(doneTitle);
        doneTitleBox.setStyle("-fx-background-color: #ffe37e");
        ArrayList<Trade> userTrades = new ArrayList<>();
        ArrayList<Trade> activeTrades = new ArrayList<>();
        ArrayList<Trade> doneTrades = new ArrayList<>();

        ObservableList<Trade> trades = FXCollections.observableArrayList();

        userTrades = retrieveTrades(trades, myCustomer);
        activeOrDone(userTrades,activeTrades,doneTrades);

        //todo mettere listener che quando schiaccio un trade mi fa vedere o il trade (se attivo), o altro se finito--> fare relativa scena
        JFXListView<Trade> activeList = new JFXListView<>();
        activeList.minHeight(200);
        activeList.maxWidth(200);
        JFXListView<Trade> doneList = new JFXListView<>();
        activeList.getItems().addAll(activeTrades);
        doneList.getItems().addAll(doneTrades);
        if(!trades.isEmpty()){
            activeList.setOnMouseClicked(addMouseEvent(activeList));
            doneList.setOnMouseClicked(addMouseEvent(doneList));
        }

        activeList.setEditable(true);
        doneList.setEditable(true);

        scrollableActive.setContent(activeList);
        scrollableActive.setMinSize(400,500);
        scrollableActive.setFitToHeight(true);
        scrollableActive.setFitToWidth(true);

        scrollableDone.setContent(doneList);
        scrollableDone.setMinSize(400,500);
        scrollableDone.setFitToHeight(true);
        scrollableDone.setFitToWidth(true);

        activePane.setCenter(scrollableActive);
        activePane.setTop(activeTitleBox);

        donePane.setCenter(scrollableDone);
        donePane.setTop(doneTitleBox);

        mainHbox.getChildren().addAll(activePane, donePane);
        mainHbox.setFillHeight(true);
        mainHbox.setAlignment(Pos.CENTER);
        //mainHbox.setStyle("-fx-background-color: #ffe37e");

        mainPane.setCenter(mainHbox);

        //todo mettere a posto stile del css
        mainPane.getStylesheets().add("Interface/ListTradesCSS.css");
        mainPane.setStyle("-fx-background-color: #afff4f");
        return mainPane;
    }

    static BorderPane refresh(){
        return display(user);
    }

    static private Customer retrieveCustomerById(String id){
        Socket socket = null;
        Customer customer = null;
        try {
            socket = new Socket("localhost", 8889);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHUSERBYID, id));
            customer = (Customer)is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customer;
    }

    static private boolean sameTrade(Trade trade){

        Socket socket = null;
        try {
            socket = new Socket("localhost", 8889);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHTRADE, trade.getCustomer1(), trade.getCustomer2()));
            currentTrade = (Trade)is.readObject();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(currentTrade.getOffer1().getSet().equals(trade.getOffer1().getSet()) && currentTrade.getOffer2().getSet().equals(trade.getOffer2().getSet())){
            return true;
        }

        return false;
    }

    private static ArrayList<Trade> retrieveTrades(ObservableList<Trade> trades, Customer myCustomer){
        ArrayList<Trade> userTrades = new ArrayList<>();
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
        return userTrades;
    }

    private static void activeOrDone(ArrayList<Trade> all, ArrayList<Trade> active, ArrayList<Trade> done){
        for(Trade trade : all){
            if(trade.isDoneDeal()){
                done.add(trade);
            }else{
                active.add(trade);
            }
        }
    }

    private static EventHandler<MouseEvent> addMouseEvent(JFXListView<Trade> tradeList){
        EventHandler<MouseEvent> eventHandlerBox =
                new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent e) {
                        Trade trade = tradeList.getSelectionModel().getSelectedItem();
                        Customer customer1 = retrieveCustomerById(trade.getCustomer1());
                        Customer customer2 = retrieveCustomerById(trade.getCustomer2());
                        if (trade.isDoneDeal()) {
                            if(trade.isPositiveEnd()) {
                                MainWindow.refreshDynamicContent(DoneDealScene.display(trade, customer1.getUsername(), customer2.getUsername()));
                            }else{
                                MainWindow.addDynamicContent(InfoScene.display("The offer has been rejected", "Interface/infoSign.png",true));
                            }
                        } else {


                            if(sameTrade(trade)) {
                                //todo devo capire come passare i customer, cosi non vengono scambiati, mettere direzione come booleano e poi rifare
                                if (user.getId().equals(trade.getCustomer1())) {

                                    MainWindow.refreshDynamicContent(TradeScene.display(trade, customer1, customer2, true, true));
                                    MainWindow.addDynamicContent(InfoScene.display(customer2.getUsername() + " has not answered yet\nYou can still change the offer", "Interface/infoSign.png", true));
                                } else {

                                    MainWindow.refreshDynamicContent(TradeScene.display(trade, customer2, customer1, true, false));
                                }
                            }else{
                                if (user.getId().equals(currentTrade.getCustomer1())) {

                                    MainWindow.refreshDynamicContent(TradeScene.display(currentTrade, customer1, customer2, true, true));
                                    MainWindow.addDynamicContent(InfoScene.display(customer2.getUsername() + " has not answered yet\nYou can still change the offer", "Interface/infoSign.png", true));
                                } else {

                                    MainWindow.refreshDynamicContent(TradeScene.display(currentTrade, customer2, customer1, true, false));
                                }
                            }
                        }
                    }
                };
        return eventHandlerBox;
    }

}
