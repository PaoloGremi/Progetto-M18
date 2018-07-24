package TradeCenter.Customers;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Exceptions.CardExceptions.AddCardException;
import TradeCenter.Exceptions.CardExceptions.CardNotFoundException;

/**
 * Class that represents a customer's collection.
 */
public class Collection implements Iterable<Card>, Serializable {

    /**
     * @param cardSet HashSet that contains the card of the collection.
     * @param serialVersionUID for //todo ask roberto
     */
    private HashSet<Card> cardSet;
    private static final long serialVersionUID = -6729279270235007035L;

    public Collection() {
        this.cardSet = new HashSet<Card>();
    }

    /**
     * Add a Card to the collection of the customer who calls this method.
     *
     * @param card Card to add.
     * @return boolean to check wheter or not the method ran fine
     */
    public boolean addCardToCollection(Card card) {
         if(cardSet.add(card)==false)
             throw new AddCardException();
         else
             return true;
    }

    /**
     * Remove the Card from the collection of the customer who calls this method.
     * @param card Card to remove
     */
    public void removeCardFromCollection(Card card) {
        if(!cardSet.remove(card)) throw new CardNotFoundException();

    }

    /**
     * Search a Card by its description in the collection of the customer.
     *
     * @param description Description to search in the HashSet of the tags for every single card in the collection.
     * @return boolean if exists.
     */
    public boolean isInTheCollection(Description description){ //todo check if really necessary
        for (Card card : cardSet){
            Description cardDescription = card.getDescription();
            if(description.equals(cardDescription)){
                return true;
            }
        }
        return false;
    }

    /**
     * Verify that the card is in the collection of the customer.
     *
     * @param card Card to search in the collection.
     * @return boolean to check wheter or not the method ran fine'
     */
    public boolean isInTheCollection(Card card) {
        return cardSet.contains(card);
    }

    /**
     * Check if the collection of the customer is empty.
     *
     * @return boolean to check wheter or not the method ran fine.
     */
    public boolean collectionIsEmpty(){
        return cardSet.isEmpty();
    }

    /**
     * Return the cardSet (for DB purposes)
     * @return this card set
     */
    public HashSet<Card> getSet() { return this.cardSet; }

    /**
     * Iterator for the collection.
     *
     * @return the iterator.
     */
    @Override
    public Iterator<Card> iterator() {
        return cardSet.iterator();
    }

    /**
     * Override toString method
     *
     * @return New String for the collection class
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for(Card card :cardSet){
            tmp.append(card.toString()).append(", ");
        }
        // if last characters are " ,", remove them
        if(tmp.charAt(tmp.length() - 2) == ',') {
            tmp.substring(0, tmp.length() - 2);
        }
        return tmp.toString();
    }
}
