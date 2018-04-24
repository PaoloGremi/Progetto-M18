package TradeCentre.customer;

import java.util.HashSet;

import TradeCentre.card.Card;

public class Collection {

    private HashSet<Card> cardSet;
    private int N = 100;

    public Collection() {
        this.cardSet = new HashSet<Card>();
    }

    // Add a TradeCentre.card to the TradeCentre.card collection who calls this method.
    public boolean addCardToCollection(Card card) {
        cardSet.add(card);
        return true;
    }

    // Search a TradeCentre.card by its identificator in the TradeCentre.card collection who calls this method.
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

    // Remove the TradeCentre.card from the TradeCentre.card collection who calls this method.
    public boolean removeCardFromCollection(Card card) {
        cardSet.remove(card);
        return false;
    }

}
