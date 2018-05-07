package TradeCenter.Trades;

import TradeCenter.Customers.*;
import TradeCenter.Card.Card;
import TradeCenter.Exceptions.CardExceptions.*;

/**
 * Class for the Offer between two Customers. Extends ATrade class.
 */
public class Offer extends ATrade {

    // Set to True if this offer approves the previously received offer (can't be set True for the first offer in a trade)
    private boolean acceptedOffer = false;

    /**
     * Make an offer between two customers
     * @param customer1 First Customers
     * @param customer2 Second Customers
     * @param offer1 First Customers's collection to be exchanged
     * @param offer2 Second Customers's collection to be exchanged
     */
    public Offer(Customer customer1, Customer customer2, Collection offer1, Collection offer2) {
        super(customer1, customer2, offer1, offer2);
    }

    /**
     * Add a Card to one of the Customers's collection (use addCardToCollection(Card, this))
     * @param card Card to be added
     * @param customer Customer to whose collection the Card should be added
     */
    public void addCardToCollection(Card card, Customer customer) {
        try {
            if (isItTheFirstCustomer(customer)) {
                super.getOffer1().addCardToCollection(card);
            } else super.getOffer2().addCardToCollection(card);
        } catch (AddCardException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Remove a Card from one of the Customers's collection (use removeCardFromCollection(Card, this))
     * @param card Card to be removed
     * @param customer Customer from whose collection the Card should be removed
     */
    public void removeCardFromCollection(Card card, Customer customer) {
        try {
            if (isItTheFirstCustomer(customer)) {
                super.getOffer1().removeCardFromCollection(card);
            } else super.getOffer2().removeCardFromCollection(card);
        } catch (CardNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Checks if the Customers is the first or the second one
     * @param customer Customer to be checked
     * @return True if first, False if second
     */
    private boolean isItTheFirstCustomer(Customer customer) {
        return customer.getId().equals(super.getCustomer1().getId());
    }

    /**
     * Accepts the offer received
     */
    protected void acceptTheOffer() {
        this.acceptedOffer = true;
    }

    /**
     * Checks if this offer is an "accepted offer"
     * @return offer's state
     */
    public boolean isAcceptedOffer() {
        return acceptedOffer;
    }

    /**
     * A neater toString to print the Offer's specifics
     * @return improved listing of Offer's specifics
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("\nOn the ");
        tmp.append(getDate());
        tmp.append("\n");
        tmp.append(super.getCustomer1().getUsername());
        tmp.append(" offers: ");
        tmp.append(getOffer1().toString());
        tmp.append("\n For: ");
        tmp.append(getOffer2().toString());
        tmp.append(" from user ");
        tmp.append(super.getCustomer2().getUsername());
        tmp.append("\n");

        return tmp.toString();
    }

}
