package TradeCenter.Trades;

import TradeCenter.Card.Card;
import TradeCenter.Customers.Collection;

import java.util.Date;

public class FakeOffer extends Offer{

    private String customer1;
    private String customer2;
    private Collection offer1 = new Collection();
    private Collection offer2 = new Collection();
    protected Date date;

    public void setCustomer1(String customer1) {
        this.customer1 = customer1;
    }

    public void setCustomer2(String customer2) {
        this.customer2 = customer2;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addCardOffer1(Card card) {
        this.offer1.addCardToCollection(card);
    }

    public void addCardOffer2(Card card) {
        this.offer2.addCardToCollection(card);
    }

    public String getFCustomer1() {
        return customer1;
    }

    public String getFCustomer2() {
        return customer2;
    }

    @Override
    public Collection getOffer1() {
        return offer1;
    }

    @Override
    public Collection getOffer2() {
        return offer2;
    }

    public Date getDate() {
        return date;
    }
}
