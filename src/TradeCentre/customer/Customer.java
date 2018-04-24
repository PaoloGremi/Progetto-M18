package TradeCentre.customer;

import TradeCentre.card.Card;
import TradeCentre.card.Description;

public class Customer {


    /**
     * @param id Unique id of the customer
     * @param username Username of the customer
     * @param password Password of the customer
     * @param collection Card collection of the customer
     */
    private String id;
    private String username;
    private String password;
    private Collection collection;

    public Customer(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.collection = new Collection();
    }

    /** Method to add a card to the customer collection
     *
     * @param card New card to
     * @return boolean to check wheter or not the method ran fine
     */
    public boolean addCard(Card card){
        if(collection.addCardToCollection(card)){
            return true;//inizializza
        }
        return false;
    }

    // Remove a TradeCentre.card to the TradeCentre.card collection of the TradeCentre.customer who calls this method.
    public boolean removeCard(Card card){
        if(collection.removeCardFromCollection(card)){
            return  true;
        }
        return  false;
    }

    // The TradeCentre.customer adds a new TradeCentre.card that wasn't in the TradeCentre.card catalog before.
    public boolean createCard(Description d, int id){
        Card newCard = new Card(id, d);
        if(addCard(newCard)){
            return true;
        }
        return false;
    }
   
    // Search a TradeCentre.card by its identificator.
    public Card[] searchByString(String string){
        Card[] cardsFound = collection.searchByString(string);
        return cardsFound;
    }

    //creare metodo per rimuoversi che ritorna il proprio id
    public   String getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

}
