package TradeCenter;

import Interface.searchCard.filterChoice.PokemonAll;
import Interface.searchCard.filterChoice.YuGiOhAll;
import TradeCenter.Card.*;

import TradeCenter.Customers.Collection;
import TradeCenter.Exceptions.CardExceptions.EmptyCollectionException;
import TradeCenter.Exceptions.TradeExceptions.AlreadyStartedTradeException;
import TradeCenter.Exceptions.TradeExceptions.MyselfTradeException;
import TradeCenter.Exceptions.TradeExceptions.NoSuchDescriptionFoundedException;
import TradeCenter.Exceptions.TradeExceptions.NoSuchTradeException;
import TradeCenter.Exceptions.UserExceptions.AlreadyLoggedInException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UserNotFoundException;
import TradeCenter.Exceptions.UserExceptions.UsernameAlreadyTakenException;
import TradeCenter.Trades.*;
import TradeCenter.Customers.*;
import DatabaseProxy.DBProxy;

import java.util.*;

/**
 * Class representing the trading center.
 *
 * It keeps a list of customers that interacts with each other
 * Customers have their own collection, taken from a database
 */
public class TradeCenter {

    private static TradeCenter instance;

    private CardCatalog pokemonCatalog;
    private CardCatalog yugiohCatalog;
    private HashMap<String, Customer> customers;
    private HashSet<Trade> activeTrades;
    private HashSet<Trade> doneTrades;
    private ArrayList<String> loggedCustomers = new ArrayList<>();
    private DBProxy proxy;

    /**
     * Create a new trade center, with the database connection that load cards and users with their attributes
     */
    private TradeCenter() {
        this.proxy = DBProxy.getInstance();
        populateCatalogs();
        this.customers = new HashMap<>();
        this.activeTrades = new HashSet<>();
        this.doneTrades = new HashSet<>();
    }

    public static TradeCenter getInstance() {
        if(instance == null) instance = new TradeCenter();
        return instance;
    }

    /**
     * A method that populates the catalogs
     */
    private void populateCatalogs(){
        this.pokemonCatalog = new CardCatalog();
        this.yugiohCatalog = new CardCatalog();
        proxy.populateCatalog(pokemonCatalog, "pokemon_card");
        proxy.populateCatalog(yugiohCatalog, "yugioh_card");
    }

    //sign-up methods
    /**
     * Create an account for a new user
     */
    public synchronized Customer addCustomer(String username, String password) throws CheckPasswordConditionsException{
        Customer temporaryCustomer;
        if(usernameTaken(username)){
            throw new UsernameAlreadyTakenException();
        }else{
            String id = customerID();
            temporaryCustomer = new Customer(id, username, password);
            customers.put(id, temporaryCustomer);
            proxy.addCustomerToDatabase(temporaryCustomer);
        }
        return temporaryCustomer;
    }

    /**
     * A method that check if the username is already in use
     *
     * @param username Candidate username
     * @return a boolean value
     */
    private boolean usernameTaken(String username){
        return proxy.getAllCustomersNames().contains(username);
    }

    /**
     * Create a unique ID for the user
     *
     * @return the ID
     */
    private String customerID(){
        return "USER-" + proxy.getNextCustomerID();
    }

    //session methods
    /**
     * A method that checks if the user's password is correct
     * @param username Username to verify
     * @param password Password to verify
     * @return if the login ended in a correct way
     */
    public synchronized boolean loggedIn(String username, String password){
        Customer customer;
        try{
            customer = searchCustomer(username);
        }catch(UserNotFoundException e){
            customer = null;
            e.printStackTrace();
        }
        if(customer == null){
            return false;
        }
        if(customer.checkPassword(password)){
            customers.put(customer.getId(), customer);
            for(Trade trade : proxy.getTradeByUser(customer.getId())) {
                if (trade.isDoneDeal()) doneTrades.add(trade);
                else activeTrades.add(trade);
            }
            loggedCustomers.add(username);
            return true;
        }
        return false;
    }

    /**
     * A method that verify if the passwords are the same when singin-up
     * @param password1 the password
     * @param password2 check for first password
     * @return if the password are equals
     */
    public synchronized boolean verifyPassword(String password1, String password2){
        return password1.equals(password2);
    }

