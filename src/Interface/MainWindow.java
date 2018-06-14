package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import Interface.searchCard.SearchCardScene;
import TradeCenter.Customers.Customer;
import javafx.application.Platform;
import javafx.geometry.Insets;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainWindow {

    /**
     * Pane for dynamic/changing content
     */
    static StackPane dynamicContent;

    /**
     * Display the window
     * @param customer //todo complete javadocs
     */
    public static void display(Customer customer) throws IOException, ClassNotFoundException {

        // values for testing purpose
        /*Customer tempUser = new Customer("01", login_username, "APassword123");
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
        }*/

        // actual MainWindow stuff
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("MAIN MENU");
        // Background
        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(" -fx-background-color: linear-gradient(from 35% 55% to 100% 100%, #1f45da, #abb5ff)");
        // Box for buttons on the left
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20, 10, 20, 20));
        vbox.setSpacing(190);
        // Username label
        Label username = new Label(customer.getUsername());
        username.setTextFill(Color.web("#ffffff"));
        username.setStyle("-fx-font-weight: bold");
        // Tile of buttons for main navigation
        VBox tileButtons = new VBox();
        tileButtons.setAlignment(Pos.CENTER_LEFT);
        //tileButtons.setPrefRows(5);
        tileButtons.setPadding(new Insets(20, 10, 20, 10));
        tileButtons.setSpacing(8);
        //tileButtons.setHgap(10.0);
        //tileButtons.setVgap(8.0);
        // Action buttons
        Button myCollection = new Button("My Collection");
        Button myWishlist = new Button("My Wishlist");
        Button searchCard = new Button("Search a card");
        Button searchUser = new Button("Search a user");
        Button myTrades = new Button("My Trades");
        Button logOut = new Button("LOG OUT");
        // Populate tileButtons and vbox
        tileButtons.getChildren().addAll(myCollection, myWishlist, myTrades, searchCard, searchUser);
        vbox.getChildren().addAll(username, tileButtons, logOut);
        // Dynamic Content pane
        dynamicContent = new StackPane();
        dynamicContent.setAlignment(Pos.TOP_RIGHT);
        dynamicContent.setStyle("-fx-background-color: transparent;");
        dynamicContent.setMinSize(900,600);
        dynamicContent.setMaxSize(900,600);
        // Populate layout
        layout.getChildren().addAll(vbox, dynamicContent);

        // Action events for the buttons
        myCollection.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            try {
                dynamicContent.getChildren().add(CollectionScene.display(retriveCustomer(customer), retriveCustomer(customer).getUsername(), false));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        myWishlist.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(WishListScene.display(retriveCustomer(customer).getWishList(), retriveCustomer(customer)));
        });
        searchCard.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(SearchCardScene.display(retriveCustomer(customer)));
        });
        searchUser.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(SearchUserScene.display(retriveCustomer(customer)));
        });
        myTrades.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(ListTradesScene.display(retriveCustomer(customer)));
        });
        logOut.setOnAction(event -> {
            window.close();
            Platform.exit();
        });

        // Beginning view set on customer's CollectionScene
        dynamicContent.getChildren().add(CollectionScene.display(customer, customer.getUsername(), false));

        // Prepare scene and dispay it
        Scene scene = new Scene(layout, 1200, 700);
        scene.getStylesheets().add("Interface/ButtonsCSS.css");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    /**
     * Clear Dynamic Content pane and adds the selected node
     * @param node to be displayed in the pane
     */
    public static void refreshDynamicContent(Node node) {
        dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
        dynamicContent.getChildren().add(node);

    }

    //todo add javadocs
    private static Customer retriveCustomer(Customer customer){
        Customer updatedCustomer = null;
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8889);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, customer.getUsername()));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            updatedCustomer = (Customer)is.readObject();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return updatedCustomer;
    }

}
