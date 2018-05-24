package TradeCenter;

import TradeCenter.Card.Card;
import TradeCenter.Card.CardCatalog;

import TradeCenter.Exceptions.CardExceptions.CardNotFoundException;
import TradeCenter.Exceptions.TradeExceptions.NoSuchTradeException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UserNotFoundException;
import TradeCenter.Card.Description;
import TradeCenter.Trades.*;
import TradeCenter.Customers.*;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing the trading center.
 *
 * It keeps a list of customers that interacts with each other
 * Customers have their own collection, taken from a database
 */
public class TradeCenter {

    private int contUsers;                              //todo: Migliorare ID utente
    private CardCatalog pokemonCatalog;
    private CardCatalog yugiohCatalog;
    private static HashMap<String, Customer> customers;
    private ArrayList<Trade> activeTrades;
    private ArrayList<Trade> doneTrades;
    private DBProxy proxy;

    /**
     * Create a new trade center, with  the database and no users
     */
    public TradeCenter() {
        this.contUsers = 0;
        this.proxy = new DBProxy();
        this.pokemonCatalog = new CardCatalog();
        this.yugiohCatalog = new CardCatalog();
        proxy.populateCatalog("pokemon_cards", pokemonCatalog);
        proxy.populateCatalog("Yugioh_card", yugiohCatalog);
        this.customers = new HashMap<String, Customer>();
        //populateCustomers();                          //todo decommentare quando il DB sara pronto
        this.activeTrades = new ArrayList<Trade>();
        this.doneTrades = new ArrayList<Trade>();
    }

    /**
     * a method that take the customers from the database
     */
    private void populateCustomers(){
        for(int i = 0;i<proxy.customersSize();i++){
            customers.put(proxy.getCostumer(i).getId(), proxy.getCostumer(i));
        }
    }

    /**
     * Create an account for a new user
     *
     * @param username
     * @param password
     */
    public void addCustomer(String username, String password) throws CheckPasswordConditionsException{
        String id = customerID();
        Customer temporaryCustomer = new Customer(id, username, password);
        customers.put(id, temporaryCustomer);
        proxy.insertCustomer(temporaryCustomer);
    }

    /**
     * Create a unique ID for the user
     *
     * @return the ID
     */
    private String customerID(){
        contUsers++;
        return "USER-" + contUsers;
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
        throw new UserNotFoundException();
    }

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
    public HashMap<Customer, Collection> searchByString(String searchString){
        HashMap<Customer, Collection> searched = new HashMap<>();
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
    void createTrade(Offer offer){
        Trade Trade = new Trade(offer);      //todo mi aspetto che l'offerta si crei dall'interfaccia grafica, o desicedere di istanziarla qua
        activeTrades.add(Trade);
    }
    /**
     * Do the exchange, users collection are update, and so the trade
     *
     * @param trade a card exchange
     */
    private void switchCards(ATrade trade){
        if(activeTrades.contains(trade)) {

            Customer customer1 = trade.getCustomer1();
            Customer customer2 = trade.getCustomer2();

            for (Card card : trade.getOffer1()) {       //take card offered from customer1, add to customer2
                customer2.addCard(card);
                customer1.removeCard(card);
            }
            for (Card card : trade.getOffer2()) {       //take card offered from customer2, add to customer1
                customer1.addCard(card);
                customer2.removeCard(card);
            }

        }else{
            throw new NoSuchTradeException();
        }
    }

    /**
     * A method that verify if the passwords are the same when singin-up
     * @param password1 the password
     * @param password2 check for first password
     * @return if the password are equals
     */
    public boolean verifyPassword(String password1, String password2){
        return password1.equals(password2);
    }

    /**
     * A method that checks if the user's password is correct
     * @param username
     * @param password
     * @return if the login ended in a correct way
     */
    public boolean loggedIn(String username, String password){
        Customer customer;
        try{
            customer = searchCustomer(username);
        }catch(UserNotFoundException e){
            customer = null;
            e.printStackTrace();
        }
        if(customer == null){
            //todo username inesistente fare registrare
            return false;
        }
        if(customer.getUsername() == username && customer.checkPassword(password)){
            return true;
        }else{
            //usere registrato ma password sbagliata //todo richiedere password
            return false;
        }

    }

    /**
     * Check for deals that are over, if so it updates the list of active and done deals
     * Also if a deal it's over, the exchange is done
     */
    public void checkDoneDeals(){
        for(Trade trade : activeTrades){        //todo togliere ciclo
            if(trade.isDoneDeal()){
                switchCards(trade);
                doneTrades.add(trade);
                activeTrades.remove(trade);
            }
        }
    }

    /**
     * A method that shows a log of all the trades that ended
     */
    public void logDoneTrades(){
        for (Trade trade : doneTrades){
            System.out.println(trade);
        }
    }

    /**
     * A method that return alla the customers
     * @return the customers map
     */
    public static HashMap<String, Customer> getCustomers() {
        return customers;
    }
}
