package TradeCenter.Customers;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Exceptions.CardExceptions.AddCardException;
import TradeCenter.Exceptions.CardExceptions.CardNotFoundException;
import TradeCenter.Exceptions.CardExceptions.RemoveCardException;

/**
 * Class that represents a customer's collection.
 */

public class Collection implements Iterable<Card>, Serializable {

    private static final long serialVersionUID = -6729279270235007035L;

    /**
     * @param cardSet HashSet that contains the card of the collection.
     */
    private HashSet<Card> cardSet;

    public Collection() {
        this.cardSet = new HashSet<Card>();
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
     * Add a Card to the collection of the customer who calls this method.
     *
     * @param card Card to add.
     * @return boolean to check wheter or not the method ran fine
     */
    public boolean addCardToCollection(Card card) {
        return cardSet.add(card);
    }

    /**
     * Search a Card by its tags in the collection of the customer.
     *
     * @param string String to search in the HashSet of the tags for every single card in the collection.
     * @return HashSet of cards that match.
     */
    public Collection searchByString(String string) {
        Collection cards = new Collection();
        for (Card card : cardSet){
            HashSet<String> cardTags = card.getDescription().getListTag();
            for(String tag : cardTags){
                if(string.equals(tag)){
                    cards.addCardToCollection(card);
                }
            }
        }
        if(cards.collectionIsEmpty()) throw  new CardNotFoundException();
        return cards;
    }

    /**
     * Remove the Card from the collection of the customer who calls this method.
     * @param card Card to remove
     */
    public void removeCardFromCollection(Card card) {
        if(!cardSet.remove(card)) throw new CardNotFoundException();

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
        return  cardSet.isEmpty();
    }

    /**
     * Search a Card by its description in the collection of the customer.
     *
     * @param description Description to search in the HashSet of the tags for every single card in the collection.
     * @return boolean if exists.
     */
     public boolean containsDescription(Description description){ //todo check if really necessary
        for (Card card : cardSet){
            Description cardDescription = card.getDescription();
            if(description.equals(cardDescription)){
                return true;
            }
        }
        return false;
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
