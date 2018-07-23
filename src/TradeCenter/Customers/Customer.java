package TradeCenter.Customers;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Exceptions.CardExceptions.AddCardException;
import TradeCenter.Exceptions.CardExceptions.CardNotFoundException;
import TradeCenter.Exceptions.CardExceptions.RemoveCardException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *Class that characterizes a customer
 */
public class Customer implements Serializable {


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
    private ArrayList<Description> wishList;

    private static final long serialVersionUID = 1977347776266038746L;

    public Customer(String id, String username, String password) throws CheckPasswordConditionsException {

        this.id = id;
        this.username = username;
        this.password = checkPasswordConditions(password);
        this.collection = new Collection();
        this.wishList = new ArrayList<>();
    }

    /**
     * Verify that the password is valid under the conditions: eight characters, one number, one uppercase and one lowercas.
     *
     * @param password Password to verify if valid.
     * @return Password accepted.
     */
    private String checkPasswordConditions(String password) {

        boolean noUppercase = true;
        boolean noLowercase = true;
        boolean noNumber = true;
        boolean length = true;

        int passwordLength = password.length();

        if (passwordLength > 7) {
            length = false;
        }

        for (int index = 0; index < passwordLength; index++) {
            if (Character.isUpperCase(password.charAt(index))) {
                noUppercase = false;
            }
            if (Character.isLowerCase(password.charAt(index))) {
                noLowercase = false;
            }
            if ((int) password.charAt(index) > 47 && (int) password.charAt(index) < 58) {
                noNumber = false;
            }
        }

        if (noLowercase || noNumber || noUppercase || length) {
            throw new CheckPasswordConditionsException();
        }

        return password;

    }

    /**
     * Check the password of the customer
     *
     * @param password String to check
     * @return Boolean
     */
    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {return true;}

        return false;
    }

    /**
     * Method to add a Card to the Customer's collection.
     *
     * @param card New Card to add
     */
    public void addCard(Card card) {
        try {
            collection.addCardToCollection(card);
        } catch (AddCardException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Method to add Cards to the Customer's collection.
     * @param cards new cards add
     */
    public void addCard(ArrayList<Card> cards) {
        try {
            for(Card card : cards) {
                collection.addCardToCollection(card);
            }
        } catch (AddCardException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Remove a Card from the collection of the Customers who calls this method.
     *
     * @param card Card to remove
     */
    public void removeCard(Card card) {
        try {
            collection.removeCardFromCollection(card);
        } catch (RemoveCardException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Remove a Card from the collection of the Customers who calls this method.
     *
     * @param cards cards to remove
     */
    public void removeCard(ArrayList<Card> cards) {
        try {
            for(Card card : cards){
                collection.removeCardFromCollection(card);
            }
        } catch (RemoveCardException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method to add a Card to the Customer's wish list .
     *
     * @param cardDescription Card description to add.
     */
    public void addCardToWishList(Description cardDescription) {
        try {

            wishList.add(cardDescription);

        } catch (AddCardException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Remove a Card from the wish list of the Customers who calls this method.
     *
     * @param cardDescription Card description to remove.
     */
    public void removeFromWishList(Description cardDescription) {
        try {

            wishList.remove(cardDescription);

        } catch (RemoveCardException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Getter of the customer's id.
     *
     * @return Id of the customer
     */
    public String getId() {
        return id;
    }

    /**
     * Getter of the customer's username
     *
     * @return Username of the customer.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter of the customer's password
     *
     * @return Customer's password
     */
    public String getPassword() { return this.password; }

    /**
     *Getter of the customer's collection
     * @return the collection of the customer
     */
    public Collection getCollection() {
        return collection;
    }

    /**
     *Getter of a customer's Wishlist
     *
     * @return the wishlist of the customer
     */
    public ArrayList<Description> getWishList() {
        return wishList;
    }

    /**
     * Override toString method
     *
     * @return The description of the User
     */
    @Override
    public String toString() {
        return this.id + ": " + this.username + "\n";
    }
}
