package Interface;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindow {

    // Nell'actionevent di login aggiungere MainWindow.display(username.getText()); per visualizzare questa finestra

    public static void display(String login_username){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("MAIN MENU");

        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);
        String image = MainWindow.class.getResource("sfondoKrenar.jpg").toExternalForm();
        layout.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center left; " +
                "-fx-background-repeat: stretch;");

        VBox vbox = new VBox();
            vbox.setAlignment(Pos.TOP_LEFT);
            vbox.setPadding(new Insets(20));
            vbox.setSpacing(8);

            Label username = new Label(login_username);
            username.setTextFill(Color.web("#ffffff"));
            username.setStyle("-fx-font-weight: bold");

            TilePane tileButtons = new TilePane(Orientation.VERTICAL);
                tileButtons.setTileAlignment(Pos.CENTER_LEFT);
                tileButtons.setPrefRows(5);
                tileButtons.setPadding(new Insets(20, 10, 20, 0));
                tileButtons.setHgap(10.0);
                tileButtons.setVgap(8.0);

                Button myCollection = new Button("My Collection");
                Button myWishlist = new Button("My Wishlist");
                Button searchCard = new Button("Search a card...");
                Button searchUser = new Button("Search a user...");
                Button myTrades = new Button("My Trades");
                Button logOut = new Button("LOG OUT");

                tileButtons.getChildren().addAll(myCollection, myWishlist, myTrades, searchCard, searchUser);

        vbox.getChildren().addAll(username, tileButtons, logOut);

        StackPane dynamicContent = new StackPane();
            dynamicContent.setAlignment(Pos.TOP_RIGHT);
            dynamicContent.setStyle("-fx-background-color: DAE6A2;");
            dynamicContent.setMinSize(900,600);
            dynamicContent.setMaxSize(900,600);

        layout.getChildren().addAll(vbox, dynamicContent);

        logOut.setOnAction(event -> {
            window.close();
            LogIn.display();
        });

        myWishlist.setOnAction(event -> {
            dynamicContent.getChildren().add(WishListScene.display());
        });

        Scene scene = new Scene(layout, 1200, 700);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

}
