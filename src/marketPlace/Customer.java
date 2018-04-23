package marketPlace;

public class Customer {

    private int id;
    private String username;
    private String password;
    private Collection collection;

    public Customer(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.collection = new Collection();
    }

    // Add a card to the card collection of the customer who calls this method.
    protected boolean addCard(Card card){
        if(collection.addCardToColl(card)){
            return true;
        }
        return false;
    }

    // Remove a card to the card collection of the customer who calls this method.
    protected boolean removeCard(Card card){
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
        return false;
    }
   
    // Search a card by its identificator.
    protected Card[] searchByString(String s){
        Card[] cardsFound = collection.searchByString(s);
        return cardsFound;
    }

    //creare metodo per rimuoversi che ritorna il proprio id

}
