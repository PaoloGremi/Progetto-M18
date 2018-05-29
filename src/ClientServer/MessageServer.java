package ClientServer;

import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Offer;
import TradeCenter.Trades.Trade;

import java.io.Serializable;

public class MessageServer implements Serializable {

    private MessageType message;
    private String string1;
    private String string2;
    private Offer offer;
    private Trade trade;
    private Customer customer;
    private Description description;

    public MessageServer(MessageType message, String string1, String string2) {
        this.message = message;
        this.string1 = string1;
        this.string2 = string2;
    }

    public MessageServer(MessageType message, Customer customer) {
        this.message = message;
        this.customer = customer;
    }

    public MessageServer(MessageType message, String username) {
        this.message = message;
        this.string1 = username;
    }

    public MessageServer(MessageType message, Offer offer) {
        this.message = message;
        this.offer = offer;
    }

    public MessageServer(MessageType message, Trade trade) {
        this.message = message;
        this.trade = trade;
    }

    public MessageServer(MessageType message, Description description) {
        this.message = message;
        this.description = description;
    }

    public Description getDescription() {
        return description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MessageType getMessage() {
        return message;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    public Offer getOffer() {
        return offer;
    }

    public Trade getTrade() {
        return trade;
    }
}
