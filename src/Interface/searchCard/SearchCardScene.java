package Interface.searchCard;


import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class SearchCardScene {
    static BorderPane mainBorder;
    static HBox hBoxTop;
    static VBox vBoxTop;
    static HBox hBoxInner;
    //filtri
    static VBox vBoxFilter;
    static JFXComboBox comboCardType;
    static TextField searchText;


    public static BorderPane display(Customer customer){
        mainBorder=new BorderPane();
        hBoxTop=new HBox();
        hBoxInner=new HBox();
        vBoxTop=new VBox();

        mainBorder.setPadding(new Insets(5));
        mainBorder.setStyle("-fx-background-color: orange");

        Button buttSearch=new Button("Search  "+ "\uD83D\uDD0D");
        buttSearch.setAlignment(Pos.BOTTOM_RIGHT);
        searchText=new TextField("");
        /***parte alta*/
        hBoxInner.setPadding(new Insets(3));
        hBoxInner.setSpacing(18);
        hBoxInner.getChildren().add(buttSearch);


        vBoxTop.getChildren().addAll(searchText,hBoxInner);
        hBoxTop.getChildren().addAll(vBoxTop);
        /**action Searc**/
        buttSearch.setOnAction(new FilterHandler(customer));

        /**filtri**/

        vBoxFilter=new VBox();

        //hBoxFilterTop=new HBox();
        comboCardType=new JFXComboBox();
        comboCardType.setPromptText("Choose type of Card");

        vBoxFilter.setPadding(new Insets(5));
        vBoxFilter.setStyle("-fx-border-color:orange;-fx-border-width:5px;-fx-background-color: DAE6A2;");

        comboCardType.getItems().addAll( "--Nothing--","Pokèmon","YU-GI-OH!");
        //action del combo
        comboCardType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch ((String) comboCardType.getValue()) {
                    case ("Pokèmon"):
                        if (vBoxFilter.getChildren().size() == 2)
                            vBoxFilter.getChildren().removeAll(vBoxFilter.getChildren().get(1));
                        vBoxFilter.getChildren().add(PokemonFilter.display());
                        break;
                    case ("YU-GI-OH!"):
                        if (vBoxFilter.getChildren().size() == 2)
                            vBoxFilter.getChildren().removeAll(vBoxFilter.getChildren().get(1));
                        vBoxFilter.getChildren().add(YugiohFilter.display());
                        break;
                    case ("--Nothing--"):
                        if (vBoxFilter.getChildren().size() == 2)
                            vBoxFilter.getChildren().removeAll(vBoxFilter.getChildren().get(1));
                        break;
                }
            }
        });
        Pane paneProva=new Pane();
        paneProva.setStyle("-fx-background-color: DAE6A2;");
        mainBorder.setCenter(paneProva);
        vBoxFilter.getChildren().add(comboCardType);
        mainBorder.setTop(hBoxTop);
        mainBorder.setLeft(vBoxFilter);
        mainBorder.getStylesheets().add("Interface/ComboBox.css");

        return mainBorder;
    }

    public static TextField getSearchText() {

        return searchText;
    }
}
