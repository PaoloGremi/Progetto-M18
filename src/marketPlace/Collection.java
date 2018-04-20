package marketPlace;

import java.util.HashSet;

public class Collection {

    private HashSet<Card> cardSet;



    protected boolean addCardToColl(Card card) {
        cardSet.add(card);
        return true; //solo di prova
    }

    protected Card[] searchByString(String str) {

        Card[] cards = new Card[100];
        int i = 0;

        for (Card crd : cardSet){
            HashSet<String> cardTags = crd.getDescription().getListTag();

            for(String tag : cardTags){

                if(str == tag){

                    cards[i]=crd;

                }
            }
        }
        return cards;
    }

    protected boolean remCardFromColl(Card card) {
        cardSet.remove(card);
        return false;
    }

}
