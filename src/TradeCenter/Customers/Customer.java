package TradeCenter.Customers;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Exceptions.CardExceptions.AddCardException;
import TradeCenter.Exceptions.CardExceptions.CardNotFoundException;
import TradeCenter.Exceptions.CardExceptions.RemoveCardException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.NoTradesExeption;

import java.util.ArrayList;
import java.util.HashSet;

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
    private Collection wishList;
    private ArrayList<String> tradeList;

    public Customer(String id, String username, String password) throws CheckPasswordConditionsException {

            this.id = id;
            this.username = username;
            this.password = checkPasswordConditions(password);
            this.collection = new Collection();
            this.wishList = new Collection();
            this.tradeList = new ArrayList<>();

    }

    /**
     * Method to add a Card to the Customer's collection.
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

    /**
     * Remove a Card from the collection of the Customers who calls this method.
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

    /**
     * The customer create a new card that wasn't in the system before.
     *
     * @param description Description of the new card
     * @param id ID of the new card
     */
    public void createCard(Description description, int id){
        Card newCard = new Card(id, description);

            addCard(newCard);

    }

    /**
     * Search a Card in the collections of the customers by tags.
     *
     * @param string String to search cards in the customer's collection.
     * @return HashSet of cards that match.
     */
    public Collection searchByString(String string){

        try {
            Collection cardsFound = collection.searchByString(string);
            return cardsFound;
        }catch (CardNotFoundException e){
            System.err.println(e.cardNotFound(getId(),getUsername()));
        }

        return null;

    }

    /**
     * Verify that the password is valid under the conditions: eight characters, one number, one uppercase and one lowercas.
     *
     * @param password Password to verify if valid.
     * @return Password accepted.
     */
    public String checkPasswordConditions(String password){

        boolean noUppercase = true;
        boolean noLowercase = true;
        boolean noNumber = true;
        boolean length = true;

        int passwordLength = password.length();

        if(passwordLength > 7){
            length = false;
        }

        for(int index = 0; index < passwordLength; index++){
            if(Character.isUpperCase(password.charAt(index))){
                noUppercase = false;
            }
            if(Character.isLowerCase(password.charAt(index))){
                noLowercase = false;
            }
            if((int)password.charAt(index)>47 && (int)password.charAt(index)<58){
                noNumber = false;
            }
        }

        if(noLowercase || noNumber || noUppercase || length){
            throw new CheckPasswordConditionsException();
        }

        return  password;

    }

    /**
     *Method to add a Card to the Customer's wish list .
     *
     * @param card Card to add.
     */
    public void addCardToWishList(Card card){
        try {

            wishList.addCardToCollection(card);

        }catch (AddCardException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Remove a Card from the wish list of the Customers who calls this method.
     *
     * @param card Card to remove.
     */
    public void removeFromWishList(Card card){
        try {

            wishList.removeCardFromCollection(card);

        }catch (RemoveCardException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Search a Card in the collections of the customers by tags.
     *
     * @param description String to search cards in the customer's collection.
     * @return HashSet of cards that match.
     */
    public Collection searchByDescription(Description description){

        try {
            Collection cardsFound = collection.searchByDescription(description);
            return cardsFound;
        }catch (CardNotFoundException e){
            System.err.println(e.cardNotFound(getId(),getUsername()));
        }

        return null;

    }

    /**
     * Add a trade to the list if the customer is involved.
     *
     * @param trade Trade to add
     */
    public void addToTradeList(String trade){
        tradeList.add(trade);
    }

    /**
     * Method to return the list with all the trades of the customer
     *
     * @return The list with the trades
     */
    public ArrayList<String> getTradeList(){
        if(tradeList.isEmpty()) throw new NoTradesExeption();
        return tradeList;
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
