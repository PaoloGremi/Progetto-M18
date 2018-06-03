package Interface.searchCard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilterHandler implements EventHandler<ActionEvent> {



    @Override
    public void handle(ActionEvent event) {
        Button buttonSearch=(Button) event.getSource();
        HBox hBoxParent=(HBox)buttonSearch.getParent();
        BorderPane mainBorder=(BorderPane)hBoxParent.getParent().getParent().getParent();
        VBox vBoxFilterLeft=(VBox) mainBorder.getLeft();

        if(vBoxFilterLeft.getChildren().size()<2){
            //todo: no filtri selezionati
        }
        if(vBoxFilterLeft.getChildren().size()==2){

            switch ((String)((ComboBox)vBoxFilterLeft.getChildren().get(0)).getValue()){

                case "Pokèmon":
                    ComboBox comboType=(ComboBox)vBoxFilterLeft.getChildren().get(0);
                    break;
                case "YU-GI-OH!":
                    break;
                //case "NOTHING" todo quando non viene selezionato tipo di carta-->cerco solo stringa

            }
        }

    }

}
