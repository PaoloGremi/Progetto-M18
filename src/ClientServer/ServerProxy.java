package ClientServer;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.TradeExceptions.AlreadyStartedTradeException;
import TradeCenter.Exceptions.UserExceptions.AlreadyLoggedInException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UsernameAlreadyTakenException;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Offer;
import TradeCenter.Trades.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ServerProxy {

    private TradeCenter tradeCenter;

    /**
     * Constructor for the server proxy
     * @param tradeCenter The static tradecenter from the server
     */
    public ServerProxy(TradeCenter tradeCenter) {
        this.tradeCenter = tradeCenter;
    }

    /**
     * Send the two passwords form the client to the tradecenter to verify if they match
     * @param messageServer Message with inside the two strings
     * @return
     */
    public boolean verifyPassword(MessageServer messageServer){
        return tradeCenter.verifyPassword(messageServer.getString1(), messageServer.getString2());
    }

    /**
     * Send username e password form the client to the server to verify if they are correct
     * @param messageServer Message with inside the two strings
     * @return
     */
    public boolean loggedIn(MessageServer messageServer){
        return tradeCenter.loggedIn(messageServer.getString1(), messageServer.getString2());
    }

    /**
     * Send the message to the tradecenter to add a new customer just created
     * @param messageServer Message with inside the username and the password of the new Customer
     * @return The new customer
     * @throws CheckPasswordConditionsException
     * @throws UsernameAlreadyTakenException
     */
    public Customer addCustomer(MessageServer messageServer) throws CheckPasswordConditionsException,UsernameAlreadyTakenException {
       return tradeCenter.addCustomer(messageServer.getString1(), messageServer.getString2());
    }

    /**
     * Search users in the tradecenter that contain in the username the string received from the client
     * @param messageServer The string to search and the username of the customer that send the message
     * @return The list of the customers that contain that string in the username
     */
    public ArrayList<String> searchUsers(MessageServer messageServer){
        return tradeCenter.searchUsers(messageServer.getString1(), messageServer.getString2());
    }

    /**
     * Search a customer in the tradecenter by its username
     * @param messageServer Message from the client with username of the customer
     * @return The customer found
     */
    public Customer searchCustomer(MessageServer messageServer){
        return tradeCenter.searchCustomer(messageServer.getString1());
    }

    /**
     * Remove a description from the wishlist of the customer
     * @param messageServer Message with the description to remove and id of the customer
     */
    public void removeFromWishList(MessageServer messageServer){
        tradeCenter.removeFromWishList(messageServer.getDescription(), messageServer.getString1());
    }

    /**
     * Method that verify if a trade between the users is already started
     * @param messageServer Message with the id of the customers
     * @return boolean if the trade i already startd or not
     */
    public boolean possibleTrade(MessageServer messageServer){
        return tradeCenter.notAlreadyTradingWith(messageServer.getString1(), messageServer.getString2());
    }

    /**
     * Method that create a new trade
     * @param messageServer Message with the ids of the customers and the two offers
     * @throws AlreadyStartedTradeException
     */
    public void createTrade(MessageServer messageServer) throws AlreadyStartedTradeException {
        tradeCenter.createTrade(messageServer.getString1(), messageServer.getString2(), messageServer.getOffer1(), messageServer.getOffer2());
    }

    /**
     * Method to update the offer if one of the customers change it
     * @param messageServer Message withe the new parameters of the offer
     * @return boolean of the result
     */
    public boolean updateOffer(MessageServer messageServer){
        return tradeCenter.updateTrade(new Offer(messageServer.getString1(), messageServer.getString2(), messageServer.getOffer1(), messageServer.getOffer2()),messageServer.isFlag());
    }

    /**
     * Method that take a started trade
     * @param messageServer Message with the two customers of the trade
     * @return The Trade found
     */
    public Trade searchTrade(MessageServer messageServer){
        return tradeCenter.takeStartedTrade(messageServer.getString1(), messageServer.getString2());
    }

    /**
     * Close the trade
     * @param messageServer Message with the trade and the result
     */
    public void endTrades(MessageServer messageServer){
        tradeCenter.endTrade((Trade) messageServer.getTrade(), messageServer.isFlag());
    }

    /**
     * Search all the trades of a simgle customer, closed or not
     * @param messageServer Message with the id of the customer
     * @return ArrayList with all the trades
     */
    public ArrayList<Trade> searchOffer(MessageServer messageServer){
        return tradeCenter.showUserTrades(messageServer.getString1());
    }

    /**
     * Search all the customers that have a card with this description in their collection
     * @param messageServer Message with the description of the card
     * @return ArrayList with the usernames of the Customers
     */
    public  ArrayList<String> searchDescription(MessageServer messageServer){
        return tradeCenter.searchUserByDescription(messageServer.getDescriptionToAdd(),messageServer.getCustomerFrom());
    }

    /**
     * Opens a Pokemon pack
     * @param messageServer Id the of the customer that opens the pack
     * @return ArrayList of the card found
     */
    public ArrayList<Card> addPokemonRandom(MessageServer messageServer){
        return tradeCenter.fromPokemonCatalog(messageServer.getString1());
    }

    /**
     * Opens a Yu-Gi-Ho pack
     * @param messageServer Id the of the customer that opens the pack
     * @return ArrayList of the card found
     */
    public ArrayList<Card> addYuGiOhRandom(MessageServer messageServer){
        return tradeCenter.fromYuGiOhCatalog(messageServer.getString1());
    }

    /**
     * Search a customer by its id
     * @param messageServer Message with the id of the customer
     * @return Customer found
     */
    public Customer searchCustomerByID(MessageServer messageServer){
        return tradeCenter.searchCustomerById(messageServer.getString1());
    }
    /**
     * Search the username of a customer by its id
     * @param messageServer Message with the id of the customer
     * @return Username found
     */
    public String searchUsernameById(MessageServer messageServer){
        return tradeCenter.searchUsernameById(messageServer.getString1());
    }

    /**
     * Remove the trade form the tradecenter
     * @param messageServer Message with the two customers of the trade to remove
     */
    public void removeTrade(MessageServer messageServer){
        tradeCenter.removeTrade(messageServer.getString1(),messageServer.getString2());
    }

    //todo add javadocs
    public HashMap<Description, ArrayList<String>> filterPokemonDescr(MessageServer messageServer){
        String customerFrom=messageServer.getCustomerFrom();
        HashSet<Description> descriptions=tradeCenter.filterPokemonDescr(messageServer.getPokemonAll());
        return tradeCenter.getCustomersFromDescriptions( descriptions,customerFrom);
    }

    public HashMap<Description,ArrayList<String>> filterYugiohDescr(MessageServer messageServer){
        String customerFrom=messageServer.getCustomerFrom();
        HashSet<Description> descriptions=tradeCenter.filterYugiohDescr(messageServer.getYuGiOhAll());
        return tradeCenter.getCustomersFromDescriptions(descriptions,customerFrom);
    }

    public void addDescrToWhishlist(MessageServer messageServer){
        Customer customer=tradeCenter.searchCustomer(messageServer.getCustomerFrom());
        tradeCenter.addToWishList(messageServer.getDescriptionToAdd(),customer);
    }

    public HashMap<Description,ArrayList<String>> searchDescrByString(MessageServer messageServer){
        String stringToSearch=messageServer.getString1();
        String username=messageServer.getString2();
         HashSet<Description> descriptions=tradeCenter.filterByString(stringToSearch);
         return tradeCenter.getCustomersFromDescriptions(descriptions,username);
    }

    public boolean isLoggedIn(MessageServer messageServer) throws AlreadyLoggedInException {
       return tradeCenter.isLogged(messageServer.getString1());
    }

    public void logOut(MessageServer messageServer){
        tradeCenter.logOut(messageServer.getString1());
    }
}
