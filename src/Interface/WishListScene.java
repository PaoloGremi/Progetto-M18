package Interface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;

public class WishListScene {
    static boolean flag = true;
    static File[] files = generateFiles();
    static ArrayList<File> files1;
    static ScrollPane scroll;

    static ScrollPane display()  {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        HBox hbox1;
        ImageView card;

        for(File file2 : files1){
            BorderPane pane = new BorderPane();

            hbox1 = new HBox();
            hbox1.setPadding(new Insets(20, 10, 20, 0));
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
            card = new ImageView();
            card.setImage(image3);

            pane.setCenter(card);
            pane.setBottom(hbox1);

            button1.setOnAction(event -> {

                for (File file4 : files){
                    if(file4.equals(file2)) {
                        flow.getChildren().remove(pane);
                        WishListScene.removeFile(files1,file4);
                    }
                }

            });
            flow.getChildren().add(pane);
        }
        return scroll;
    }

    static void removeFile(ArrayList<File> files, File file){
        files.remove(file);
    }

    static File[] generateFiles() {
        if(flag) {
            files = new File("database/DB_yugioh/yugioh_pics/").listFiles();
            files1 = new ArrayList<>();
            flag=false;
            for(int i=0; i<files.length; i++){
                files1.add(files[i]);
            }
        }
        return files;
    }

}


