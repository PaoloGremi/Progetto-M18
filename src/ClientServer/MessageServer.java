package ClientServer;

import Interface.searchCard.filterChoice.PokemonAll;
import Interface.searchCard.filterChoice.YuGiOhAll;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.ATrade;
import TradeCenter.Trades.Offer;
import TradeCenter.Trades.Trade;

import java.io.Serializable;

public class MessageServer implements Serializable {

    private MessageType message;
    private String string1;
    private String string2;
    private Offer offer;
    private ATrade trade;
    private Customer customer1;
    private Customer customer2;
    private Description description;
    private Collection offer1;
    private Collection offer2;
    private Trade trade1;
    private boolean flag;
    private PokemonAll pokemonAll;
    private YuGiOhAll yuGiOhAll;
    private  Description descriptionToAdd;
    private String customerFrom;

    private static final long serialVersionUID = 4415426162143895504L;

    public MessageServer(MessageType message, String string1, String string2) {
        this.message = message;
        this.string1 = string1;
        this.string2 = string2;
    }

    public MessageServer(MessageType message, String string1, Customer customer) {
        this.message = message;
        this.string1 = string1;
        this.customer1 = customer;
    }

    public MessageServer(MessageType message, Customer customer) {
        this.message = message;
        this.customer1 = customer;
    }

    public MessageServer(MessageType message, Customer customer1, Customer customer2) {
        this.message = message;
        this.customer1 = customer1;
        this.customer2 = customer2;
    }

    public MessageServer(MessageType message, String username) {
        this.message = message;
        this.string1 = username;
    }


    public MessageServer(MessageType message, Offer offer) {
        this.message = message;
        this.offer = offer;
    }

    public MessageServer(MessageType message, ATrade trade) {
        this.message = message;
        this.trade = trade;
    }

    public MessageServer(MessageType message, Description description) {
        this.message = message;
        this.description = description;
    }

    public MessageServer(MessageType message,  String username, Description description) {
        this.message = message;
        this.string1 = username;
        this.description = description;
    }

    public MessageServer(MessageType message, Customer customer1, Customer customer2, Collection offer1, Collection offer2) {
        this.message = message;
        this.customer1 = customer1;
        this.customer2 = customer2;
        this.offer1 = offer1;
        this.offer2 = offer2;
    }

    public MessageServer(MessageType message, PokemonAll pokemonAll,String customerFrom) {
        this.message=message;
        this.pokemonAll=pokemonAll;
        this.customerFrom=customerFrom;
    }
    public MessageServer(MessageType message, YuGiOhAll yuGiOhAll,String customerFrom){
        this.message=message;
        this.yuGiOhAll=yuGiOhAll;
        this.customerFrom=customerFrom;
    }

    public MessageServer(MessageType message, Trade trade1) {
        this.message = message;
        this.trade1 = trade1;
    }

    public MessageServer(MessageType message, ATrade trade, boolean flag) {
        this.message = message;
        this.trade = trade;
        this.flag = flag;
    }

    public MessageServer(MessageType message, Customer customer1, Description description) {
        this.message = message;
        this.customer1 = customer1;
        this.description = description;
    }

    public MessageServer(MessageType message, String string1, String string2, Collection offer1, Collection offer2) {
        this.message = message;
        this.string1 = string1;
        this.string2 = string2;
        this.offer1 = offer1;
        this.offer2 = offer2;
    }

    public MessageServer(MessageType message, String string1, String string2, Collection offer1, Collection offer2, boolean flag) {
        this.message = message;
        this.string1 = string1;
        this.string2 = string2;
        this.offer1 = offer1;
        this.offer2 = offer2;
        this.flag = flag;
    }
    public MessageServer(MessageType message, Description descriptionToAdd, String customerFrom){
        this.message=message;
        this.descriptionToAdd=descriptionToAdd;
        this.customerFrom=customerFrom;
    }

    public boolean isFlag() {
        return flag;
    }

    public PokemonAll getPokemonAll() {
        return pokemonAll;
    }

    public Description getDescription() {
        return description;
    }

    public Customer getCustomer1() {
        return customer1;
    }

    public Customer getCustomer2() {
        return customer2;
    }

    public Collection getOffer1() {
        return offer1;
    }

    public Collection getOffer2() {
        return offer2;
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

    public ATrade getTrade() {
        return trade;
    }

    public Trade getTrade1() {
        return trade1;
    }

    public YuGiOhAll getYuGiOhAll() {
        return yuGiOhAll;
    }

    public Description getDescriptionToAdd() {
        return descriptionToAdd;
    }

    public String getCustomerFrom() {
        return customerFrom;
    }
}
