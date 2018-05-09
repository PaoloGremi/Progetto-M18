package Interface;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindow{

    Stage mainWindow;

    // Nell'actionevent di login aggiungere MainWindow.display(); per visualizzare questa finestra

    public static void display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("MAIN MENU");

        StackPane layout = new StackPane();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(8);

        Label username = new Label("USERNAME"); //todo passare username da login

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button myCollection = new Button("My Collection");
        Button myWishlist = new Button("My Wishlist");
        Button searchCard = new Button("Search a card...");
        Button searchUser = new Button("Search a user...");
        Button myTrades = new Button("My Trades");
        Button logOut = new Button("LOG OUT");

        logOut.setOnAction(event -> {
            window.close();
            Platform.exit();
        });

        grid.add(myCollection,0,0);
        grid.add(myWishlist,0,1);
        grid.add(myTrades,0,2);
        grid.add(searchCard,0,3);
        grid.add(searchUser,0,4);

        VBox dynamicContent = new VBox();
        dynamicContent.setAlignment(Pos.TOP_RIGHT);

        // todo sistemare il contenuto dinamico (wishlist, trade, etc)

        vbox.getChildren().add(username);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(logOut);

        vbox.getChildren().add(dynamicContent);

        Scene scene = new Scene(vbox, 1200, 700);
        window.setScene(scene);
        window.show();
    }

}
