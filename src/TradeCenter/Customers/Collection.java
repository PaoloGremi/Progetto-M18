package TradeCenter.Customers;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Exceptions.CardExceptions.AddCardException;
import TradeCenter.Exceptions.CardExceptions.CardNotFoundException;
import TradeCenter.Exceptions.CardExceptions.RemoveCardException;

public class Collection implements Iterable<Card>, Serializable {

    /**
     * @param cardSet HashSet that contains the card of the collection.
     */
    private HashSet<Card> cardSet;

    public Collection() {
        this.cardSet = new HashSet<Card>();
    }

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
        if(true) {                          //todo Al posto del "true" va messo il metodo che verifichi la presenza della carta nel database
            cardSet.add(card);
        }
        else {
            throw  new AddCardException();
        }
        return true;
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
        if(isInTheCollection(card)) {
            cardSet.remove(card);
        }
        else{
            throw new RemoveCardException();
        }

    }

    /**
     * Verify that the card is in the collection of the customer.
     *
     * @param card Card to search in the collection.
     * @return boolean to check wheter or not the method ran fine'
     */
    public boolean isInTheCollection(Card card){
        for(Card cardInThecollection : cardSet){
            if(cardInThecollection.equals(card)) return true;
        }

        return false;
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
     * @return HashSet of cards that match.
     */
    public Collection searchByDescription(Description description) {
        Collection cards = new Collection();
        for (Card card : cardSet){
            Description cardDescription = card.getDescription();
                if(description.equals(cardDescription)){
                    cards.addCardToCollection(card);
                    }
        }
        if(cards.collectionIsEmpty()) throw  new CardNotFoundException();
        return cards;
    }

    /**
     * Override toString method
     *
     * @return New String for the collection class
     */

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        int index = 1;
        for(Card card :cardSet){
            tmp.append(card.toString());
            if(index != cardSet.size()){
                tmp.append(", ");
            }

        }

        return tmp.toString();
    }
}
