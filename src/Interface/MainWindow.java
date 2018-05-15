package Interface;

import TradeCenter.Card.Description;
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

    // Nell'actionevent di login aggiungere MainWindow.display(username.getText()); per visualizzare questa finestra

    static StackPane dynamicContent;

    public static void display(String login_username){

        Customer tempUser = new Customer("01", login_username, "APassword123");
        try {
            tempUser.addCardToWishList(new YuGiOhDescription("Ancient Dragon", "an ancient dragon", "./database/DB_yugioh/yugioh_pics/AncientDragon-YS15-EU-C-1E.png", "cos",0,0,0,0,0)); {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("MAIN MENU");

        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);
        //String image = MainWindow.class.getResource("sfondoKrenar.jpg").toExternalForm();
        /*layout.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center left; " +
                "-fx-background-repeat: stretch;");*/
        layout.setStyle(" -fx-background-color: linear-gradient(from 35% 55% to 100% 100%, #1f45da, #abb5ff)");//#3b5998)");

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
            LogIn.display();
        });

        myWishlist.setOnAction(event -> {
            dynamicContent.getChildren().add(WishListScene.display(tempUser.getWishList(), tempUser.getUsername()));
        });

        myCollection.setOnAction(event -> {
            dynamicContent.getChildren().add(CollectionScene.display(tempUser.getCollection(), tempUser.getUsername()));
        });

        dynamicContent.getChildren().add(CollectionScene.display(tempUser.getCollection(), tempUser.getUsername()));

        Scene scene = new Scene(layout, 1200, 700);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void refreshDynamicContent(Node node) {
        dynamicContent.getChildren().removeAll();
        dynamicContent.getChildren().add(node);

    }

}
