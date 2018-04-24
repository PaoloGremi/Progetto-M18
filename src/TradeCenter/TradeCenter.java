package TradeCenter;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Trades.Trade;
import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing the trading center.
 *
 * It keeps a list of customers that interacts with each other
 * Customers have their own collection, taken from a database
 */
public class TradeCenter {

    private int contUsers;                              //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
    private CardCatalog catalog;
    private HashMap<String, Customer> customers;
    private ArrayList<Trade> activeTrades;
    private ArrayList<Trade> doneTrades;

    /**
     * Create a new trade center, with  the database and no users
     */
    public TradeCenter() {
        this.contUsers = 0;                             //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
        this.catalog = new CardCatalog();                  //todo: fare mettere nell'istanziazione del catalogo la creazione del database
        this.customers = new HashMap<String, Customer>();
        this.activeTrades = new ArrayList<Trade>();
        this.doneTrades = new ArrayList<Trade>();
    }

    /**
     * Create an account for a new user
     *
     * @param username
     * @param password
     */
    public void addCustomer(String username, String password) {
        String id = customerID();
        customers.put(id, new Customer(id,username,password));
    }

    /**
     * Create a unique ID for the user
     *
     * @return the ID
     */
    private String customerID(){
        contUsers++;                                    //todo: CAMBIARE QUANDO MIGLIORO ID UTENTE
        return "USER-" + contUsers;
    }

    /**
     * Allow the user to delete his account
     *
     * @param id user identifier
     */
    public void removeCustomer(int id) {
        customers.remove(id);                           //capire come passare id
    }

    /**
     * User can find another user by searching his name
     *
     * @param username
     * @return
     */
    public Customer searchCustomer(String username){        //todo: potrebbe ritornare solo l'ID--> do meno info a chi non le deve avere
        for(String key : customers.keySet()){
            if((customers.get(key)).getUsername().equals(username)){
                return customers.get(key);
            }
        }
        return null;
    }

    /**
     * If a card doesn't exist in the database, the user can add it
     *
     * @param description the card
     */
    public void addDescription(Description description){
        catalog.addDescription(description);            //todo secondo me il catalog non va messo qua come attributo(diventano 2 istanze diverse), RIVEDERE
    }

    //todo: puo essere utile mettere il metodo remove description ??

    /**
     * Users can search a card, they see all users that match the search
     *
     * @param searchString name or description of a card
     * @return a list of customers with their own collections
     */
    public HashMap<Customer, Card[]> searchByString(String searchString){       //todo ritorna card[] o meglio collection??
        HashMap<Customer, Card[]> tmp = new HashMap<>();
        for(String key : customers.keySet()){
            tmp.put(customers.get(key), customers.get(key).searchByString(searchString));
        }
        return tmp;
    }

    /**
     * Do the exchange, users collection are update, and so the trade
     *
     * @param trade a card exchange
     */
    void switchCards(Trade trade){
            //ricontrollare trade prima di fare questo metodo e il successivo
        //devo aspettare che venga definito lo scambio delle carte
    }

    /**
     * Check for deals that are over, if so it updates the list of active and done deals
     * Also if a deal it's over, the exchange is done
     */
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
