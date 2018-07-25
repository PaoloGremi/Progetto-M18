package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import TradeCenter.Card.Card;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.TradeExceptions.AlreadyStartedTradeException;
import TradeCenter.Trades.ATrade;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static Interface.SearchUserScene.retrieveCustomer;

/**
 * The interface for the trade view
 */

public class TradeScene {

    private static HBox buttonsBox;
    private static GridPane mainGrid;
    private static ScrollPane myCollectionGrid;
    private static ScrollPane otherCollectionGrid;
    private static ScrollPane myOfferGrid;
    private static ScrollPane otherOfferGrid;
    private static BorderPane myCollectionPane;
    private static BorderPane otherCollectionPane;
    private static BorderPane myOfferPane;
    private static BorderPane otherOfferPane;
    private static BorderPane mainPane;
    private static Customer myC;
    private static Customer otherC;
    private static ArrayList<Card> myImageList = new ArrayList<Card>();
    private static ArrayList<Card> otherImageList = new ArrayList<Card>();
    private static FlowPane myCollFlow;
    private static FlowPane otherCollFlow;
    private static FlowPane myOfferFlow;
    private static FlowPane otherOfferFlow;
    private static ArrayList<Card> myCollectionList = new ArrayList<Card>();
    private static ArrayList<Card> otherCollectionList = new ArrayList<Card>();
    private static Text myCollection;
    private static Text otherCollection;
    private static Text myOffer;
    private static Text otherOffer;
    private static TextFlow myCollectionTitle;
    private static TextFlow otherCollectionTitle;
    private static TextFlow myOfferTitle;
    private static TextFlow otherOfferTitle;

    private static Collection myCardOffer;
    private static Collection otherCardOffer;


    private static ATrade currentTrade = null;
    private static JFXButton accept;
    /**
     *
     * @param trade a trade
     * @param myCustomer the customer itself
     * @param otherCustomer the customer you want to make an offer
     * @param flagStarted build each grid
     * @param changedMind
     * @return
     */
    static BorderPane display(ATrade trade, Customer myCustomer, Customer otherCustomer,  boolean flagStarted, boolean changedMind){

        currentTrade=trade;

        myCardOffer = new Collection();
        otherCardOffer = new Collection();
        myC = myCustomer;
        otherC = otherCustomer;
        myImageList.removeAll(myImageList);
        otherImageList.removeAll(otherImageList);
        myCollectionList.removeAll(myCollectionList);
        otherCollectionList.removeAll(otherCollectionList);
        mainPane = new BorderPane();
        myCollFlow = new FlowPane();
        otherCollFlow = new FlowPane();
        myOfferFlow = new FlowPane();
        otherOfferFlow = new FlowPane();
        //griglia
        mainGrid = new GridPane();
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        myCollection = new Text(myCustomer.getUsername()+ "'s Collection");
        myCollectionTitle = new TextFlow(myCollection);
        myCollectionTitle.setPadding(new Insets(5));
        myCollectionTitle.setStyle("-fx-background-color: orange;");
        otherCollection = new Text(otherCustomer.getUsername()+ "'s Collection");
        otherCollectionTitle = new TextFlow(otherCollection);
        otherCollectionTitle.setPadding(new Insets(5));
        otherCollectionTitle.setStyle("-fx-background-color: orange");
        myOffer = new Text(myCustomer.getUsername()+ "'s Offer");
        myOfferTitle = new TextFlow(myOffer);
        myOfferTitle.setPadding(new Insets(5));
        myOfferTitle.setStyle("-fx-background-color: orange");
        otherOffer = new Text(otherCustomer.getUsername()+ "'s Offer");
        otherOfferTitle = new TextFlow(otherOffer);
        otherOfferTitle.setPadding(new Insets(5));
        otherOfferTitle.setStyle("-fx-background-color: orange;");

        //griglie

        myCollectionGrid = new ScrollPane();
        otherCollectionGrid = new ScrollPane();
        myOfferGrid = new ScrollPane();
        otherOfferGrid = new ScrollPane();

        myCollectionGrid.setMinSize(405,240);
        myCollectionGrid.setMaxSize(405,240);
        otherCollectionGrid.setMinSize(405,240);
        otherCollectionGrid.setMaxSize(405,240);
        myOfferGrid.setMinSize(405,233);
        myOfferGrid.setMaxSize(405,233);
        otherOfferGrid.setMinSize(405,233);
        otherOfferGrid.setMaxSize(405,233);
        myCollectionPane = new BorderPane();
        otherCollectionPane = new BorderPane();
        myOfferPane = new BorderPane();
        otherOfferPane = new BorderPane();

        if(!flagStarted) {
            myCollectionPane = displayCards(myCustomer, myCollectionTitle, myCollectionGrid, myCollFlow, true, myCollectionList);
            otherCollectionPane = displayCards(otherCustomer, otherCollectionTitle, otherCollectionGrid, otherCollFlow, false, otherCollectionList);
            myOfferPane = displayCards(null, myOfferTitle, myOfferGrid, myOfferFlow, false, null);
            otherOfferPane = displayCards(null, otherOfferTitle, otherOfferGrid, otherOfferFlow, false, null);

            //myCollectionPane.setMaxHeight(50);
            //myCollectionPane.setMaxWidth(60);
            //costruisco la griglia principale, aggiungendoci le singole
            mainGrid.add(myCollectionPane, 1, 1);
            mainGrid.add(otherCollectionPane, 1, 2);
            mainGrid.add(myOfferPane, 2, 1);
            mainGrid.add(otherOfferPane, 2, 2);
            mainGrid.setStyle("-fx-background-color: DAE6A2;");
            mainGrid.setAlignment(Pos.CENTER);
        }
        //bottoni
        buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(7, 20, 7, 20));
        buttonsBox.setSpacing(10);
        buttonsBox.setStyle("-fx-background-color: orange;");
        JFXButton refuse = new JFXButton("Refuse");
        refuse.setButtonType(JFXButton.ButtonType.RAISED);
        JFXButton raise = new JFXButton("Raise");
        raise.setButtonType(JFXButton.ButtonType.RAISED);
        accept = new JFXButton("Accept");
        accept.setButtonType(JFXButton.ButtonType.RAISED);

