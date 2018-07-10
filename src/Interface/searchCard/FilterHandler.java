package Interface.searchCard;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import Interface.MainWindow;
import Interface.SearchDescriptionScene;
import Interface.searchCard.filterChoice.PokemonAll;
import Interface.searchCard.filterChoice.YuGiOhAll;
import TradeCenter.Card.PokemonDescription;
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
import java.util.HashSet;

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
                    //check if null(non modificato)--> ""
                    if(comboTypePo==null)
                        comboTypePo="";
                    int hpValue=(int) PokemonFilter.getHpSlider().getValue();
                    int levValue=(int)PokemonFilter.getLevSlider().getValue();
                    int weightValue=(int)PokemonFilter.getWeightSlider().getValue();
                    String len1=PokemonFilter.getTextLen1().getText();
                    String len2=PokemonFilter.getTextLen2().getText();
                    mainBorder.setBottom(new Label("Type"+comboTypePo+"    len1: "+len1+"    len2: "+len2+"    HP: "+(hpValue)+"    LEV: "+(levValue)+"    WEIGHT: "+(weightValue)));
                    Socket socket1;
                    try {
                        socket1 = new Socket("localhost", 8889);
                        System.out.println("Client connected");
                        ObjectOutputStream os = new ObjectOutputStream(socket1.getOutputStream());
                        System.out.println("Ok");
                        os.writeObject(new MessageServer(MessageType.FILTERPOKEMONDESCR, new PokemonAll(comboTypePo,hpValue,levValue,weightValue,len1,len2)));
                        ObjectInputStream is = new ObjectInputStream(socket1.getInputStream());

                        HashMap<PokemonDescription,ArrayList<String>> returnMessage = (HashMap<PokemonDescription, ArrayList<String>>) is.readObject();

                        //todo provvisorio
                        if(returnMessage.size()>=1)
                            mainBorder.setCenter(DescriptionFounded.display(customer, returnMessage));
                        socket1.close();
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
                    String refValue=YugiohFilter.getReferenceText().getText();
                    //Double levValue=
                    mainBorder.setBottom(new Label("Type: "+comboTypeYu+"    Monster:"+comboMonster+"    ATK:"+atkValue+"    DEF:"+defValue+"   REF: "+refValue));
                    Socket socket2;
                   /* try {
                        socket2 = new Socket("localhost", 8889);
                        System.out.println("Client connected");
                        ObjectOutputStream os = new ObjectOutputStream(socket2.getOutputStream());
                        System.out.println("Ok");
                        os.writeObject(new MessageServer(MessageType.FILTERYUGIOHDESCR, new YuGiOhAll(refValue,levValue,atkValue,defValue,)));
                        ObjectInputStream is = new ObjectInputStream(socket2.getInputStream());
                        ArrayList<HashMap<Customer, Collection>> returnMessage = (ArrayList<HashMap<Customer,Collection>>) is.readObject();
                        if(returnMessage.size()>=1) { //todo provvisorio
                            MainWindow.refreshDynamicContent(SearchDescriptionScene.display(returnMessage, customer));
                        }
                        socket2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    */
                    break;
                //case "NOTHING" todo quando non viene selezionato tipo di carta-->cerco solo stringa

            }
        }

    }

}
