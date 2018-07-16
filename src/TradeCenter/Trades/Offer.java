package TradeCenter.Trades;

import TradeCenter.Customers.*;
import TradeCenter.Card.Card;
import TradeCenter.Exceptions.CardExceptions.*;

/**
 * Class that allows a customer to make an offer to another one
 */

public class Offer extends ATrade {

    private boolean acceptedOffer = false;

    /**
     * Make an offer between two customers
     * @param customer1 First Customers
     * @param customer2 Second Customers
     * @param offer1 First Customers's collection to be exchanged
     * @param offer2 Second Customers's collection to be exchanged
     */
    public Offer(String customer1, String customer2, Collection offer1, Collection offer2) {
        super(customer1, customer2, offer1, offer2);
    }

    /**
     * A neater toString to print the Offer's specifics
     * @return improved listing of Offer's specifics
     */
    @Override
    public String toString() {
        return "On the " + getDate() + "\n" + super.getCustomer1() + " offers: " + getOffer1().toString() + "\n For: " + getOffer2().toString() + " from user " + super.getCustomer2() + "\n";
    }

}