    /**
     * Verify if the customer is logged in another client
     * @param username Username of the customer
     * @return Boolean if is already logged or not
     */
    public synchronized boolean isLogged(String username){
        if(loggedCustomers.contains(username)) throw new AlreadyLoggedInException();
        return false;
    }

    /**
     * Log out the customer from the system
     * @param username Username of the customer
     */
    public void logOut(String username){
        loggedCustomers.remove(username);
    }

    //add cards to users
    /**
     * Open a pack with 7 cards
     * @param userId Customer that open the pack
     * @param catalog Catalog where take the random cards
     * @return The 7 random cards
     */
    private synchronized ArrayList<Card> randomCards(String userId, CardCatalog catalog){

        ArrayList<Card> cards = new ArrayList<>();
        int nextCard = proxy.getNextCardID();
        Random random = new Random();
        for(int i =0; i<7 ;i++){
            cards.add(new Card(nextCard+i, (Description) catalog.getCatalog().toArray()[random.nextInt(catalog.getCatalog().size())]));
        }
        addCardToCustomer(searchCustomerById(userId).getId(),cards);
        return cards;
    }

    /**
     * Opens a pokemon pack
     * @param customerId Customer that opens the pack
     * @return the 7 cards
     */
    public synchronized ArrayList<Card> fromPokemonCatalog(String customerId){
        ArrayList<Card> cards = randomCards(customerId, pokemonCatalog);
        return cards;
    }

    /**
     *Opens a yu-gi-ho pack
     *@param customerId Customer that opens the pack
     *@return the 7 cards
     */
    public synchronized ArrayList<Card> fromYuGiOhCatalog(String customerId){
        ArrayList<Card> cards = randomCards(customerId, yugiohCatalog);
        return cards;
    }

    /**
     * a method that add a card to collection to a customer
     * @param customerId Id of the customer himself
     * @param cards the cards to add to a collection
     */
    private synchronized void addCardToCustomer(String customerId, ArrayList<Card> cards){
        customers.get(customerId).addCard(cards);
        proxy.updateCustomer(customers.get(customerId));
    }

    /**
     * method that remove a card from a customer's collection
     * @param customerID ID of the customer
     * @param card the to remove from a collection
     */
    private synchronized void removeCardFromCustomer(String customerID, Card card){
        customers.get(customerID).removeCard(card);
        proxy.updateCustomer(customers.get(customerID));
    }

    /**
     * Method that add a card to a customer's wishlist
     * @param cardDescription the card
     * @param customer the customer itself
     */
    public synchronized void addToWishList(Description cardDescription, Customer customer){
        customers.get(customer.getId()).addCardToWishList(cardDescription);
        proxy.updateCustomer(customers.get(customer.getId()));
    }

    /**
     * A method that removes a card from a Customer Wishlist
     * @param cardDescription the card to remove
     * @param id Id of the customer that wants to remove the card
     */
    public synchronized void removeFromWishList(Description cardDescription, String id) {
        customers.get(id).removeFromWishList(cardDescription);
        proxy.updateCustomer(customers.get(id));
    }

    //search user
    /**
     * User can find another user by searching his name
     * @return
     */
    public synchronized Customer searchCustomer(String username){
        for(String key : customers.keySet()){
            if((customers.get(key)).getUsername().equals(username)){
                return customers.get(key);
            }
        }
        //user not in map
        Customer customer = proxy.retrieveSingleCustomer(username);
        if(customer != null) {
            customers.put(customer.getId(),customer);
            return customer;
        }
        //user dont exist
        throw new UserNotFoundException();
    }

    /**
     * A method that search all the customers that have a username similar to a string searched
     * @param username the string for the research
     * @param myUsername a string to exclude myself from the research
     * @return the list of customers that corresponds to the search
     */
    public synchronized ArrayList<String> searchUsers(String username, String myUsername){
        ArrayList<String> usersFound = new ArrayList<>();
        ArrayList<String> allCustomersNames = proxy.getAllCustomersNames();
        for(String customer: allCustomersNames){
            if(customer.toLowerCase().contains(username.toLowerCase()) && !customer.equals(myUsername)){
                usersFound.add(customer);
            }
        }
        return usersFound;
    }

