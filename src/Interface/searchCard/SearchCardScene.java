package Interface.searchCard;


import Interface.searchCard.PokemonFilter;
import Interface.searchCard.YugiohFilter;
import TradeCenter.Customers.Customer;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;


public class SearchCardScene {
    static BorderPane mainBorder;
    static HBox hBoxTop;
    static VBox vBoxTop;
    static HBox hBoxInner;
    //filtri
    static VBox vBoxFilter;
    static JFXComboBox comboCardType;


    public static BorderPane display(Customer customer){
        mainBorder=new BorderPane();
        hBoxTop=new HBox();
        hBoxInner=new HBox();
        vBoxTop=new VBox();

        mainBorder.setPadding(new Insets(5));
        mainBorder.setStyle("-fx-background-color: orange");

        Button buttSearch=new Button("Search  "+ "\uD83D\uDD0D");
        Button buttFilter=new Button("Add Filters");
        buttFilter.setStyle("-fx-border-color:blue;-fx-border-width:2px;");
        TextField searchText=new TextField("    ");
        /***parte alta*/
        hBoxInner.setPadding(new Insets(3));
        hBoxInner.setSpacing(18);
        hBoxInner.getChildren().add(buttFilter);
        hBoxInner.getChildren().add(buttSearch);


        vBoxTop.getChildren().addAll(searchText,hBoxInner);
        hBoxTop.getChildren().addAll(vBoxTop);
        /**action Searc**/
        buttFilter.setOnAction(new FilterHandler(customer));

        /**filtri**/

        vBoxFilter=new VBox();
        //hBoxFilterTop=new HBox();
        comboCardType=new JFXComboBox();
        comboCardType.setPromptText("Choose type of Card");

        vBoxFilter.setPadding(new Insets(5));
        vBoxFilter.setStyle("-fx-border-color:orange;-fx-border-width:5px;-fx-background-color: DAE6A2;");

        comboCardType.getItems().addAll( //farlo dinamico
          "Pokèmon",
          "YU-GI-OH!"
        );
        //action del combo
        comboCardType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(comboCardType.getValue().equals("Pokèmon")){
                   if(vBoxFilter.getChildren().size()==2)
                   vBoxFilter.getChildren().removeAll(vBoxFilter.getChildren().get(1));
                   vBoxFilter.getChildren().add(PokemonFilter.display());
                }
                if (comboCardType.getValue().equals("YU-GI-OH!")){
                    if(vBoxFilter.getChildren().size()==2)
                    vBoxFilter.getChildren().removeAll(vBoxFilter.getChildren().get(1));
                    vBoxFilter.getChildren().add(YugiohFilter.display());

                }
            }
        });


        Pane paneProva=new Pane();
        paneProva.setStyle("-fx-background-color: DAE6A2;");
        mainBorder.setCenter(paneProva);
        vBoxFilter.getChildren().add(comboCardType);
        mainBorder.setTop(hBoxTop);
        mainBorder.setLeft(vBoxFilter);
        return mainBorder;
    }
}
