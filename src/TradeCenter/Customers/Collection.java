package TradeCenter.Customers;

import java.util.HashSet;

import TradeCenter.Card.Card;

public class Collection {

    private HashSet<Card> cardSet;
    private int N = 100;

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
     * @return Array of cards that match.
     */
    public Card[] searchByString(String string) {

        Card[] cards = new Card[N];       //todo: METTERE CARDINALITA VARIABILE
        int i = 0;

        for (Card card : cardSet){
            HashSet<String> cardTags = card.getDescription().getListTag();

            for(String tag : cardTags){

                if(string == tag){

                    cards[i]=card;
                    i++;

                }
            }
        }

        if(cards[0]==null) throw  new CardNotFoundException();

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

    /**V
     * erify that the card is in the collection of the customer.
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

}
