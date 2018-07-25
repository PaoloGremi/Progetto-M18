package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;


public class ListTradesScene {

    private static Customer user;
    private static Trade currentTrade;

    /**
     * Displays active and closed trades of the customer logged in the client
     * @param myCustomer Customer logged
     * @return BorderPane withe the lists
     */
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
        doneList.minHeight(200);
        doneList.maxWidth(200);
        activeList.getItems().addAll(activeTrades);
        changTextCell(activeList);
        doneList.getItems().addAll(doneTrades);
        changTextCell(doneList);
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
       // mainPane.getStylesheets().add("Interface/ListTradesCSS.css");
        mainPane.setStyle("-fx-background-color: #e2e551");
        return mainPane;
    }

    /**
     * Refresh the dynamic pane
     * @return Borderpane with de lists
     */
    static BorderPane refresh(){
        return display(user);
    }

    /**
     * Retrieve the customer by his id
     * @param id Id of the customer to find
     * @return The customer found
     */
    static private Customer retrieveCustomerById(String id){
        Socket socket = null;
        Customer customer = null;
        try {
            socket = new Socket(ServerIP.ip, ServerIP.port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHUSERBYID, id));
            customer = (Customer)is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * Verify that the trades are the same or they changed while the customer is watching the list
     * @param trade Trade to verify
     * @return Boolean if the trade is the same
     */
    static private boolean sameTrade(Trade trade) throws NullPointerException{

        Socket socket = null;
        try {
            socket = new Socket(ServerIP.ip, ServerIP.port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHTRADE, trade.getCustomer1(), trade.getCustomer2()));
            currentTrade = (Trade)is.readObject();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(currentTrade!=null) {
            if (currentTrade.getOffer1().getSet().equals(trade.getOffer1().getSet()) && currentTrade.getOffer2().getSet().equals(trade.getOffer2().getSet())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieve the trades of the customer logged
     * @param trades List of the trades
     * @param myCustomer Customer logged
     * @return List with all the trades
     */
    private static ArrayList<Trade> retrieveTrades(ObservableList<Trade> trades, Customer myCustomer){
        ArrayList<Trade> userTrades = new ArrayList<>();
        try {
            Socket socket = new Socket(ServerIP.ip, ServerIP.port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHOFFER, myCustomer.getId()));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            userTrades = (ArrayList<Trade>) (is.readObject());
            socket.close();
            trades.addAll(userTrades);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userTrades;
    }

    /**
     * Check the status of the trade and add it to the right list
     * @param all List with all the trades
     * @param active List with only active trades
     * @param done List with only closed trades
     */
    private static void activeOrDone(ArrayList<Trade> all, ArrayList<Trade> active, ArrayList<Trade> done){
        for(Trade trade : all){
            if(trade.isDoneDeal()){
                done.add(trade);
            }else{
                active.add(trade);
            }
        }
    }

    /**
     * Method to handle mouse events
     * @param tradeList List to assign to the mouse event
     * @return Mouse event handled
     */
    private static EventHandler<MouseEvent> addMouseEvent(JFXListView<Trade> tradeList){
        EventHandler<MouseEvent> eventHandlerBox =
                new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent e) {
                        Trade trade = tradeList.getSelectionModel().getSelectedItem();
                        if (trade != null) {
                            Customer customer1 = retrieveCustomerById(trade.getCustomer1());
                            Customer customer2 = retrieveCustomerById(trade.getCustomer2());
                            if (trade.isDoneDeal()) {
                                if (trade.isPositiveEnd()) {
                                    MainWindow.refreshDynamicContent(DoneDealScene.display(trade, customer1.getUsername(), customer2.getUsername()));
                                } else {
                                    MainWindow.addDynamicContent(InfoScene.display("The offer has been rejected", "Interface/imagePack/infoSign.png", true));
                                }
                            } else {


                                if (sameTrade(trade)) {
                                    //todo devo capire come passare i customer, cosi non vengono scambiati, mettere direzione come booleano e poi rifare
                                    if (user.getId().equals(trade.getCustomer1())) {

                                        MainWindow.refreshDynamicContent(TradeScene.display(trade, customer1, customer2, true, true));
                                        MainWindow.addDynamicContent(InfoScene.display(customer2.getUsername() + " has not answered yet\nYou can still change the offer", "Interface/imagePack/infoSign.png", true));
                                    } else {

                                        MainWindow.refreshDynamicContent(TradeScene.display(trade, customer2, customer1, true, false));
                                    }
                                } else {
                                    if(currentTrade != null) {
                                        if (user.getId().equals(currentTrade.getCustomer1())) {

                                            MainWindow.refreshDynamicContent(TradeScene.display(currentTrade, customer1, customer2, true, true));
                                            MainWindow.addDynamicContent(InfoScene.display(customer2.getUsername() + " has not answered yet\nYou can still change the offer", "Interface/imagePack/infoSign.png", true));
                                        } else {

                                            MainWindow.refreshDynamicContent(TradeScene.display(currentTrade, customer2, customer1, true, false));
                                        }
                                    }else{
                                        MainWindow.addDynamicContent(InfoScene.display(customer2.getUsername() + " has already ended\nthe trade, watch the\nresult in MyTrades", "Interface/imagePack/infoSign.png", false));
                                    }
                                }
                            }
                        }
                    }
                };
        return eventHandlerBox;
    }

    /**
     * Modify the text of the cells
     * @param id1 Id customer1 of the trade
     * @param id2 Id customer2 of the trade
     * @param date Date and time of the trade
     * @return Cell modified
     */
    private static Text cellText(String id1, String id2, Date date){
        String username1 = usernameFromId(id1);
        String username2 = usernameFromId(id2);
        Text text = new Text(username1 + " - " + username2 + "\n" + date);
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    /**
     * Find the username of the customer from his id
     * @param id Id of the customer
     * @return Username of the customer
     */
    private static String usernameFromId(String id){
        Socket socket = null;
        String username = null;
        try {
            socket = new Socket(ServerIP.ip, ServerIP.port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHUSERNAME, id));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            username = (String) (is.readObject());
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * Modify the cells of the list
     * @param listView List to modify
     */
    private static void changTextCell(JFXListView listView){
        listView.setCellFactory(lv -> new ListCell<Trade>() {
            @Override
            public void updateItem(Trade item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(cellText(item.getCustomer1(), item.getCustomer2(), item.getDate()).getText());
                }
            }
        });
    }
}
