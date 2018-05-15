package Interface;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sun.applet.Main;

public class SearchCardScene {
    static BorderPane mainBorder;
    static HBox hBoxTop;
    static VBox vBoxTop;
    static HBox hBoxInner;
    //filtri
    static VBox vBoxFilter;
    static HBox hBoxFilterTop;


    static BorderPane display(){
        mainBorder=new BorderPane();
        hBoxTop=new HBox();
        hBoxInner=new HBox();
        vBoxTop=new VBox();

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

        hBoxFilterTop.setPadding(new Insets(3));




        mainBorder.setTop(hBoxTop);
        return mainBorder;
    }
}
