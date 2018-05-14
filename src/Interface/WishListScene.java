
package Interface;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.ArrayList;

import static javafx.scene.input.KeyCode.U;

public class WishListScene{
    static boolean flag = true;
    static File[] files = generateFiles();
    static ArrayList<File> files1;
    static ScrollPane scroll;


    static ScrollPane display()  {

        FlowPane flow = new FlowPane();

        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        HBox hbox1;


        for(File file2 : files1){
            BorderPane pane = new BorderPane();
            Pane pane2 = new Pane();
            pane.setPadding(new Insets(0));

            hbox1 = new HBox();
            hbox1.setPadding(new Insets(10));
            hbox1.setSpacing(10);
            hbox1.setStyle("-fx-background-color: orange");

            Button button1 = new Button("Remove "+ "\uD83D\uDD71");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button1);

            Button button2 = new Button("Search " + "\uD83D\uDD0D");
            button1.setPrefSize(100, 20);
            hbox1.getChildren().add(button2);


            File file3 = new File(file2.getPath());
            Image image3 = new Image(file3.toURI().toString());
            ImageView card = new ImageView();
            card.setImage(image3);
            pane2.getChildren().add(card);
            card.setPreserveRatio(true);
            card.setFitHeight(313);

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

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(card, "wish"));
                        }
                    };

            card.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(5, 0, 5, 0));
        }
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
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
