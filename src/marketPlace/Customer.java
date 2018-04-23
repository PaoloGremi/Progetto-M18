package marketPlace;

public class Customer {

    private String user;
    private String pssw;
    private Collection collection;

    // Add a card to the card collection of the customer who calls this method.
    protected boolean addCard(Card card){
        if(collection.addCardToColl(card)){
            return true;
        }
        return false;
    }

    // Remove a card to the card collection of the customer who calls this method.
    protected boolean rmCard(Card card){
        if(collection.remCardFromColl(card)){
            return  true;
        }
        return  false;
    }

    // The customer adds a new card that wasn't in the card catalog before.
    protected boolean createCard(Description d, int id){
        Card newCard = new Card(id, d);
        if(addCard(newCard)){
            return true;
        }
        return  false;
    }
   
    // Search a card by its identificator.
    protected Card[] searchByString(String s){
        Card[] cardsFound = collection.searchByString(s);
        return cardsFound;
    }

}
