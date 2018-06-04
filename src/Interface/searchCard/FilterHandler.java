package Interface.searchCard;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import Interface.MainWindow;
import Interface.SearchDescriptionScene;
import Interface.searchCard.filterChoice.PokemonAll;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;

public class FilterHandler implements EventHandler<ActionEvent> {

    Customer customer;
    public FilterHandler(Customer customer) {
        this.customer=customer;
    }

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

                    String comboTypePo= (String) PokemonFilter.getComboType().getValue();
                    int hpValue=(int) PokemonFilter.getHpSlider().getValue();
                    int levValue=(int)PokemonFilter.getLevSlider().getValue();
                    int weightValue=(int)PokemonFilter.getWeightSlider().getValue();
                    String len1=PokemonFilter.getTextLen1().getText(); //TODO se non digitato niente non è null.. faccio controllo nell'oggeto PokemonAll
                    String len2=PokemonFilter.getTextLen2().getText();
                    mainBorder.setBottom(new Label(comboTypePo+"    len1: "+len1+"    len2: "+len2+"    HP: "+(hpValue)+"    LEV: "+(levValue)+"    WEIGHT: "+(weightValue)));
                    Socket socket;
                    try {
                        socket = new Socket("localhost", 8889);
                        System.out.println("Client connected");
                        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                        System.out.println("Ok");
                        os.writeObject(new MessageServer(MessageType.FILTERPOKEMONDESCR, new PokemonAll(comboTypePo,hpValue,levValue,weightValue,len1,len2)));
                        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                        ArrayList<HashMap<Customer, Collection>> returnMessage = (ArrayList<HashMap<Customer,Collection>>) is.readObject();
                        if(returnMessage.size()>=1) { //todo provvisorio
                            MainWindow.refreshDynamicContent(SearchDescriptionScene.display(returnMessage, customer));
                        }
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "YU-GI-OH!":
                    String comboMonster= (String) YugiohFilter.getComboMonster().getValue();
                    String comboTypeYu= (String) YugiohFilter.getComboType().getValue();
                    Double atkValue=YugiohFilter.getAtkSlider().getValue();
                    Double defValue=YugiohFilter.getDefSlider().getValue();
                    mainBorder.setCenter(new Label(comboTypeYu+"    Monster:"+comboMonster+"    ATK:"+atkValue+"    DEF:"+defValue));
                    break;
                //case "NOTHING" todo quando non viene selezionato tipo di carta-->cerco solo stringa

            }
        }

    }

}
