package Interface;

import TradeCenter.Card.Card;
import TradeCenter.Customers.Collection;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.util.ArrayList;

public class CollectionScene{
    static boolean flag = true;
    static File[] files;
    static ArrayList<File> files1;
    static ScrollPane scroll;
    static Collection coll;
    static String user;

    static HBox hbox;

    static BorderPane display(Collection collection, String username)  {

        coll=collection;
        user=username;
        //files = generateFiles(url);
        BorderPane border = new BorderPane();
        Button buttonAdd= new Button("Add Card \uD83C\uDCCF");
        hbox = new HBox();
        hbox.setPadding(new Insets(5));
        hbox.setSpacing(670);
        hbox.setStyle("-fx-background-color: orange");

        TextFlow textFlow = new TextFlow();
        textFlow.setPadding(new Insets(5));
        Text text = new Text(username +"'s wish list");
        text.setStyle("-fx-font-weight: bold");
        textFlow.getChildren().add(text);
        hbox.getChildren().add(textFlow);
        hbox.getChildren().add(buttonAdd);

        FlowPane flow = new FlowPane();

        flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setStyle("-fx-background-color: DAE6A2;");

        scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(flow);

        for(Card file2 : collection){
            BorderPane pane = new BorderPane();

            pane.setPadding(new Insets(5,0,0,5));

            //File file3 = new File(file2.getDescription().getPicUrl());
            //Image image3 = new Image(file3.toURI().toString());
            Image image3 = SwingFXUtils.toFXImage(file2.getDescription().getPic(),null);
            ImageView card = new ImageView();
            card.setImage(image3);
            card.setPreserveRatio(true);
            card.setFitHeight(285);

            pane.setCenter(card);

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerBox =
                    new EventHandler<javafx.scene.input.MouseEvent>() {

                        @Override
                        public void handle(javafx.scene.input.MouseEvent e) {
                            MainWindow.refreshDynamicContent(Demo.display(card, "collection"));
                        }
                    };

            card.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

            flow.getChildren().add(pane);
            flow.setMargin(pane, new Insets(5, 0, 5, 0));
        }
        scroll.setPadding(new Insets(3));
        scroll.setStyle("-fx-background-color: orange");
        border.setCenter(scroll);
        border.setTop(hbox);
        return border;
    }

    static void removeFile(ArrayList<File> files, File file){
        files.remove(file);
    }

    static File[] generateFiles(String url) {
        if(flag) {
            files = new File(url).listFiles();
            files1 = new ArrayList<>();
            flag=false;
            for(int i=0; i<files.length; i++){
                files1.add(files[i]);
            }
        }
        return files;
    }

    static BorderPane refresh(){
        return display(coll,user);
    }

}

