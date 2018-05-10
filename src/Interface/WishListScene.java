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
import java.util.ArrayList;

public class WishListScene {

    Stage wishListWindow;
    static int i = 0;
    static File[] files;
    static ArrayList<File> files1;
    static void display()  {
       // wishListWindow = primaryStage;
        //wishListWindow.setTitle("Wish List");

        Stage window = new Stage();


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


        HBox hbox1;
        Button button1 = new Button("Remove");
        ImageView card;

        Scene scene = new Scene(border, 1350, 720);

        if(i==0) {
            files = new File("database/DB_yugioh/yugioh_pics/").listFiles();
            files1 = new ArrayList<>();
            i++;
            for(int i=0; i<files.length; i++){
                files1.add(files[i]);
            }
        }



        for(File file2 : files1){
            BorderPane pane = new BorderPane();
            hbox1 = new HBox();
            hbox1.setPadding(new Insets(8, 6, 8, 6));
            hbox1.setSpacing(10);
            hbox1.setStyle("-fx-background-color: orange");

            button1 = new Button("Remove");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button1);

            Button button2 = new Button("Search");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button2);

            File file3 = new File(file2.getPath());
            Image image3 = new Image(file3.toURI().toString());
            card = new ImageView();
            card.setImage(image3);

            pane.setCenter(card);
            pane.setBottom(hbox1);

            button1.setOnAction(event -> {

                for (File file4 : files){
                    if(file4.equals(file2)) { WishListScene.removeFile(files1,file4);}
                }

                border.requestLayout();
                WishListScene.display();

            });


            flow.getChildren().add(pane);
        }

        border.setCenter(scroll);

        /*wishListWindow.setScene(scene);
        wishListWindow.setResizable(false);
        wishListWindow.show();*/

        window.setScene(scene);
        window.show();

    }

    static void removeFile(ArrayList<File> files, File file){
        files.remove(file);
    }

}