        //listener bottoni
        raise.setOnAction(event -> {
            try {
                Socket socket = new Socket(ServerIP.ip, ServerIP.port);
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                if(!flagStarted){

                    if(!myCardOffer.collectionIsEmpty() && !otherCardOffer.collectionIsEmpty()) {

                            MainWindow.addDynamicContent(InfoScene.display("Your offer has been sent", "Interface/imagePack/infoSign.png", false));
                            os.writeObject(new MessageServer(MessageType.CREATEOFFER, myC.getId(), otherC.getId(), myCardOffer, otherCardOffer));
                            Thread.sleep(100);

                    } else {
                        MainWindow.addDynamicContent(InfoScene.display("You can't offer empty\ncollections", "Interface/imagePack/2000px-Simple_Alert.svg.png", true));
                    }
                }else {
                    if (verifyUpdated(currentTrade)) {
                        Customer currentMy = null;
                        Customer currentOther = null;
                        updateCustomers();
                        if (myC.getId().equals(currentTrade.getCustomer1())) {
                            currentMy = myC;
                            currentOther = otherC;
                        } else {
                            currentMy = otherC;
                            currentOther = myC;
                        }
                        if(!myCardOffer.collectionIsEmpty() && !otherCardOffer.collectionIsEmpty()) {
                            if (stillInTheCollection(currentMy.getCollection(), currentTrade.getOffer1()) && stillInTheCollection(currentOther.getCollection(), currentTrade.getOffer2())) {
                                if (myC.getId().equals(currentTrade.getCustomer1())) {
                                    os.writeObject(new MessageServer(MessageType.RAISEOFFER, myC.getId(), otherC.getId(), myCardOffer, otherCardOffer, changedMind));
                                } else {
                                    os.writeObject(new MessageServer(MessageType.RAISEOFFER, currentMy.getId(), currentOther.getId(), otherCardOffer, myCardOffer, false));
                                }
                                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                                Object read = is.readObject();
                                Thread.sleep(100);
                                boolean flag = read instanceof AlreadyStartedTradeException;
                                if (!flag) {
                                    myCardOffer.getSet().remove(myCardOffer.getSet());
                                    otherCardOffer.getSet().remove(otherCardOffer.getSet());
                                    MainWindow.addDynamicContent(InfoScene.display("Offer changed", "Interface/imagePack/infoSign.png", false));
                                    System.out.println("raised new offer");
                                } else {
                                    throw new AlreadyStartedTradeException(otherC.getUsername());
                                }
                            } else {
                                removeTrade(myC.getId(), otherC.getId());
                                MainWindow.refreshDynamicContent(TradeScene.display(null, myC, otherC, false, false));
                                MainWindow.addDynamicContent(InfoScene.display("The other customer traded one or\n more cards with someone else\nthe trade is restarted", "Interface/imagePack/2000px-Simple_Alert.svg.png", true));
                            }
                        }else {
                            MainWindow.addDynamicContent(InfoScene.display("You can't offer empty\ncollections", "Interface/imagePack/2000px-Simple_Alert.svg.png", true));
                        }

                    }else{
                        if(currentTrade!=null) {
                            infoOfferChanged();
                        }else{
                            MainWindow.addDynamicContent(InfoScene.display("The trade has already been closed\nby the other customer\nsee the result in My Trade", "Interface/imagePack/2000px-Simple_Alert.svg.png", false));
                        }
                    }
                }

                    socket.close();

            } catch (IOException | ClassNotFoundException | AlreadyStartedTradeException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        refuse.setOnAction(event -> {
            if(verifyUpdated(currentTrade)) {
                    try {
                        MainWindow.addDynamicContent(InfoScene.display("Offer rejected", "Interface/imagePack/infoSign.png", false));
                        Socket socket = new Socket(ServerIP.ip, ServerIP.port);
                        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                        os.writeObject(new MessageServer(MessageType.ENDTRADE, trade, false));
                        Thread.sleep(100);
                        socket.close();
                        System.out.println("refused offer");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            } else{
                if(currentTrade!=null) {
                    infoOfferChanged();
                }else{
                    MainWindow.addDynamicContent(InfoScene.display("The trade has already been closed\nby the other customer\nsee the result in My Trade", "Interface/imagePack/2000px-Simple_Alert.svg.png", false));
                }
            }
        });


        accept.setOnAction(event -> {
            Customer currentmy = null;
            Customer currentOther= null;
            updateCustomers();
            boolean condition = myC.getId().equals(currentTrade.getCustomer2());


            if(condition){
                if(verifyUpdated(currentTrade)) {
                    if(myC.getId().equals(currentTrade.getCustomer1())){
                        currentmy = myC;
                        currentOther = otherC;
                    }
                    else {
                        currentmy = otherC;
                        currentOther = myC;
                    }
                    updateCustomers();
                        if(stillInTheCollection(currentmy.getCollection(),currentTrade.getOffer1()) && stillInTheCollection(currentOther.getCollection(),currentTrade.getOffer2())) {
                            Timeline task = loading(currentTrade);
                            task.playFromStart();
                       }else{
                            removeTrade(myC.getId(),otherC.getId());
                            MainWindow.refreshDynamicContent(TradeScene.display(null, myC, otherC,false, false));
                            MainWindow.addDynamicContent(InfoScene.display("The other customer traded one or\n more cards with someone else\nthe trade is restarted", "Interface/imagePack/2000px-Simple_Alert.svg.png", true));
                        }

                }
                else {
                    if(currentTrade!=null) {
                        infoOfferChanged();
                    }else {
                        MainWindow.addDynamicContent(InfoScene.display("The trade has already been closed\nby the other customer\nsee the result in My Trade", "Interface/imagePack/2000px-Simple_Alert.svg.png", false));
                    }
                }
            }else{
                MainWindow.addDynamicContent(InfoScene.display("You can't accept your own offer", "Interface/imagePack/2000px-Simple_Alert.svg.png", true));
                System.err.println("You cannot accept your own offer");
            }
        });
        //buttonsBox.getChildren().addAll(refuse, raise, accept);
        if(!flagStarted) {
            raise.setText("New Trade");
            buttonsBox.getChildren().addAll(raise);
            mainPane.setTop(mainGrid);
            mainPane.setBottom(buttonsBox);
        }else{
            buttonsBox.getChildren().addAll(refuse, raise, accept);
            if(trade!=null) {
                restoreFromPreviousTrade(trade);
            }
        }
        mainPane.getStylesheets().add("Interface/ButtonsCSS.css");
        return mainPane;
    }

    /**
     * method used to display cards
     * @param customer the customer itself
     * @param title title of the element
     * @param grid where you add the element
     * @param flowPane a type of pane
     * @param flag tells if the collection is mine
     * @param collection the collection
     * @return the pane
     */
    static BorderPane displayCards(Customer customer, TextFlow title, ScrollPane grid, FlowPane flowPane, boolean flag, ArrayList<Card> collection){
        BorderPane pane = new BorderPane();
        pane.setTop(title);                 //titolo
        //FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: #ffe17a");


        if(customer == null){
            grid.setFitToWidth(true);
            grid.setFitToHeight(true);
            grid.setContent(flowPane);
            grid.setStyle("-fx-background-color: #ffe17a");

            pane.setCenter(grid);
            return pane;
        }

        //ciclo per aggiungere carte
        for (Card card : customer.getCollection()){
            if(collection != null){
                collection.add(card);
            }
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(161);
            ImageView imageCopy = new ImageView(image);
            imageCopy.setPreserveRatio(true);
            imageCopy.setFitHeight(161);
            cardPane.setCenter(imageView);

            cardPane.setOnMouseEntered(event -> {
                ScaleTransition st = CollectionScene.addScale(imageView);
                st.play();
            });
            cardPane.setOnMouseExited(event -> {
                ScaleTransition st = CollectionScene.removeScale(imageView);
                st.play();
            });

            Tooltip tooltip = new Tooltip();

            Tooltip.install(imageView, new Tooltip("Right Click To Zoom"));

            imageView.setOnMousePressed(moveCardsCollection(imageView,flag,card));

            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }

        grid.setFitToWidth(true);
        grid.setFitToHeight(true);
        grid.setContent(flowPane);
        grid.setStyle("-fx-background-color: #ffe17a");

        pane.setCenter(grid);
        return pane;
    }

    /**
     * static method used to add a card
     * @param scrollPane a pane type
     * @param card the card
     * @param flag used to know if the collection is mine
     * @return the pane
     */
    static ScrollPane addToOffer(ScrollPane scrollPane, Card card, boolean flag){
        ArrayList<Card> imageList;

        if(flag){
            myCardOffer.addCardToCollection(card);
        }
        else{
            otherCardOffer.addCardToCollection(card);
        }

        if(flag) {
            myImageList.add(card);
            imageList=myImageList;
        }
        else{
            otherImageList.add(card);
            imageList = otherImageList;
        }
        FlowPane flow = new FlowPane();
        //flow.getChildren().remove(imageView);
        flow.setStyle("-fx-background-color: #ffe17a");
        for (Card c: imageList) {
            BorderPane pane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(c.getDescription().getPic(), null);
            ImageView imageView1 = new ImageView();
            imageView1.setImage(image);
            imageView1.setPreserveRatio(true);
            imageView1.setFitHeight(161);

            imageView1.setOnMouseEntered(event -> {
                ScaleTransition st = CollectionScene.addScale(imageView1);
                st.play();
            });
            imageView1.setOnMouseExited(event -> {
                ScaleTransition st = CollectionScene.removeScale(imageView1);
                st.play();
            });

            imageView1.setOnMousePressed(moveCardsOffer(flow,pane,flag,c));
            if(c.equals(card)){
                ScaleTransition ft = addScaleTransition(pane);
                ft.play();
            }

            pane.setCenter(imageView1);
            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(10, 5, 10, 5));
        }
        scrollPane.setContent(flow);
        return scrollPane;
    }

    static void restoreCollection(Card card, boolean flag, FlowPane flowPane, ArrayList<Card> cardList){
        if(!toDisable()){
            accept.setDisable(true);
        }
        flowPane.getChildren().removeAll(flowPane.getChildren());
        if(card!=null) {
            cardList.add(card);
        }
        for(Card c : cardList) {
                BorderPane cardPane = new BorderPane();
                Image image = SwingFXUtils.toFXImage(c.getDescription().getPic(), null);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(161);
                ImageView imageCopy = new ImageView(image);
                imageCopy.setPreserveRatio(true);
                imageCopy.setFitHeight(161);
                cardPane.setCenter(imageView);

                cardPane.setOnMouseEntered(event -> {
                    ScaleTransition st = CollectionScene.addScale(imageView);
                    st.play();
                });
                cardPane.setOnMouseExited(event -> {
                    ScaleTransition st = CollectionScene.removeScale(imageView);
                    st.play();
                });

                Tooltip tooltip = new Tooltip();

                Tooltip.install(imageView, new Tooltip("Right Click To Zoom"));

                imageView.setOnMousePressed(moveCardsCollection(imageCopy,flag,c));

                flowPane.getChildren().add(cardPane);
                flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
            }

    }

    /**
     * display the already started trade
     * @param trade the trade to restore
     */
    static void restoreFromPreviousTrade(ATrade trade){
        GridPane mainGrid1 = new GridPane();
        myImageList.removeAll(myImageList);
        otherImageList.removeAll(otherImageList);
        myCollectionList.removeAll(myCollectionList);
        otherCollectionList.removeAll(otherCollectionList);


        restoreScroll(myCollectionPane, myCollectionGrid, myCollFlow);
        restoreScroll(myOfferPane, myOfferGrid, myOfferFlow);
        restoreScroll(otherCollectionPane, otherCollectionGrid,otherCollFlow);
        restoreScroll(otherOfferPane, otherOfferGrid,otherOfferFlow);

        for (Card card : myC.getCollection()) {
            myCollectionList.add(card);
        }

        for (Card card : otherC.getCollection()) {
            otherCollectionList.add(card);
        }
        if(myC.getId().equals(trade.getCustomer1())) {
            for (Card card : trade.getOffer1()) {
                addToOffer(myOfferGrid, card, true);
            }

            for (Card card : trade.getOffer2()) {
                addToOffer(otherOfferGrid, card, false);
            }
        }else{
            for (Card card : trade.getOffer2()) {
                addToOffer(myOfferGrid, card, true);
            }

            for (Card card : trade.getOffer1()) {
                addToOffer(otherOfferGrid, card, false);
            }
        }



        myCollectionList.removeAll(myImageList);
        otherCollectionList.removeAll(otherImageList);
        restoreCollection(null, true, myCollFlow, myCollectionList);
        restoreCollection(null, false, otherCollFlow, otherCollectionList);

        myCollectionPane.setCenter(myCollectionGrid);
        myCollectionPane.setTop(myCollectionTitle);
        myOfferPane.setCenter(myOfferGrid);
        myOfferPane.setTop(myOfferTitle);
        otherCollectionPane.setCenter(otherCollectionGrid);
        otherCollectionPane.setTop(otherCollectionTitle);
        otherOfferPane.setCenter(otherOfferGrid);
        otherOfferPane.setTop(otherOfferTitle);

        mainGrid1.setHgap(10);
        mainGrid1.setVgap(10);

        mainGrid1.add(myCollectionPane,1,1);
        mainGrid1.add(otherCollectionPane,1,2);
        mainGrid1.add(myOfferPane,2,1);
        mainGrid1.add(otherOfferPane,2,2);
        mainGrid1.setStyle("-fx-background-color: DAE6A2;");
        mainGrid1.setAlignment(Pos.CENTER);


        mainPane.setCenter(mainGrid1);
        mainPane.setBottom(buttonsBox);


    }

    /**
     * restore the scroll viwe
     * @param borderPane type of pane
     * @param scrollPane type of pane
     * @param flowPane type of pane
     */
    static void restoreScroll(BorderPane borderPane,ScrollPane scrollPane, FlowPane flowPane){
        borderPane.setStyle("-fx-background-color: #ffe17a");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(flowPane);
        scrollPane.setStyle("-fx-background-color: #ffe17a");
        flowPane.setStyle("-fx-background-color: #ffe17a");
        scrollPane.setStyle("-fx-background-color: #ffe17a");
        scrollPane.setContent(flowPane);

    }

    /**
     * method used to visualize the trade scene
     * @return border pane
     */
    static BorderPane refresh(){

        GridPane mainGrid1 = new GridPane();

        mainGrid1.setHgap(10);
        mainGrid1.setVgap(10);

        restoreCollection(null, true, myCollFlow, myCollectionList);
        restoreCollection(null, false, otherCollFlow, otherCollectionList);

        mainGrid1.add(myCollectionPane,1,1);
        mainGrid1.add(otherCollectionPane,1,2);
        mainGrid1.add(myOfferPane,2,1);
        mainGrid1.add(otherOfferPane,2,2);
        mainGrid1.setStyle("-fx-background-color: DAE6A2;");
        mainGrid1.setAlignment(Pos.CENTER);


        mainPane.setCenter(mainGrid1);
        mainPane.setBottom(buttonsBox);

        return mainPane;
    }

    /**
     * method that close the trade with successful ends
     * @param trade the trade
     * @return a timeline in which you've done the trade
     */
    private static Timeline loading(ATrade trade ){

        Timeline task = new Timeline(

                new KeyFrame(
                        Duration.ZERO,
                        event -> {

                            MainWindow.addDynamicContent(InfoScene.display("Deal done", "Interface/imagePack/pokeBall.png",false));
                        }
                ),

                new KeyFrame(
                        Duration.millis(5),
                        event -> {

                            try {
                                Socket socket = new Socket(ServerIP.ip, ServerIP.port);
                                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                                os.writeObject(new MessageServer(MessageType.ENDTRADE,  trade, true));
                                try {
                                    //todo fix sleep
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                socket.close();
                                System.out.println("accepted offer");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                )
        );

        return task;
    }

    /**
     * method to update the trade
     * @param trade the trade you want to update
     * @return updater trade
     */
    private static ATrade retrieveActualTrade(ATrade trade){
        ATrade actualTrade = null;
        try {
            Socket socket = new Socket(ServerIP.ip, ServerIP.port);
            socket.setTcpNoDelay(true);
            //socket.setKeepAlive(true);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHTRADE, trade.getCustomer1(), trade.getCustomer2()));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            actualTrade = (ATrade) is.readObject();
            os.flush();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return actualTrade;
    }

    /**
     * Verify if the trade is successfully updeter
     * @param trade the trade to update
     * @return a boolean
     */
    private static boolean verifyUpdated(ATrade trade){
        ATrade actualTrade = retrieveActualTrade(trade);
        if(actualTrade!=null) {
            if (trade.getOffer1().getSet().size() == actualTrade.getOffer1().getSet().size() && trade.getOffer2().getSet().size() == actualTrade.getOffer2().getSet().size()) {
                if (trade.getOffer1().getSet().equals(actualTrade.getOffer1().getSet()) && trade.getOffer2().getSet().equals(actualTrade.getOffer2().getSet())) {
                    return true;
                }
            }
        }
        currentTrade = actualTrade;
        return false;
    }

    /**
     * method used to change an offer
     */
    private static void infoOfferChanged(){
        updateCustomers();
        restoreFromPreviousTrade(currentTrade);
        MainWindow.addDynamicContent(InfoScene.display("The other customer changed\nthe offer", "Interface/imagePack/2000px-Simple_Alert.svg.png",true));
    }

    /**
     * Check if the card is still in the customer's collection
     * @param collection Collection of the customer
     * @param offer Offer with the cards to verify
     * @return Boolean with the result
     */
    private static boolean stillInTheCollection(Collection collection, Collection offer){
        for(Card card : offer){
            if(!collection.isInTheCollection(card)){
                return false;
            }
        }

        return true;
    }

    /**
     * Remove the trade
     * @param myid customer's id
     * @param otherId other customer's id
     */
    private static void removeTrade(String myid, String otherId){
        Socket socket = null;
        try {
            socket = new Socket(ServerIP.ip, ServerIP.port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.REMOVETRADE, myid, otherId));
            Thread.sleep(100);
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update of the customer
     */
    private static void updateCustomers(){
        Customer currentMy = null;
        Customer currentOther = null;
        currentMy = retrieveCustomer(myC.getUsername());
        currentOther = retrieveCustomer(otherC.getUsername());

        myC=currentMy;
        otherC=currentOther;
    }

    /**
     * Mouse event handler that move the cards from collection to offer
     * @param imageView the card's image
     * @param flag used to check if the mouse is clicked
     * @param card the card
     * @return the event
     */
    private static EventHandler<MouseEvent> moveCardsCollection(ImageView imageView, boolean flag, Card card){
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent1) {
                if(toDisable()) {
                    accept.setDisable(true);
                }else{
                    accept.setDisable(false);
                }

                if (mouseEvent1.getButton().equals(MouseButton.SECONDARY)) {
                    if (mouseEvent1.getClickCount() == 1) {
                        MainWindow.refreshDynamicContent(Demo.display(imageView, "trade"));
                    }
                }
                if (flag) {
                    if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent1.getClickCount() == 1) {

                            handleEventCollection(myCollectionList,card,flag,myOfferGrid,myOfferPane,myCollFlow);

                        }
                    }
                } else {
                    if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent1.getClickCount() == 1) {

                            handleEventCollection(otherCollectionList,card,flag,otherOfferGrid,otherOfferPane,otherCollFlow);
                        }
                    }
                }
            }

        };

        return event;
    }

    /**
     * Disable the accept button if the customer change the offers
     * @return boolean if the button is to disable
     */
    private static boolean toDisable() {
        if (currentTrade != null){
            return myCardOffer.getSet().equals(currentTrade.getOffer1().getSet()) && otherCardOffer.getSet().equals(currentTrade.getOffer2().getSet()) || myCardOffer.getSet().equals(currentTrade.getOffer2().getSet()) && otherCardOffer.getSet().equals(currentTrade.getOffer1().getSet());
        }
        return true;
    }

    /**
     * method that move the card from the collection to the offer
     * @param collectionList the collection
     * @param card the card
     * @param flag tells if the collection is mine
     * @param offerGrid type of pane
     * @param offerPane type of pane
     * @param collFlow type of pane
     */
    private static void handleEventCollection(ArrayList<Card> collectionList, Card card, boolean flag, ScrollPane offerGrid, BorderPane offerPane, FlowPane collFlow){
        collectionList.remove(card);
        addToOffer(offerGrid, card, flag);
        offerPane.setCenter(offerGrid);
        restoreCollection(null,flag,collFlow, collectionList);
    }

    /**
     * Mouse handler to move the card from offer to collection
     * @param flow type of pane
     * @param pane tipe of pane
     * @param flag to check if it's my collection
     * @param card the card
     * @return the event
     */
    private static EventHandler<MouseEvent> moveCardsOffer(FlowPane flow, BorderPane pane, boolean flag, Card card){
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(toDisable()) {
                    accept.setDisable(true);
                }else{
                    accept.setDisable(false);
                }
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                    if(flag) {

                        handleEventOffer(myCollectionList,card,flag, myCollFlow,flow,myImageList,myCardOffer,pane);

                    }
                    else{

                        handleEventOffer(otherCollectionList,card,flag,otherCollFlow,flow,otherImageList,otherCardOffer,pane);

                    }

                }
            }
        };

        return event;
    }

    /**
     * method to move the card from offer to collection
     * @param collectionList the collection
     * @param card the card
     * @param flag tells if the collection is mine
     * @param collFlow type of pane
     * @param flow type of pane
     * @param imageList card's image
     * @param cardOffer the offered card
     * @param pane type of pane
     */
    private static void handleEventOffer(ArrayList<Card> collectionList, Card card, boolean flag, FlowPane collFlow, FlowPane flow, ArrayList<Card> imageList, Collection cardOffer, BorderPane pane){
        cardOffer.removeCardFromCollection(card);
        collFlow.getChildren().add(pane);
        restoreCollection(card, flag, collFlow, collectionList);
        imageList.remove(card);
        flow.getChildren().remove(pane);
    }

    /**
     * Adds scale animation
     * @param node Node to add animation
     * @return The transition
     */
    private static ScaleTransition addScaleTransition(Node node){
        ScaleTransition ft = new ScaleTransition(Duration.millis(200), node);
        ft.setFromX(0);
        ft.setFromY(0);
        ft.setToX(1);
        ft.setToY(1);
        ft.setAutoReverse(true);
        return ft;
    }
}