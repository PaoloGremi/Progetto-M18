package Interface;

import TradeCenter.Card.Card;
import TradeCenter.Customers.Customer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.swing.*;

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

    static BorderPane display(Customer myCustomer, Customer otherCustomer){
        mainPane = new BorderPane();
        //griglia
        mainGrid = new GridPane();
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        //todo mettere le griglie ecc...
        //titoli
        TextArea myCollectionTitle = new TextArea("myCollection");
        TextArea myOfferTitle = new TextArea("myOffer");
        TextArea otherCollectionTitle = new TextArea("otherCollection");
        TextArea otherOfferTitle = new TextArea("otherOffer");

        //griglie
        myCollectionGrid = new ScrollPane();
        otherCollectionGrid = new ScrollPane();
        myOfferGrid = new ScrollPane();
        otherOfferGrid = new ScrollPane();

        //costruisco le singole griglie
        myCollectionPane = displayCards(myCustomer, myCollectionTitle, myCollectionGrid);
        otherCollectionPane = displayCards(otherCustomer, otherCollectionTitle, otherCollectionGrid);
        myOfferPane = displayCards(null, myOfferTitle, myOfferGrid);
        otherOfferPane = displayCards(null, otherOfferTitle, otherOfferGrid);

        //costruisco la griglia principale, aggiungendoci le singole
        mainGrid.add(myCollectionPane,1,1);
        mainGrid.add(otherCollectionPane,1,2);
        mainGrid.add(myOfferPane,2,1);
        mainGrid.add(otherOfferPane,2,2);

        //bottoni
        buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(15, 20, 10, 20));
        buttonsBox.setSpacing(10);
        buttonsBox.setStyle("-fx-background-color: #aa12ff");
        Button refuse = new Button("Refuse");
        Button raise = new Button("Raise");
        Button accept = new Button("Accept");
        buttonsBox.getChildren().addAll(refuse, raise, accept);

        mainPane.setCenter(mainGrid);
        mainPane.setBottom(buttonsBox);

        return mainPane;
    }

    static BorderPane displayCards(Customer customer, TextArea title, ScrollPane grid){

        BorderPane pane = new BorderPane();
        pane.setTop(title);                 //titolo
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: #fff910");

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
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(300);
            cardPane.setCenter(imageView);
            EventHandler<MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(imageView, "other_user"));
                        }
                    };

            imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);
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
}
