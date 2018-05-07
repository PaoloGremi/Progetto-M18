package Test.OfferTradeTest;

import TradeCenter.Card.Card;
import TradeCenter.Card.CardType;
import TradeCenter.Card.Description;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Offer;
import TradeCenter.Trades.Trade;

public class test {
    // simple test to check Offer and Trade and their methods
    public static void main(String[] args) {

        TradeCenter tc = new TradeCenter();
        Customer c1 = new Customer("us001", "Mark Johnson", "AnObviousPass001");
        Customer c2 = new Customer("ca002", "Philip De Suigen", "SuchSecurity002");

        tc.addCustomer(c1.getUsername(), "AnObviousPass001");
        tc.addCustomer(c2.getUsername(), "SuchSecurity002");

        try {
            Description d1 = new YuGiOhDescription("Big Blue Dragon", "A big dragon, which is blue", "./src/BS_001.jpg","NON SO", 2,0,0,0,0);
            Description d2 = new YuGiOhDescription("Big Trap", "A trap, which is big", "./src/BS_001.jpg", "NON SO", 2,0,0,0,0);

            Card card1 = new Card(01, d1);
            Card card2 = new Card(02, d2);

            Collection col1 = new Collection();
            col1.addCardToCollection(card1);
            col1.addCardToCollection(card2);
            Collection col2 = new Collection();
            col2.addCardToCollection(card1);
            col2.addCardToCollection(card2);

            Offer o1 = new Offer(c1, c2, col1, col2);
            Trade t = new Trade(o1);
            Offer o2 = new Offer(c2, c1, col2, col1);
            o2.acceptTheOffer();
            t.update(o2);
            System.out.println(t.isDoneDeal());
            System.out.println(t);

            System.out.println("\n");

            Offer o3 = new Offer(c1, c2, col1, col2);
            Trade t1 = new Trade(o3);
            col1.removeCardFromCollection(card1);
            Offer o4 = new Offer(c2, c1, col2, col1);
            t1.update(o4);
            System.out.println(t1.isDoneDeal());
            System.out.println(t1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
