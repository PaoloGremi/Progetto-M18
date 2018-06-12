package TradeCenter.Trades;

import java.io.Serializable;
import java.util.Date;
import TradeCenter.Customers.*;
import TradeCenter.Exceptions.CardExceptions.EmptyCollectionException;
import TradeCenter.Exceptions.TradeExceptions.MyselfTradeException;

public abstract class ATrade implements Serializable {

    /**
     *@param customer1 Customer 1
     *@param customer2 Customer 2
     *@param offer1 Collection offered by Customer 1 to Customer 2
     *@param offer2 Collection requested by Customer 1 from Customer 2
     *@param date Latest update date and time
     */
    private Customer customer1;
    private Customer customer2;
    private Collection offer1;
    private Collection offer2;
    protected Date date;

    /**
     * Constructor method
     * @param customer1 First Customers
     * @param customer2 Second Customers
     * @param offer1 First Customers's collection to be exchanged
     * @param offer2 Second Customers's collection to be exchanged
     */
    protected ATrade(Customer customer1, Customer customer2, Collection offer1, Collection offer2) {
        if(customer1.getId().equals(customer2.getId())){
            throw new MyselfTradeException();
        }
        this.customer1 = customer1;
        this.customer2 = customer2;
        if (offer1.collectionIsEmpty() || offer2.collectionIsEmpty()) throw new EmptyCollectionException();
        else {
            this.offer1 = offer1;
            this.offer2 = offer2;
        }
        this.date = new Date();
    }

    /**
     * Getter for first Customers
     * @return first Customers
     */
    public Customer getCustomer1() {
        return customer1;
    }

    /**
     * Getter for second Customers
     * @return second Customers
     */
    public Customer getCustomer2() {
        return customer2;
    }

    /**
     * Getter for first Customers's offer
     * @return first Customers's offer
     */
    public Collection getOffer1() {
        return offer1;
    }

    /**
     * Getter for second Customers's offer
     * @return second Customers's offer
     */
    public Collection getOffer2() {
        return offer2;
    }

    /**
     * Updater for first customer's collection, second customer's collection and current date
     * @param offer1 Firs customer's collection
     * @param offer2 Second customer's collection
     * @param date Current date
     */
    protected void updateParameters(Collection offer1, Collection offer2, Date date) {
        this.offer1 = offer1;
        this.offer2 = offer2;
        this.date = date;
    }
}
