package marketPlace;

import java.util.HashSet;

public class Collection {

    private HashSet<Card> cardSet;



    protected boolean addCardToColl(Card card) {
        cardSet.add(card);
        return true; //solo di prova
    }

    protected Card[] searchByString(String string) {
        // to be defined
        Card[] cards = new Card[0];    //solo di prova
        return cards;
    }

    protected boolean remCardFromColl(Card card) {
        cardSet.remove(card);
        return false;
    }

}
