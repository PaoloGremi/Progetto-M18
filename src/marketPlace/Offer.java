package marketPlace;

import java.util.Date;

public class Offer extends ATrade {

    /**
     * Class for the Offer between two Customers. Extends ATrade class.
     */
    
    // Formalise a offer between two customers.
    protected Offer(Customer c1, Customer c2, Collection cl1, Collection cl2) {
        super(c1, c2, cl1, cl2);
    }

    // Add the selected card to the collection offered by Customer 1.
    protected boolean addCardToColl1(Card card) {
        if(super.getColl1().searchByString(card.getDescription().getName()).length == 0) {
            this.getColl1().addCardToCollection(card);
            return true;
        }
        return false;
    }

    // Method to add the selected card to the collection requested from Customer 2.
    protected boolean addCardToColl2(Card card) {
        if(this.getColl2().searchByString(card.getDescription().getName()).length == 0) {
            this.getColl2().addCardToCollection(card);
            return true;
        }
        return false;
    }

    // Method to remove the selected card from the collection offered by Customer 1.
    protected boolean remCardFromColl1(Card card) {
        if(this.getColl1().searchByString(card.getDescription().getName()).length != 0) {
            this.getColl1().removeCardFromCollection(card);
            return true;
        }
        return false;
    }

    // Method to remove the selected card to the collection requested from Customer 2.
    protected boolean remCardFromColl2(Card card) {
        if(this.getColl2().searchByString(card.getDescription().getName()).length != 0) {
            this.getColl2().removeCardFromCollection(card);
            return true;
        }
        return false;
    }

    // A neater toString to print the Offer's specifics.
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("\nOn the ");
        tmp.append(getDate().getDate());
        tmp.append("\n Offers: ");
        tmp.append(getColl1().toString());
        tmp.append("\n For: ");
        tmp.append(getColl2().toString());

        return tmp.toString();
    }

}
