package TradeCenter.Customers;

import java.util.HashSet;

import TradeCenter.Card.Card;

public class Collection {

    private HashSet<Card> cardSet;
    private int N = 100;

    public Collection() {
        this.cardSet = new HashSet<Card>();
    }

    // Add a TradeCenter.Card to the TradeCenter.Card collection who calls this method.
    public boolean addCardToCollection(Card card) {
        cardSet.add(card);
        return true;
    }

    // Search a TradeCenter.Card by its identificator in the TradeCenter.Card collection who calls this method.
    public Card[] searchByString(String str) {

        Card[] cards = new Card[N];       //METTERE CARDINALITA VARIABILE
        int i = 0;

        for (Card crd : cardSet){
            HashSet<String> cardTags = crd.getDescription().getListTag();

            for(String tag : cardTags){

                if(str == tag){

                    cards[i]=crd;
                    i++;

                }
            }
        }
        return cards;
    }

    // Remove the TradeCenter.Card from the TradeCenter.Card collection who calls this method.
    public boolean removeCardFromCollection(Card card) {
        cardSet.remove(card);
        return false;
    }

}
