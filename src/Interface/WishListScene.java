package Interface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javax.swing.*;
import javax.swing.text.Element;
import java.io.File;

public class WishListScene extends Application {

    Stage wishListWindow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        wishListWindow = primaryStage;
        wishListWindow.setTitle("Wish List");


        BorderPane border = new BorderPane();

        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(20);
        flow.setPrefWrapLength(500);
        flow.setStyle("-fx-background-color: DAE6A2;");

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);


        File file = new File("database/DB_yugioh/yugioh_pics/AlexandriteDragon-YS15-EU-C-1E.png");
        Image image  = new Image(file.toURI().toString());
        File file1 = new File("database/DB_yugioh/yugioh_pics/BrightStarDragon-YS15-EU-C-1E.png");
        Image image2 = new Image(file1.toURI().toString());

        /*ImageView pages = new ImageView();
        ImageView pages2 = new ImageView();
        pages.setImage(image);
        pages2.setImage(image2);
        flow.getChildren().add(pages);
        flow.getChildren().add(pages2);*/

        File[] files = new File("database/DB_yugioh/yugioh_pics/").listFiles();

        for(File file2 : files){
            BorderPane border2 = new BorderPane();
            HBox hbox1 = new HBox();
            hbox1.setPadding(new Insets(15, 12, 15, 12));
            hbox1.setSpacing(10);
            hbox1.setStyle("-fx-background-color: #336699;");

            Button button1 = new Button("Reomve");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button1);

            Button button2 = new Button("Search");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button2);

            File file3 = new File(file2.getPath());
            Image image3 = new Image(file3.toURI().toString());
            ImageView card = new ImageView();
            card.setImage(image3);
            border.setCenter(card);
            border.setBottom(hbox1);
            Pane pane = new Pane();
            pane.getChildren().add(card);
            pane.getChildren().add(hbox1);
            flow.getChildren().add(pane);
        }


        //border.setCenter(scroll);
        //border.setBottom(button);

        //layout.getChildren().add(scroll);
        //layout.getChildren().add(hbox);
        //layout.getChildren().add(flow);


        Scene scene = new Scene(scroll, 1200, 700);
        wishListWindow.setScene(scene);
        wishListWindow.show();

    }
}
