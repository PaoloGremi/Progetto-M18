package DatabaseProxy;

import TradeCenter.Card.Card;
import TradeCenter.Customers.Collection;

import java.sql.Date;

public class FakeOffer {

    private String customer1;
    private String customer2;
    private Collection offer1 = new Collection();
    private Collection offer2 = new Collection();
    private Date date;
    private boolean doneDeal;
    private boolean positiveEnd;
    private int id;

    protected void setCustomer1(String customer1) {
        this.customer1 = customer1;
    }

    protected void setCustomer2(String customer2) {
        this.customer2 = customer2;
    }

    protected void setDate(Date date) {
        this.date = date;
    }

    protected void addCardOffer1(Card card) {
        this.offer1.addCardToCollection(card);
    }

    protected void addCardOffer2(Card card) {
        this.offer2.addCardToCollection(card);
    }

    protected void setDoneDeal(Boolean doneDeal) { this.doneDeal = doneDeal; }

    protected void setPositiveEnd(Boolean positiveEnd) { this.positiveEnd = positiveEnd; }

    protected void setId(int id) { this.id = id; }

    public String getCustomer1() {
        return customer1;
    }

    public String getCustomer2() {
        return customer2;
    }

    public Collection getOffer1() {
        return offer1;
    }

    public Collection getOffer2() {
        return offer2;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getDoneDeal() { return doneDeal; }

    public boolean isPositiveEnd() { return positiveEnd; }

    public int getId() { return id; }
}