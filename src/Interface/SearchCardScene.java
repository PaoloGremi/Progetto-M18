package Interface;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SearchCardScene {
    static BorderPane mainBorder;
    static HBox hBoxTop;
    static VBox vBoxTop;
    static HBox hBoxInner;
    //filtri
    static VBox vBoxFilter;
    static HBox hBoxFilterTop;
    static ComboBox comboCardType;


    static BorderPane display(){
        mainBorder=new BorderPane();
        hBoxTop=new HBox();
        hBoxInner=new HBox();
        vBoxTop=new VBox();

        mainBorder.setPadding(new Insets(5));
        mainBorder.setStyle("-fx-background-color: orange");

        Button buttSearch=new Button("Search  "+ "\uD83D\uDD0D");
        Button buttFilter=new Button("Filter");
        TextField searchText=new TextField("    ");
        /***parte alta*/
        hBoxInner.setPadding(new Insets(3));
        hBoxInner.setSpacing(18);
        hBoxInner.getChildren().add(buttFilter);
        hBoxInner.getChildren().add(buttSearch);

        vBoxTop.getChildren().addAll(searchText,hBoxInner);
        hBoxTop.getChildren().addAll(vBoxTop);

        /**filtri**/

        vBoxFilter=new VBox();
        hBoxFilterTop=new HBox();
        comboCardType=new ComboBox();
        comboCardType.setPromptText("Choose type of Card");

        vBoxFilter.setPadding(new Insets(5));

        comboCardType.getItems().addAll( //farlo dinamico
          "Pokèmon",
          "YU-GI-OH!"
        );




        vBoxFilter.getChildren().add(comboCardType);
        mainBorder.setTop(hBoxTop);
        mainBorder.setLeft(vBoxFilter);
        return mainBorder;
    }
}