    /**
     * Search a customer in the system by his id
     * @param id Id to find
     * @return Customer found
     */
    public synchronized Customer searchCustomerById(String id){

        if(customers.keySet().contains(id)) return customers.get(id);

        Customer customer = proxy.retrieveSingleCustomerByID(id);
        customers.put(customer.getId(),customer);
        return customer;

    }

    /**
     * Search a username in the system by his id
     * @param id Id to find
     * @return Username found
     */
    public synchronized String searchUsernameById(String id){

        if(customers.keySet().contains(id)) return customers.get(id).getUsername();

        Customer customer = proxy.retrieveSingleCustomerByID(id);
        customers.put(customer.getId(),customer);
        return customer.getUsername();

    }

    //search cards, and owners
    /**
     *  A method that checks if any of the user has a given card
     * @param description the card that has to be searched
     * @return the list of the users that have that card in their collection
     */
    public synchronized ArrayList<String> searchUserByDescription(Description description, String customerUsername){
        ArrayList<String> customersFound = proxy.getCustomersByDescription(description);
        customersFound.remove(customerUsername);
        return customersFound;
    }

    /**
     * Hashmap with k=Description, V=ArrayList<Customer> thats have the description
     * @param hashDescr: Descriptions
     * @return HashMap<Description,ArrayList<Customer>> a map with for every description, the list of custumers that owns that
     */
    public synchronized HashMap<Description,ArrayList<String>> getCustomersFromDescriptions(HashSet<Description> hashDescr, String username){
        HashMap<Description,ArrayList<String>> map = new HashMap<>();
        for (Description descr : hashDescr) {
            map.put(descr,searchUserByDescription(descr,username));
        }
        return map;
    }

    //search cards with filters
    /**
     *Search cards with a string
     *
     * @param s the string for search
     * @return the set of cards
     */
    public synchronized HashSet<Description> filterByString(String s) throws NoSuchDescriptionFoundedException  {
        return proxy.getFoundDescrByString(s);
    }

    /**
     * Return Description foundend in DB filtered
     *
     * @param pokemonAll: Pokemon All
     * @return PokemonDescription matched
     */
    public synchronized HashSet<Description> filterPokemonDescr(PokemonAll pokemonAll) throws NoSuchDescriptionFoundedException {
        return proxy.getFoundDescrPokemon(pokemonAll);
    }

    /**
     * Return YuGiOhDescription foundend in DB filtered
     *
     * @param yuGiOhAll: YuGiOh Description
     * @return YuGiOh Description matched
     */
    public synchronized HashSet<Description> filterYugiohDescr(YuGiOhAll yuGiOhAll) throws NoSuchDescriptionFoundedException {
        return proxy.getFoundDescrYugioh(yuGiOhAll);
    }

    //trade methods
    /**
     * Method that create a trade between 2 customers
     * @param customer1 id of the customer who make the trade
     * @param customer2 id of the custumer who recive the trade
     * @param offer1 the offer of the first customer
     * @param offer2 the offer of the second customer
     */
    public synchronized void createTrade(String customer1, String customer2, Collection offer1, Collection offer2){
        if(notAlreadyTradingWith(customer1, customer2)){
            try {
                Trade Trade = new Trade(new Offer(customer1, customer2, offer1, offer2), proxy.getNextTradeID());
                activeTrades.add(Trade);
                proxy.insertTrade(Trade);
            }catch (MyselfTradeException | EmptyCollectionException e){
                System.err.println(e.getMessage());
            }
        }else{
            throw new AlreadyStartedTradeException(searchUsernameById(customer2));
        }
    }

