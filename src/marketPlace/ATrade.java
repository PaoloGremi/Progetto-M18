package marketPlace;

import java.util.Date;

public abstract class ATrade {

    /**
     * Abstract class for Offer and Trade.
     * @param user1:    Customer 1;
     * @param user2:    Customer 2;
     * @param coll1:    Collection offered by Customer 1 to Customer 2;
     * @param coll2:    Collection requested by Customer 1 from Customer 2;
     * @param date:     Latest update date and time;
     */

    private Customer user1;
    private Customer user2;
    private Collection coll1;
    private Collection coll2;
    private Date date;

    protected ATrade(Customer c1, Customer c2, Collection cl1, Collection cl2) {
        this.user1 = c1;
        this.user2 = c2;
        this.coll1 = cl1;
        this.coll2 = cl2;
        this.date = new Date();
    }

    protected Customer getUser1() {
        return user1;
    }

    protected Customer getUser2() {
        return user2;
    }

    protected Collection getColl1() {
        return coll1;
    }

    protected Collection getColl2() {
        return coll2;
    }

    protected Date getDate() {
        return date;
    }

    public void setColl1(Collection coll1) {
        this.coll1 = coll1;
    }

    public void setColl2(Collection coll2) {
        this.coll2 = coll2;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
