package marketPlace;

import java.util.Date;

public class Offer extends ATrade {

    /**
     * Class for the Offer between two Customers. Extends ATrade class.
     */

    protected Offer(Customer c1, Customer c2, Collection cl1, Collection cl2) {
        super(c1, c2, cl1, cl2);
    }

    protected boolean addCardToColl1(Card card) {
        /* Method to add the selected card to the collection offered by Customer 1 */
        if(super.getColl1().searchByString(card.getDescription().getName()).length == 0) {
            this.getColl1().addCardToColl(card);
            return true;
        }
        return false;
    }

    protected boolean addCardToColl2(Card card) {
        /* Method to add the selected card to the collection requested from Customer 2 */
        if(this.getColl2().searchByString(card.getDescription().getName()).length == 0) {
            this.getColl2().addCardToColl(card);
            return true;
        }
        return false;
    }

    protected boolean remCardFromColl1(Card card) {
        /* Method to remove the selected card from the collection offered by Customer 1 */
        if(this.getColl1().searchByString(card.getDescription().getName()).length != 0) {
            this.getColl1().remCardFromColl(card);
            return true;
        }
        return false;
    }

    protected boolean remCardFromColl2(Card card) {
        /* Method to remove the selected card to the collection requested from Customer 2 */
        if(this.getColl2().searchByString(card.getDescription().getName()).length != 0) {
            this.getColl2().remCardFromColl(card);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        /* A neater toString to print the Offer's specifics */
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
