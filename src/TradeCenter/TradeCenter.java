package TradeCenter;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Trades.Trade;
import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeCenter {

    private int contUsers;                              //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
    private CardCatalog catalog;
    private HashMap<String, Customer> customers;
    private ArrayList<Trade> activeTrades;
    private ArrayList<Trade> doneTrades;

    public TradeCenter() {
        this.contUsers = 0;                             //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
        this.catalog = new CardCatalog();                  //todo: fare mettere nell'istanziazione del catalogo la creazione del database
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
        catalog.addDescription(description);            //todo secondo me il catalog non va messo qua come attributo(diventano 2 istanze diverse), RIVEDERE
    }

    //todo: puo essere utile mettere il metodo remove description ??
    public HashMap<Customer, Card[]> searchByString(String searchString){
        HashMap<Customer, Card[]> tmp = new HashMap<>();
        for(String key : customers.keySet()){
            tmp.put(customers.get(key), customers.get(key).searchByString(searchString));
        }
        return tmp;
    }

    void switchCards(Trade trade){
            //ricontrollare trade prima di fare questo metodo e il successivo
        //devo aspettare che venga definito lo scambio delle carte
    }


    public void checkDoneDeals(){
        for(Trade trade : activeTrades){
            if(trade.isDoneDeal()){
                switchCards(trade);
                doneTrades.add(trade);
                activeTrades.remove(trade);
            }
        }
    }


}
