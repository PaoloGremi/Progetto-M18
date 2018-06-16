package ClientServer;

import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.TradeExceptions.AlreadyStartedTradeException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UsernameAlreadyTakenException;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Offer;
import TradeCenter.Trades.Trade;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerProxy {

    private TradeCenter tradeCenter;

    public ServerProxy(TradeCenter tradeCenter) {
        this.tradeCenter = tradeCenter;
    }

    public boolean verifyPassword(MessageServer messageServer){
        return tradeCenter.verifyPassword(messageServer.getString1(), messageServer.getString2());
    }

    public boolean loggedIn(MessageServer messageServer){
        return tradeCenter.loggedIn(messageServer.getString1(), messageServer.getString2());
    }

    public void addCustomer(MessageServer messageServer) throws CheckPasswordConditionsException,UsernameAlreadyTakenException {
        tradeCenter.addCustomer(messageServer.getString1(), messageServer.getString2());
    }

    public ArrayList<Customer> searchUsers(MessageServer messageServer){
        return tradeCenter.searchUsers(messageServer.getString1(), messageServer.getCustomer1().getUsername());
    }

    public Customer searchCustomer(MessageServer messageServer){
        return tradeCenter.searchCustomer(messageServer.getString1());
    }

    public void removeFromWishList(MessageServer messageServer){
        tradeCenter.removeFromWishList(messageServer.getDescription(), messageServer.getCustomer1());
    }

    public boolean possibleTrade(MessageServer messageServer){
        return tradeCenter.notAlreadyTradingWith(messageServer.getCustomer1(), messageServer.getCustomer2());
    }

    public void createTrade(MessageServer messageServer) throws AlreadyStartedTradeException {
        tradeCenter.createTrade(messageServer.getCustomer1(), messageServer.getCustomer2(), messageServer.getOffer1(), messageServer.getOffer2());
    }

    public boolean updateOffer(MessageServer messageServer){
        return tradeCenter.updateTrade(new Offer(messageServer.getCustomer1(), messageServer.getCustomer2(), messageServer.getOffer1(), messageServer.getOffer2()));
    }

    public Trade searchTrade(MessageServer messageServer){
        return tradeCenter.takeStartedTrade(messageServer.getCustomer1(), messageServer.getCustomer2());
    }

    public void endTrades(MessageServer messageServer){
        tradeCenter.endTrade((Trade) messageServer.getTrade(), messageServer.isFlag());
    }

    public ArrayList<Trade> searchOffer(MessageServer messageServer){
        return tradeCenter.showUserTrades(messageServer.getCustomer1());
    }

    public ArrayList<HashMap<Customer, Collection>> searchDescription(MessageServer messageServer){
        return tradeCenter.searchByDescription(messageServer.getDescription());
    }
}
