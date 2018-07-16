package TradeCenter.Trades;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import TradeCenter.Customers.*;
import TradeCenter.Exceptions.CardExceptions.EmptyCollectionException;
import TradeCenter.Exceptions.TradeExceptions.MyselfTradeException;

/**
 * An abstract class that controls the trade between two customers.
 */

public abstract class ATrade implements Serializable {

    /**
     *@param customer1 Customer 1
     *@param customer2 Customer 2
     *@param offer1 Collection offered by Customer 1 to Customer 2
     *@param offer2 Collection requested by Customer 1 from Customer 2
     *@param date Latest update date and time
     */
    private String customer1;
    private String customer2;
    private Collection offer1;
    private Collection offer2;
    private Date date;

    /**
     * Constructor method
     * @param customer1 First Customers
     * @param customer2 Second Customers
     * @param offer1 First Customers's collection to be exchanged
     * @param offer2 Second Customers's collection to be exchanged
     */
    protected ATrade(String customer1, String customer2, Collection offer1, Collection offer2) {
        if(customer1.equals(customer2)){
            throw new MyselfTradeException();
        }
        this.customer1 = customer1;
        this.customer2 = customer2;
        if (offer1.collectionIsEmpty() || offer2.collectionIsEmpty()) throw new EmptyCollectionException();
        else {
            this.offer1 = offer1;
            this.offer2 = offer2;
        }
        this.date = new Date(Calendar.getInstance().getTime().getTime());
    }

    /**
     * Getter for first Customers
     * @return first Customers
     */
    public String getCustomer1() {
        return customer1;
    }

    /**
     * Getter for second Customers
     * @return second Customers
     */
    public String getCustomer2() {
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
     * Getter for trade's date
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the trade's date
     * @param date current date
     */
    void setDate(Date date) {
        this.date = date;
    }

    /**
     * Updater for first customer's collection, second customer's collection and current date
     * @param offer1 Firs customer's collection
     * @param offer2 Second customer's collection
     * @param date Current date
     */
    void updateParameters(String customer1, String customer2, Collection offer1, Collection offer2, Date date, boolean flag) {
        if(flag) {
            this.customer1 = customer1;
            this.customer2 = customer2;
            this.offer1 = offer1;
            this.offer2 = offer2;
        } else {
            this.customer1 = customer2;
            this.customer2 = customer1;
            this.offer1 = offer2;
            this.offer2 = offer1;
        }
        this.date = date;
    }
}
