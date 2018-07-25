package Interface.searchCard;

import ClientServer.MessageServer;
import ClientServer.MessageType;
import ClientServer.ServerIP;
import Interface.searchCard.filterChoice.PokemonAll;
import Interface.searchCard.filterChoice.YuGiOhAll;
import TradeCenter.Card.Description;
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
        String text=SearchCardScene.getSearchText().getText();
        if(vBoxFilterLeft.getChildren().size()<2){
            //todo: no filtri selezionati
        }
        if(vBoxFilterLeft.getChildren().size()==1 || ((ComboBox)vBoxFilterLeft.getChildren().get(0)).getValue().equals("--Nothing--") ){
            Socket socket0;
            try {
                socket0 = new Socket(ServerIP.ip, ServerIP.port);
                System.out.println("Client connected: Searching Description by Name"); //TODO da togliere
                ObjectOutputStream os = new ObjectOutputStream(socket0.getOutputStream());
                System.out.println("Ok");
                os.writeObject(new MessageServer(MessageType.SEARCHDESCRSBYTRING,SearchCardScene.getSearchText().getText(),customer.getUsername()));
                ObjectInputStream is = new ObjectInputStream(socket0.getInputStream());

                HashMap<Description,ArrayList<String>> returnMessage = (HashMap<Description, ArrayList<String>>) is.readObject();

                mainBorder.setCenter(DescriptionFound.display(customer, returnMessage));
                socket0.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        if(vBoxFilterLeft.getChildren().size()==2){

            switch ((String)((ComboBox)vBoxFilterLeft.getChildren().get(0)).getValue()){

                case "Pokèmon":

                    String comboTypePo= (String) PokemonFilter.getComboType().getValue();
                    //check if null(non modificato)--> ""
                    if(comboTypePo==null || comboTypePo.equals("--Nothing--"))
                        comboTypePo="";
                    int hpValue=(int) PokemonFilter.getHpSlider().getValue();
                    int levValue=(int)PokemonFilter.getLevSlider().getValue();
                    int weightValue=(int)PokemonFilter.getWeightSlider().getValue();
                    String len1=PokemonFilter.getTextLen1().getText();
                    String len2=PokemonFilter.getTextLen2().getText();
                    //mainBorder.setBottom(new Label("Type"+comboTypePo+"    len1: "+len1+"    len2: "+len2+"    HP: "+(hpValue)+"    LEV: "+(levValue)+"    WEIGHT: "+(weightValue)));
                    Socket socket1;
                    try {
                        socket1 = new Socket(ServerIP.ip, ServerIP.port);
                        System.out.println("Client connected: Searching Pokemon Description");
                        ObjectOutputStream os = new ObjectOutputStream(socket1.getOutputStream());
                        System.out.println("Ok");
                        os.writeObject(new MessageServer(MessageType.FILTERPOKEMONDESCR, new PokemonAll(text,comboTypePo,hpValue,levValue,weightValue,len1,len2),customer.getUsername()));
                        ObjectInputStream is = new ObjectInputStream(socket1.getInputStream());

                        HashMap<Description,ArrayList<String>> returnMessage = (HashMap<Description, ArrayList<String>>) is.readObject();


                        mainBorder.setCenter(DescriptionFound.display(customer, returnMessage));
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
                    String refValue=YugiohFilter.getReferenceText().getText();
                    int atkValue= (int) YugiohFilter.getAtkSlider().getValue();
                    int defValue= (int) YugiohFilter.getDefSlider().getValue();
                    int levelValue= (int) YugiohFilter.getLevSlider().getValue();
                    if(comboMonster==null || comboMonster.equals("--Nothing--"))
                        comboMonster="";
                    if(comboTypeYu==null || comboTypeYu.equals("--Nothing--"))
                        comboTypeYu="";
                    //mainBorder.setBottom(new Label("Type: "+comboTypeYu+"    Monster:"+comboMonster+"    ATK:"+atkValue+"    DEF:"+defValue+"   REF: "+refValue));
                    Socket socket2;
                    try {
                        socket2 = new Socket(ServerIP.ip, ServerIP.port);
                        System.out.println("Client connected: Searching YuGiOh Description");
                        ObjectOutputStream os = new ObjectOutputStream(socket2.getOutputStream());
                        System.out.println("Ok");
                        os.writeObject(new MessageServer(MessageType.FILTERYUGIOHDESCR, new YuGiOhAll(text,refValue,levelValue,atkValue,defValue,comboMonster,comboTypeYu),customer.getUsername()));
                        ObjectInputStream is = new ObjectInputStream(socket2.getInputStream());

                        HashMap<Description,ArrayList<String>> returnMessage = (HashMap<Description, ArrayList<String>>) is.readObject();

                            mainBorder.setCenter(DescriptionFound.display(customer, returnMessage));
                        socket2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                //case "NOTHING" todo quando non viene selezionato tipo di carta-->cerco solo stringa

            }
        }

    }

}
