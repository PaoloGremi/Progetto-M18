package TradeCentre.Trades;

import TradeCentre.customer.*;
import TradeCentre.card.Card;

public class Offer extends ATrade {

    /**
     * Class for the Offer between two Customers. Extends ATrade class.
     */

    /**
     * Make an offer between two customers
     * @param customer1 First customer
     * @param customer2 Second customer
     * @param collection1 First customer's collection to be exchanged
     * @param collection2 Second customer's collection to be exchanged
     */
    protected Offer(Customer customer1, Customer customer2, Collection collection1, Collection collection2) {
        super(customer1, customer2, collection1, collection2);
    }

    /**
     * Add a card to one of the customer's collection (use addCardToCollection(card, this))
     * @param card Card to be added
     * @param customer Customer to whose collection the card should be added
     */
    public void addCardToCollection(Card card, Customer customer) { //todo: add try-catch and exceptions
        if(isItTheFirstCustomer(customer)) {
            super.getCollection1().addCardToCollection(card);
        } else super.getCollection2().addCardToCollection(card);
    }

    /**
     * Remove a card from one of the customer's collection (use removeCardFromCollection(card, this))
     * @param card Card to be removed
     * @param customer Customer from whose collection the card should be removed
     */
    public void removeCardFromCollection(Card card, Customer customer) {
        if (isItTheFirstCustomer(customer)) {
            super.getCollection1().removeCardFromCollection(card);
        } else super.getCollection2().removeCardFromCollection(card);
    }

    /**
     * Checks if the customer is the first or the second one
     * @param customer
     * @return True if first, False if second
     */
    private boolean isItTheFirstCustomer(Customer customer) {
        return customer.getId().equals(super.getCustomer1()().getId);
    }

    /**
     * A neater toString to print the Offer's specifics
     * @return improved listing of Offer's specifics
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("\nOn the ");
        tmp.append(getDate().getDate());
        tmp.append("\n Offers: ");
        tmp.append(getCollection1().toString());
        tmp.append("\n For: ");
        tmp.append(getCollection2().toString());

        return tmp.toString();
    }

}
