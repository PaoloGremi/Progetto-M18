package Interface;

import TradeCenter.Card.Card;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Customer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow {

    static StackPane dynamicContent;

    public static void display(String login_username){

        // values for testing purpose
        Customer tempUser = new Customer("01", login_username, "APassword123");
        try {
            tempUser.addCardToWishList(new YuGiOhDescription("Ancient Dragon", "an ancient dragon", "./database/DB_yugioh/yugioh_pics/AncientDragon-YS15-EU-C-1E.png", "cos",0,0,0,0,0)); {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            tempUser.addCard(new Card(0, new PokemonDescription("Blastoise", "A better squirtle", "./database/DB_pokemon/pokemon_pics/BS_002.jpg", 002,"Water",100,189,"5'3''",52 )));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("MAIN MENU");

        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(" -fx-background-color: linear-gradient(from 35% 55% to 100% 100%, #1f45da, #abb5ff)");

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20, 10, 20, 20));
        vbox.setSpacing(200);

        Label username = new Label(login_username);
        username.setTextFill(Color.web("#ffffff"));
        username.setStyle("-fx-font-weight: bold");

        TilePane tileButtons = new TilePane(Orientation.VERTICAL);
        tileButtons.setTileAlignment(Pos.CENTER_LEFT);
        tileButtons.setPrefRows(5);
        tileButtons.setPadding(new Insets(20, 10, 20, 10));
        tileButtons.setHgap(10.0);
        tileButtons.setVgap(8.0);

        Button myCollection = new Button("My Collection");
        Button myWishlist = new Button("My Wishlist");
        Button searchCard = new Button("Search a card");
        Button searchUser = new Button("Search a user");
        Button myTrades = new Button("My Trades");
        Button logOut = new Button("LOG OUT");

        tileButtons.getChildren().addAll(myCollection, myWishlist, myTrades, searchCard, searchUser);

        vbox.getChildren().addAll(username, tileButtons, logOut);

        dynamicContent = new StackPane();
        dynamicContent.setAlignment(Pos.TOP_RIGHT);
        dynamicContent.setStyle("-fx-background-color: transparent;");
        dynamicContent.setMinSize(900,600);
        dynamicContent.setMaxSize(900,600);

        layout.getChildren().addAll(vbox, dynamicContent);

        logOut.setOnAction(event -> {
            window.close();
            Platform.exit();
        });

        myWishlist.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(WishListScene.display(tempUser.getWishList(), tempUser.getUsername()));
        });

        myCollection.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(CollectionScene.display(tempUser, tempUser.getUsername(), false));
        });

        searchCard.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(SearchCardScene.display());
        });

        //prova Gore
        myTrades.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(OtherUserProfileScene.display(tempUser));
        });

        dynamicContent.getChildren().add(CollectionScene.display(tempUser, tempUser.getUsername(), false));

        Scene scene = new Scene(layout, 1200, 700);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void refreshDynamicContent(Node node) {
        dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
        dynamicContent.getChildren().add(node);

    }

}
