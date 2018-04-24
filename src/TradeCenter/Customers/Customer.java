package TradeCenter.Customers;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;

public class Customer {


    /**
     * @param id Unique id of the Customers
     * @param username Username of the Customers
     * @param password Password of the Customers
     * @param collection Card collection of the Customers
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

    /** Method to add a Card to the Customers collection
     *
     * @param card New Card to
     * @return boolean to check wheter or not the method ran fine
     */
    public boolean addCard(Card card){
        if(collection.addCardToCollection(card)){
            return true;//inizializza
        }
        return false;
    }

    // Remove a TradeCenter.Card to the TradeCenter.Card collection of the TradeCenter.Customers who calls this method.
    public boolean removeCard(Card card){
        if(collection.removeCardFromCollection(card)){
            return  true;
        }
        return  false;
    }

    // The TradeCenter.Customers adds a new TradeCenter.Card that wasn't in the TradeCenter.Card catalog before.
    public boolean createCard(Description d, int id){
        Card newCard = new Card(id, d);
        if(addCard(newCard)){
            return true;
        }
        return false;
    }
   
    // Search a TradeCenter.Card by its identificator.
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
