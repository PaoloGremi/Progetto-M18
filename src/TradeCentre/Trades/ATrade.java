package TradeCentre.Trades;

import java.util.Date;
import TradeCentre.customer.*;

public abstract class ATrade {

    /**
     * Abstract class for Offer and Trade.
     * @param user1 Customer 1
     * @param user2 Customer 2
     * @param coll1 Collection offered by Customer 1 to Customer 2
     * @param coll2 Collection requested by Customer 1 from Customer 2
     * @param date Latest update date and time
     */
    private Customer customer1;
    private Customer customer2;
    private Collection collection1;
    private Collection collection2;
    private Date date;

    /**
     *
     * @param customer1 First customer
     * @param customer2 Second customer
     * @param collection1 First customer's collection to be exchanged
     * @param collection2 Second customer's collection to be exchanged
     */
    protected ATrade(Customer customer1, Customer customer2, Collection collection1, Collection collection2) {
        this.customer1 = customer1;
        this.customer2 = customer2;
        this.collection1 = collection1;
        this.collection2 = collection2;
        this.date = new Date();
    }

    /**
     * Getter for first customer
     * @return first customer
     */
    protected Customer getCustomer1() {
        return customer1;
    }

    /**
     * Getter for second customer
     * @return second customer
     */
    protected Customer getCustomer2() {
        return customer2;
    }

    /**
     * Getter for first customer's collection
     * @return first customer's collection
     */
    protected Collection getCollection1() {
        return collection1;
    }

    /**
     * Getter for second customer's collection
     * @return second customer's collection
     */
    protected Collection getCollection2() {
        return collection2;
    }

    /**
     * Getter for current date
     * @return current date
     */
    protected Date getDate() {
        return date;
    }

    /**
     * Setter for first customer
     * @return void
     */
    public void setCollection1(Collection collection1) {
        this.collection1 = collection1;
    }

    /**
     * Setter for second customer's collection
     * @return void
     */
    public void setCollection2(Collection collection2) {
        this.collection2 = collection2;
    }

    /**
     * Setter for current date
     * @return void
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
