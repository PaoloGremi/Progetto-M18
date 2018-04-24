package TradeCentre;

import TradeCentre.Trades.Trade;
import TradeCentre.card.Card;
import TradeCentre.card.Description;
import TradeCentre.customer.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeCenter {

    private int contUsers;                              //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
    //private DescriptionCatalog catalog;               // manca ancora la classe
    private HashMap<String, Customer> customers;
    private ArrayList<Trade> activeTrades;
    private ArrayList<Trade> doneTrades;

    public TradeCenter() {                               //puo servire passare il catalogo??
        this.contUsers = 0;                             //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
        //this.catalog = catalog;
        this.customers = new HashMap<String, Customer>();
        this.activeTrades = new ArrayList<Trade>();
        this.doneTrades = new ArrayList<Trade>();
    }

    public void addCustomer(String username, String password) {
        String id = customerID();
        customers.put(id, new Customer(id,username,password));
    }

    private String customerID(){
        contUsers++;                                    //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
        return "USER-" + contUsers;
    }

    public void removeCustomer(int id) {
        customers.remove(id);                           //capire come passare id
    }

    public Customer searchCustomer(String username){
        for(String key : customers.keySet()){
            if((customers.get(key)).getUsername().equals(username)){
                return customers.get(key);
            }
        }
        return null;
    }

    public void addDescription(Description description){
        //vuoto fino a che non viene creata la classe catalogo
    }

    public HashMap<Customer, Card[]> searchByString(String searchString){
        HashMap<Customer, Card[]> tmp = new HashMap<>();
        for(String key : customers.keySet()){
            tmp.put(customers.get(key), customers.get(key).searchByString(searchString));
        }
        return tmp;
    }
/*FINIRE
    boolean switchCards(Trade trade){
            //ricontrollare trade prima di fare questo metodo e il successivo
    }

*/
    public void checkDoneDeals(){
        for(Trade trade : activeTrades){
            if(trade.isDoneDeal()){
                doneTrades.add(trade);
                activeTrades.remove(trade);
            }
        }
    }


}
