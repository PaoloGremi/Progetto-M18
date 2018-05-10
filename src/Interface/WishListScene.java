package Interface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;

public class WishListScene extends Application {

    Stage wishListWindow;


    public void start(Stage primaryStage) throws Exception {
        wishListWindow = primaryStage;
        wishListWindow.setTitle("Wish List");


        BorderPane border = new BorderPane();

        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);


        File[] files = new File("database/DB_yugioh/yugioh_pics/").listFiles();

        for(File file2 : files){
            BorderPane pane = new BorderPane();
            HBox hbox1 = new HBox();
            hbox1.setPadding(new Insets(8, 6, 8, 6));
            hbox1.setSpacing(10);
            hbox1.setStyle("-fx-background-color: orange");

            Button button1 = new Button("Remove");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button1);

            Button button2 = new Button("Search");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button2);

            File file3 = new File(file2.getPath());
            Image image3 = new Image(file3.toURI().toString());
            ImageView card = new ImageView();
            card.setImage(image3);

            pane.setCenter(card);
            pane.setBottom(hbox1);

            flow.getChildren().add(pane);
        }

        border.setCenter(scroll);

        Scene scene = new Scene(border, 1350, 720);

        wishListWindow.setScene(scene);
        wishListWindow.setResizable(false);
        wishListWindow.show();

    }


}


