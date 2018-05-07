package TradeCenter;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Customers.CardNotFoundException;
import TradeCenter.Exceptions.CardExceptions.NullDescriptionException;
import TradeCenter.Exceptions.TradeExceptions.NoSuchTradeException;
import TradeCenter.Exceptions.UserExceptions.UserNotFoundException;
import TradeCenter.Trades.ATrade;
import TradeCenter.Trades.Offer;
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
        //todo mettere criterio per password es: almeno una maiuscola e un numero
        //todo mettere poi la relativa eccezione
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
        try{
            if(customers.remove(id) == null){                                 //capire come passare id
                throw new UserNotFoundException();          //todo: non bellissimo, vedere come migliorarlo
            }
        }catch (UserNotFoundException e){
            e.printStackTrace();
        }

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
        //user not found
        throw new UserNotFoundException();                 //todo Serve mettere il throws nwll'header??
    }

    /**
     * If a card doesn't exist in the database, the user can add it
     *
     * @param description the card
     */
    public void addDescription(Description description){
        try{
            catalog.addDescription(description);            //todo secondo me il catalog non va messo qua come attributo(diventano 2 istanze diverse), RIVEDERE
        }catch (NullDescriptionException e){
            e.printStackTrace();                               //todo meglio cosi o con il throws nell'header del metodo??
        }
    }

    //todo: puo essere utile mettere il metodo remove description ??

    /**
     * Method that search if the card is in the database or not
     *
     * @param description the card that we need to check
     * @return boolean True if the card is in the database
     */
    public boolean searchCard(Description description){
        //fino a che il metodo non c'è deve ritornare sempre true
        return true;                //todo mettere il metodo che cerca nel DB
    }

    /**
     * Users can search a card, they see all users that match the search
     *
     * @param searchString name or description of a card
     * @return a list of customers with their own collections
     */
    public HashMap<Customer, Card[]> searchByString(String searchString){       //todo ritorna card[] o meglio collection??
        HashMap<Customer, Card[]> searched = new HashMap<>();
        for(String key : customers.keySet()){
            searched.put(customers.get(key), customers.get(key).searchByString(searchString));
        }
        if(searched.size() == 0){
            //nothing found
            throw new CardNotFoundException();          //todo eccezione da risolvere, se nessuno ha la carta il software deve continuare
        }
        return searched;
    }

    /**
     * This method starts a new trade between two customers
     * @param offer the first offer
     */
    void createTrade(Offer offer){         //todo magari vedere per EXCEP se esistono i customer
        Trade Trade = new Trade(offer);      //todo mi aspetto che l'offerta si crei dall'interfaccia grafica, o desicedere di istanziarla qua
        activeTrades.add(Trade);        //aspettare che robi rifaccia i trade
    }
    /**
     * Do the exchange, users collection are update, and so the trade
     *
     * @param trade a card exchange
     */
    void switchCards(ATrade trade){
        if(activeTrades.contains(trade)){
            customers.get()
        }else{
            throw new NoSuchTradeException();
        }
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
