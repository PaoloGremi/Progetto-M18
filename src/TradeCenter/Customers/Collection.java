package TradeCenter.Customers;

import java.util.HashSet;

import TradeCenter.Card.Card;

public class Collection {

    private HashSet<Card> cardSet;

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
        if(true) {                          //Al posto del "true" va messo il metodo che verifichi la presenza della carta nel database
            cardSet.add(card);
        }
        else {
            throw  new AddCardException();
        }
        return true;
    }

    /**
     * Search a Card by its tads in the collection of the customer.
     *
     * @param string String to search in the HashSet of the tags for every single card in the collection.
     * @return HashSet of cards that match.
     */
    public HashSet<Card> searchByString(String string) {

        HashSet<Card> cards = new HashSet<Card>();       //todo: METTERE CARDINALITA VARIABILE


        for (Card card : cardSet){
            HashSet<String> cardTags = card.getDescription().getListTag();

            for(String tag : cardTags){

                if(string == tag){

                    cards.add(card);


                }
            }
        }

        if(cards.isEmpty()) throw  new CardNotFoundException();

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
