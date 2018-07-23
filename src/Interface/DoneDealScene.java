package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import TradeCenter.Card.Card;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class DoneDealScene {

    /**
     * Shows a closed trade
     * @param trade Closed trade to show
     * @param myUser User logged
     * @param otherUser Other user ivolved in the trade
     * @return BorderPane with the trade
     */
    static BorderPane display(Trade trade, String myUser, String otherUser){

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #82c460");
        BorderPane myBorder = new BorderPane();
        BorderPane otherBorder = new BorderPane();
        JFXButton back = new JFXButton("Back to Trades");
        HBox hBox = new HBox();
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        buttonBox.setStyle("-fx-background-color: orange");
        buttonBox.setPadding(new Insets(5));
        buttonBox.getChildren().add(back);
        hBox.setFillHeight(true);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        ScrollPane myScroll = new ScrollPane();
        myScroll.setFitToHeight(true);
        myScroll.setFitToWidth(true);
        myScroll.setStyle("-fx-background-color: #ffe37e");
        ScrollPane otherScroll = new ScrollPane();
        otherScroll.setFitToHeight(true);
        otherScroll.setFitToWidth(true);
        otherScroll.setStyle("-fx-background-color: #ffe37e");
        TextFlow myTitle = new TextFlow(new Text(myUser+"'s new cards"));
        HBox myTitleBox = new HBox();
        myTitleBox.setPadding(new Insets(5));
        myTitleBox.getChildren().add(myTitle);
        myTitleBox.setStyle("-fx-background-color: #ffe37e");
        TextFlow otherTitle = new TextFlow(new Text(otherUser+"'s new cards"));
        HBox otherTitleBox = new HBox();
        otherTitleBox.setPadding(new Insets(5));
        otherTitleBox.getChildren().add(otherTitle);
        otherTitleBox.setStyle("-fx-background-color: #ffe37e");
        myScroll.setContent(populateFlow(trade.getOffer2()));
        otherScroll.setContent(populateFlow(trade.getOffer1()));
        myBorder.setTop(myTitleBox);
        myBorder.setCenter(myScroll);
        otherBorder.setTop(otherTitleBox);
        otherBorder.setCenter(otherScroll);
        hBox.getChildren().addAll(myBorder, otherBorder);
        borderPane.setCenter(hBox);
        borderPane.setTop(buttonBox);
        back.setOnAction(event -> {
            MainWindow.refreshDynamicContent(ListTradesScene.refresh());
        });
        return borderPane;
    }

    /**
     * Populate the FlowPane withe the new cards of the customers
     * @param offer Cards to add to the FlowPane
     * @return FlowPane with the cards
     */
    static FlowPane populateFlow(Collection offer){
        FlowPane flowPane = new FlowPane();
        for(Card card : offer){
            BorderPane cardPane = new BorderPane();
            Image image = SwingFXUtils.toFXImage(card.getDescription().getPic(), null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(161);
            cardPane.setCenter(imageView);
            ScaleTransition ft = new ScaleTransition(Duration.millis(500), cardPane);
            ft.setFromX(0);
            ft.setFromY(0);
            ft.setToX(1);
            ft.setToY(1);
            ft.setAutoReverse(true);
            ft.play();
            flowPane.getChildren().add(cardPane);
            flowPane.setMargin(cardPane, new Insets(10, 5, 10, 5));
        }
        flowPane.setStyle("-fx-background-color: #57ae78");
        return flowPane;
    }
}
