package marketPlace;

import java.util.HashSet;

public class Collection {

    private HashSet<Card> cardSet;


    // Add a card to the card collection who calls this method.
    protected boolean addCardToCollection(Card card) {
        cardSet.add(card);
        return true; //solo di prova
    }

    // Search a card by its identificator in the card collection who calls this method.
    protected Card[] searchByString(String str) {

        Card[] cards = new Card[100];       //METTERE CARDINALITA VARIABILE
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

    // Remove the card from the card collection who calls this method.
    protected boolean removeCardFromCollection(Card card) {
        cardSet.remove(card);
        return false;
    }

}