    /**
     * A method that checks if two users are already trading
     *
     * @param myCustomer customer1
     * @param otherCustomer customer2
     * @return if the user are already trading
     */
    public synchronized boolean notAlreadyTradingWith(String myCustomer, String otherCustomer){
        boolean flag = true;
        if(myCustomer.equals(otherCustomer)) return false; //cannot trade with yourself
        for(Trade trade: activeTrades){
            if(trade.betweenUsers(myCustomer) && trade.betweenUsers(otherCustomer)){
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Retrieve a trade if exists already
     * @param myCustomer Customer logged in the client
     * @param otherCustomer Other customer involved
     * @return Trade found
     */
    public synchronized Trade takeStartedTrade(String myCustomer, String otherCustomer){
        Trade searchTrade = null;
        for(Trade trade : activeTrades){
            if(trade.betweenUsers(myCustomer) && trade.betweenUsers(otherCustomer)){
                searchTrade = trade;
            }
        }
        return searchTrade;
    }

    /**
     * Check if the trade exists in the system
     * @param trade Trade to found
     * @return boolean if exists
     */
    private boolean isActive(Trade trade){
        for (Trade trade1 : activeTrades){
            if(trade1.betweenUsers(trade.getCustomer1()) && trade1.betweenUsers(trade.getCustomer2())){
                return true;
            }
        }
        return false;
    }

    /**
     * Method that give the possibility to update the previus offer
     * @param offer the offer of a customer
     * @param flag
     * @return boolean if the trade is updated
     */
    //todo cosa è il flag
    public synchronized boolean updateTrade(Offer offer, boolean flag){
        Trade trade = takeStartedTrade(offer.getCustomer1(), offer.getCustomer2());
        trade.update(offer, flag);
        proxy.updateTrade(trade);
        return true;
    }

    /**
     * Remove the trade from the active trades
     * @param myCustomer Customer logged
     * @param otherCustomer The other customer involved
     */
    public synchronized void removeTrade(String myCustomer, String otherCustomer){
        Trade trade = takeStartedTrade(myCustomer,otherCustomer);
        activeTrades.remove(trade);
        proxy.removeTrade(trade.getId());
    }

    /**
     * Do the exchange, users collection are update, and so the trade
     *
     * @param trade a card exchange
     */
    private synchronized void switchCards(Trade trade){
        if(isActive(trade)) {
            for (Card card : trade.getOffer1()) {       //take card offered from customer1, add to customer2
                customers.get(trade.getCustomer2()).addCard(card);
            }
            for (Card card : trade.getOffer2()) {       //take card offered from customer2, add to customer1
                customers.get(trade.getCustomer1()).addCard(card);
            }
            for(Card card : trade.getOffer1()){
                customers.get(trade.getCustomer1()).removeCard(card);
            }
            for(Card card : trade.getOffer2()){
                customers.get(trade.getCustomer2()).removeCard(card);
            }
            proxy.updateCustomer(customers.get(trade.getCustomer1()));
            proxy.updateCustomer(customers.get(trade.getCustomer2()));
        }else{
            throw new NoSuchTradeException();
        }
    }

    /**
     * End an active trade
     * @param trade Trade to end
     * @param result The result of the trade
     */
    public synchronized void endTrade(Trade trade, boolean result){
        if(result){
            switchCards(trade);
        }
        for(Trade activeTrade : activeTrades){
            if(activeTrade.betweenUsers(trade.getCustomer1()) && activeTrade.betweenUsers(trade.getCustomer2())){
                activeTrades.remove(activeTrade);
                trade.doneDeal(result);
                doneTrades.add(trade);
                proxy.updateTrade(trade);
            }
        }//must be this order or trade != activetrade
    }

    /**
     * A method that shows the trades still active of a customer
     *
     * @param customer the customer that wants to see his trades
     * @return the list of the customer's trades
     */
    private ArrayList<Trade> showUserActiveTrades(String customer){
        ArrayList<Trade> activeTradeList = new ArrayList<>();
        for(Trade trade : activeTrades){
            if(trade.betweenUsers(customer)){
                activeTradeList.add(trade);
            }
        }


        return activeTradeList;
    }

    /**
     * A method that shows the finished trades of a customer
     *
     * @param customer the customer that wants to see his trades
     * @return the list of the customer's trades
     */
    private ArrayList<Trade> showUserDoneTrades(String customer){
        ArrayList<Trade> doneTradesList = new ArrayList<>();
        for(Trade trade : doneTrades){
            if(trade.betweenUsers(customer)){
                doneTradesList.add(trade);
            }
        }
        return doneTradesList;
    }

    /**
     * A method that shows all the trades of a user
     *
     * @param customer the user
     * @return the list of trades of the user, first the active ones
     */
    public ArrayList<Trade> showUserTrades(String customer){
        ArrayList<Trade> tradesList = new ArrayList<>();
        tradesList.addAll(showUserActiveTrades(customer));
        tradesList.addAll(showUserDoneTrades(customer));
        return tradesList;
    }
}