package Interface;

import TradeCenter.Card.Card;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.ATrade;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;


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
    private static ArrayList<Card> otheCollectionList = new ArrayList<Card>();
    static Text myCollection;
    static Text otherCollection;
    static Text myOffer;
    static Text otherOffer;
    static TextFlow myCollectionTitle;
    static TextFlow otherCollectionTitle;
    static TextFlow myOfferTitle;
    static TextFlow otherOfferTitle;

    static BorderPane display(ATrade trade, Customer myCustomer, Customer otherCustomer, boolean flagStarted){

        myC = myCustomer;
        otherC = otherCustomer;
        myImageList.removeAll(myImageList);
        otherImageList.removeAll(otherImageList);
        myCollectionList.removeAll(myCollectionList);
        otheCollectionList.removeAll(otheCollectionList);
        mainPane = new BorderPane();
        myCollFlow = new FlowPane();
        otherCollFlow = new FlowPane();
        myOfferFlow = new FlowPane();
        otherOfferFlow = new FlowPane();
        //griglia
        mainGrid = new GridPane();
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        //todo mettere le griglie ecc...
        //titoli

        myCollection = new Text("myCollection");       //todo abbellire i titoli
        myCollectionTitle = new TextFlow(myCollection);
        myCollectionTitle.setPadding(new Insets(5));
        myCollectionTitle.setStyle("-fx-background-color: #aa12ff");
        otherCollection = new Text(otherCustomer.getUsername()+ "'s Collection");
        otherCollectionTitle = new TextFlow(otherCollection);
        otherCollectionTitle.setPadding(new Insets(5));
        otherCollectionTitle.setStyle("-fx-background-color: #aa12ff");
        myOffer = new Text("myOffer");
        myOfferTitle = new TextFlow(myOffer);
        myOfferTitle.setPadding(new Insets(5));
        myOfferTitle.setStyle("-fx-background-color: #aa12ff");
        otherOffer = new Text(otherCustomer.getUsername()+ "'s Offer");
        otherOfferTitle = new TextFlow(otherOffer);
        otherOfferTitle.setPadding(new Insets(5));
        otherOfferTitle.setStyle("-fx-background-color: #aa12ff");

        //griglie

        myCollectionGrid = new ScrollPane();
        otherCollectionGrid = new ScrollPane();
        myOfferGrid = new ScrollPane();
        otherOfferGrid = new ScrollPane();

        myCollectionGrid.setMinSize(405,240);
        myCollectionGrid.setMaxSize(405,240);
        otherCollectionGrid.setMinSize(405,240);
        otherCollectionGrid.setMaxSize(405,240);
        myOfferGrid.setMinSize(405,240);
        myOfferGrid.setMaxSize(405,240);
        otherOfferGrid.setMinSize(405,240);
        otherOfferGrid.setMaxSize(405,240);
        myCollectionPane = new BorderPane();
        otherCollectionPane = new BorderPane();
        myOfferPane = new BorderPane();
        otherOfferPane = new BorderPane();

        if(!flagStarted) {
            //costruisco le singole griglie
            myCollectionPane = displayCards(myCustomer, myCollectionTitle, myCollectionGrid, myCollFlow, true, myCollectionList);
            otherCollectionPane = displayCards(otherCustomer, otherCollectionTitle, otherCollectionGrid, otherCollFlow, false, otheCollectionList);
            myOfferPane = displayCards(null, myOfferTitle, myOfferGrid, myOfferFlow, false, null);
            otherOfferPane = displayCards(null, otherOfferTitle, otherOfferGrid, otherOfferFlow, false, null);

            //myCollectionPane.setMaxHeight(50);
            //myCollectionPane.setMaxWidth(60);
            //costruisco la griglia principale, aggiungendoci le singole
            mainGrid.add(myCollectionPane, 1, 1);
            mainGrid.add(otherCollectionPane, 1, 2);
            mainGrid.add(myOfferPane, 2, 1);
            mainGrid.add(otherOfferPane, 2, 2);
            mainGrid.setStyle("-fx-background-color: #55ff44");
            mainGrid.setAlignment(Pos.CENTER);
        }
        //bottoni
        buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(15, 20, 10, 20));
        buttonsBox.setSpacing(10);
        buttonsBox.setStyle("-fx-background-color: #aa12ff");
        Button refuse = new Button("Refuse");

        Button raise = new Button("Raise");
        Button accept = new Button("Accept");
        buttonsBox.getChildren().addAll(refuse, raise, accept);
        if(!flagStarted) {
            mainPane.setCenter(mainGrid);
            mainPane.setBottom(buttonsBox);
        }
        else{
            if(trade!=null) {
                restoreFromPreviousTrade(trade);
            }
        }
        mainPane.getStylesheets().add("Interface/ButtonsCSS.css");
        return mainPane;
    }

    static BorderPane displayCards(Customer customer, TextFlow title, ScrollPane grid, FlowPane flowPane, boolean flag, ArrayList<Card> collection){
        BorderPane pane = new BorderPane();
        pane.setTop(title);                 //titolo
        //FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: #fff910");

        //todo togliere if e gestire le offerte
        if(customer == null){
            grid.setFitToWidth(true);
            grid.setFitToHeight(true);
            grid.setContent(flowPane);
            grid.setStyle("-fx-background-color: #fffb48");

            pane.setCenter(grid);
            //todo vedere se si possono settare spazi ecc...
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
            cardPane.setCenter(imageView);

            Tooltip tooltip = new Tooltip();

            Tooltip.install(imageView, new Tooltip("Right Click To Zoom"));


            imageView.setOnMousePressed(new EventHandler<MouseEvent>() {

                public void handle(MouseEvent mouseEvent) {



                    if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                        if(mouseEvent.getClickCount() == 1){
                            MainWindow.refreshDynamicContent(Demo.display(imageView, "trade"));
                        }
                    }
                    if(flag) {
                        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            if (mouseEvent.getClickCount() == 1) {

                                //myCollFlow.getChildren().remove(imageView);
                                addToOffer(myOfferGrid, card,flag);
                                myOfferPane.setCenter(myOfferGrid);
                                myCollectionList.remove(card);
                                restoreCollection(null,flag,myCollFlow, myCollectionList);

                            }
                        }
                    }
                    else{
                        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            if (mouseEvent.getClickCount() == 1) {


                                //otherCollFlow.getChildren().remove(imageView);
                                addToOffer(otherOfferGrid,card,flag);
                                otherOfferPane.setCenter(otherOfferGrid);
                                otheCollectionList.remove(card);
                                restoreCollection(null,flag, otherCollFlow, otheCollectionList );

                            }
                        }
                    }
                }

            });

            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }

        grid.setFitToWidth(true);
        grid.setFitToHeight(true);
        grid.setContent(flowPane);
        grid.setStyle("-fx-background-color: #fffb48");

        pane.setCenter(grid);
        //todo vedere se si possono settare spazi ecc...
        return pane;
    }

    static ScrollPane addToOffer(ScrollPane scrollPane, Card card, boolean flag){
        ArrayList<Card> imageList;

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
        flow.setStyle("-fx-background-color: #fff910");
        for (Card c: imageList) {
            BorderPane pane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(c.getDescription().getPic(), null);
            ImageView imageView1 = new ImageView();
            imageView1.setImage(image);
            imageView1.setPreserveRatio(true);
            imageView1.setFitHeight(161);
            imageView1.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                        if(flag) {
                            myCollFlow.getChildren().add(pane);
                           // myCollectionList.add(c);
                            restoreCollection(c, flag, myCollFlow, myCollectionList);
                            myImageList.remove(c);
                            flow.getChildren().remove(pane);

                        }
                        else{
                            //otheCollectionList.add(c);
                            otherCollFlow.getChildren().add(pane);
                            restoreCollection(c, flag, otherCollFlow, otheCollectionList);
                            otherImageList.remove(c);
                            flow.getChildren().remove(pane);

                        }

                    }
                }
            });
            pane.setCenter(imageView1);
            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(10, 5, 10, 5));
        }
        scrollPane.setContent(flow);
        return scrollPane;
    }

    static void restoreCollection(Card card, boolean flag, FlowPane flowPane, ArrayList<Card> cardList){
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
                cardPane.setCenter(imageView);

                Tooltip tooltip = new Tooltip();

                Tooltip.install(imageView, new Tooltip("Right Click To Zoom"));


                imageView.setOnMousePressed(new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent mouseEvent1) {


                        if (mouseEvent1.getButton().equals(MouseButton.SECONDARY)) {
                            if (mouseEvent1.getClickCount() == 1) {
                                MainWindow.refreshDynamicContent(Demo.display(imageView, "trade"));
                            }
                        }
                        if (flag) {
                            if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                                if (mouseEvent1.getClickCount() == 1) {
                                    myCollectionList.remove(c);
                                    addToOffer(myOfferGrid, c, flag);
                                    myOfferPane.setCenter(myOfferGrid);
                                    restoreCollection(null,flag,myCollFlow, myCollectionList);


                                }
                            }
                        } else {
                            if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                                if (mouseEvent1.getClickCount() == 1) {
                                    otheCollectionList.remove(c);
                                    addToOffer(otherOfferGrid, c, flag);
                                    otherOfferPane.setCenter(otherOfferGrid);
                                    restoreCollection(null,flag, otherCollFlow, otheCollectionList );
                                }
                            }
                        }
                    }

                });

                flowPane.getChildren().add(cardPane);
                flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
            }

    }

    static void restoreFromPreviousTrade(ATrade trade){
         GridPane mainGrid1 = new GridPane();
        myImageList.removeAll(myImageList);
        otherImageList.removeAll(otherImageList);
        myCollectionList.removeAll(myCollectionList);
        otheCollectionList.removeAll(otheCollectionList);

        restoreScroll(myCollectionPane, myCollectionGrid, myCollFlow);
        restoreScroll(myOfferPane, myOfferGrid, myOfferFlow);
        restoreScroll(otherCollectionPane, otherCollectionGrid,otherCollFlow);
        restoreScroll(otherOfferPane, otherOfferGrid,otherOfferFlow);

        for (Card card : trade.getCustomer1().getCollection()) {
            myCollectionList.add(card);
        }

        for (Card card : trade.getCustomer1().getCollection()) {
            otheCollectionList.add(card);
        }

        for(Card card : trade.getOffer1()){
            addToOffer(myOfferGrid, card ,true);
        }

        for (Card card : trade.getOffer2()){
            addToOffer(otherOfferGrid,card,false);
        }

        myCollectionList.removeAll(myImageList);
        otheCollectionList.removeAll(otherImageList);
        restoreCollection(null, true, myCollFlow, myCollectionList);
        restoreCollection(null, false, otherCollFlow, otheCollectionList);

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
        mainGrid1.setStyle("-fx-background-color: #55ff44");
        mainGrid1.setAlignment(Pos.CENTER);


        mainPane.setCenter(mainGrid1);
        mainPane.setBottom(buttonsBox);


    }

    static void restoreScroll(BorderPane borderPane,ScrollPane scrollPane, FlowPane flowPane){
        borderPane.setStyle("-fx-background-color: #fff910");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(flowPane);
        scrollPane.setStyle("-fx-background-color: #fffb48");
        flowPane.setStyle("-fx-background-color: #fff910");
        scrollPane.setStyle("-fx-background-color: #fff910");
        scrollPane.setContent(flowPane);

    }

    static BorderPane refresh(){

        GridPane mainGrid1 = new GridPane();

        mainGrid1.setHgap(10);
        mainGrid1.setVgap(10);

        restoreCollection(null, true, myCollFlow, myCollectionList);
        restoreCollection(null, false, otherCollFlow, otheCollectionList);

        mainGrid1.add(myCollectionPane,1,1);
        mainGrid1.add(otherCollectionPane,1,2);
        mainGrid1.add(myOfferPane,2,1);
        mainGrid1.add(otherOfferPane,2,2);
        mainGrid1.setStyle("-fx-background-color: #55ff44");
        mainGrid1.setAlignment(Pos.CENTER);


        mainPane.setCenter(mainGrid1);
        mainPane.setBottom(buttonsBox);

        return mainPane;
    }


}