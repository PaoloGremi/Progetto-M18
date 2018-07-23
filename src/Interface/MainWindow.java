package Interface;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import Interface.searchCard.SearchCardScene;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainWindow {

    /**
     * Pane for dynamic/changing content
     */
    private static StackPane dynamicContent;

    /**
     * Display the window
     * @param customer's window displayed
     */
    public static void display(Customer customer) throws IOException, ClassNotFoundException {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("MAIN MENU");
        // Background
        BorderPane layout = new BorderPane();

        layout.setStyle(" -fx-background-color: red");
        // Box for buttons on the left
        JFXDrawer drawer = new JFXDrawer();
        drawer.setPrefWidth(150);
        drawer.setStyle("-fx-background-color: rgba(255,124,0,0.65);");
        JFXHamburger hamburger = new JFXHamburger();
        HBox pane = new HBox();
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.BASELINE_LEFT);
        pane.setSpacing(200);
        pane.getChildren().add(hamburger);
        //pane.setStyle("-fx-background-color: rgba(255,0,9,0.65);");
        hamburger.visibleProperty().set(true);
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.setOnMouseClicked(event -> {
            if(drawer.isOpened()) {
                transition.setRate(transition.getRate()*-1);
                transition.play();
                drawer.close();
            }
            else {
                transition.setRate(transition.getRate()*-1);
                transition.play();
                drawer.open();
            }
        });

        VBox vbox = new VBox();
        //vbox.setPrefWidth();
        vbox.setStyle("-fx-background-color: #ff0009;");

        //drawer.setOverLayVisible(true);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20, 10, 20, 10));
        vbox.setSpacing(130);
        // Username label
        Label username = new Label(customer.getUsername());
        username.setAlignment(Pos.CENTER);
        username.setTextFill(Color.web("#ffffff"));
        username.setStyle("-fx-font-weight: bold");
        // Tile of buttons for main navigation
        VBox tileButtons = new VBox();
        tileButtons.setAlignment(Pos.CENTER);
        tileButtons.setPadding(new Insets(20, 5, 20, 5));
        tileButtons.setSpacing(8);
        // Action buttons
        JFXButton myCollection = new JFXButton("My Collection");
        JFXButton myWishlist = new JFXButton("My Wishlist");
        JFXButton searchCard = new JFXButton("Search a card");
        JFXButton searchUser = new JFXButton("Search a user");
        JFXButton myTrades = new JFXButton("My Trades");
        JFXButton logOut = new JFXButton("LOG OUT");
        myCollection.setButtonType(JFXButton.ButtonType.RAISED);
        myWishlist.setButtonType(JFXButton.ButtonType.RAISED);
        searchCard.setButtonType(JFXButton.ButtonType.RAISED);
        searchUser.setButtonType(JFXButton.ButtonType.RAISED);
        myTrades.setButtonType(JFXButton.ButtonType.RAISED);
        logOut.setButtonType(JFXButton.ButtonType.RAISED);
        // Populate tileButtons and vbox
        tileButtons.getChildren().addAll(myCollection, myWishlist, myTrades, searchCard, searchUser);
        VBox sidepaneBox = new VBox();
        sidepaneBox.setAlignment(Pos.CENTER);
        sidepaneBox.setSpacing(500);
        sidepaneBox.getChildren().addAll(username,logOut);
        vbox.getChildren().add(sidepaneBox);
        drawer.setContent(tileButtons);
        drawer.setAlignment(Pos.CENTER);
        drawer.setDefaultDrawerSize(600);
        drawer.setSidePane(vbox);
        //drawer.getSidePane().add(logOut);
        // Dynamic Content pane
        dynamicContent = new StackPane();
        dynamicContent.setAlignment(Pos.TOP_RIGHT);
        dynamicContent.setStyle("-fx-background-color: transparent;");
        dynamicContent.setMinSize(900,600);
        dynamicContent.setMaxSize(900,600);
        // Populate layout
        //layout.getChildren().addAll(pane,drawer , dynamicContent);
        layout.setTop(pane);
        //mainBox.getChildren().addAll(pane, drawer);
        layout.setLeft(drawer);
        layout.setCenter(dynamicContent);

        // Action events for the buttons
        myCollection.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            long startTime = System.currentTimeMillis();
            try {
                Customer updatedCustomer = SearchUserScene.retrieveCustomer(customer.getUsername());
                dynamicContent.getChildren().add(CollectionScene.display(updatedCustomer, updatedCustomer.getUsername()));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
        });
        myWishlist.setOnAction(event -> {
            Customer updatedCustomer = SearchUserScene.retrieveCustomer(customer.getUsername());
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(WishListScene.display(updatedCustomer.getWishList(), updatedCustomer));
        });
        searchCard.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(SearchCardScene.display(SearchUserScene.retrieveCustomer(customer.getUsername())));
        });
        searchUser.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(SearchUserScene.display(customer));
        });
        myTrades.setOnAction(event -> {
            dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
            dynamicContent.getChildren().add(ListTradesScene.display(SearchUserScene.retrieveCustomer(customer.getUsername())));
        });
        logOut.setOnAction(event -> {
            logOut(customer.getUsername());
            window.close();
            Platform.exit();
        });

        // Beginning view set on customer's CollectionScene
        dynamicContent.getChildren().add(CollectionScene.display(customer, customer.getUsername()));

        // Prepare scene and dispay it
        Scene scene = new Scene(layout, 1200, 700);
        scene.getStylesheets().add("Interface/ButtonsCSS.css");
        window.setScene(scene);
        window.setResizable(false);
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                logOut(customer.getUsername());
            }
        });
        window.show();
    }

    /**
     * A Method used to log out
     * @param username of the customer who want to log out
     */
    private static void logOut(String username){
        Socket socket = null;
        try {
            socket = new Socket(ServerIP.ip, ServerIP.port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new MessageServer(MessageType.LOGOUT, username));
            Thread.sleep(30);
            os.flush();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Clear Dynamic Content pane and adds the selected node
     * @param node to be displayed in the pane
     */
    public static void refreshDynamicContent(Node node) {
        dynamicContent.getChildren().removeAll(dynamicContent.getChildren());
        dynamicContent.getChildren().add(node);
    }

    /**
     * Add a Dynamic Content pane
     * @param node to be displayed in the pane
     */
    public static void addDynamicContent(Node node){
        ScaleTransition ft = new ScaleTransition(Duration.millis(500), node);
        ft.setFromX(0);
        ft.setFromY(0);
        ft.setToX(1);
        ft.setToY(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();
        dynamicContent.getChildren().add(node);
    }

    /**
     * Remove the Dynamic Content pane
     * @param node to be removed
     */
    public static void removeDynamicContent(Node node){
        dynamicContent.getChildren().removeAll(node);
    }

}
