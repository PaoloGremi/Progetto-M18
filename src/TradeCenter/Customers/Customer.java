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

    /** Method to add a Card to the Customers collection.
     *
     * @param card New Card to add
     * @return boolean to check wheter or not the method ran fine
     */
    public void addCard(Card card){
        try {
            collection.addCardToCollection(card);
        }catch (AddCardException e){
            System.err.println(e.getMessage());
        }

    }

    /**Remove a Card from the collection of the Customers who calls this method.
     *
     * @param card Card to remove
     */
    public void removeCard(Card card){
        try {
            collection.removeCardFromCollection(card);
        }catch (RemoveCardException e){
            System.err.println(e.getMessage());
        }


    }

    /**The customer create a new card that wasn't in the system before.
     *
     * @param description Description of the new card
     * @param id ID of the new card
     */
    public void createCard(Description description, int id){
        Card newCard = new Card(id, description);

            addCard(newCard);

    }

    /**Search a Card in the collections of the customers by tags.
     *
     * @param string String to search cards in the customer's collection.
     * @return Array of cards that match.
     */
    public Card[] searchByString(String string){

        try {
            Card[] cardsFound = collection.searchByString(string);
            return cardsFound;
        }catch (CardNotFoundException e){
            e.cardNotFound(getId(),getUsername());
        }

        return null;

    }

    /**Getter of the customer's id.
     *
     * @return Id of the customer
     */
    public   String getId(){
        return id;
    }

    /**Getter of the customer's username
     *
     * @return Username of the customer.
     */
    public String getUsername() {
        return username;
    }

}
