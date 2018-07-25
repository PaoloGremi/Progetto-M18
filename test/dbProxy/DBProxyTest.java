package dbProxy;

import DatabaseProxy.DBProxy;
import TradeCenter.Card.Card;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Customers.Customer;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DBProxyTest {
    private DBProxy proxy;
    private Customer c1;
    private Customer c2;
    private PokemonDescription pd1;
    private Card card1;
    /**
     * DB initialized with two Customer and 8 Pokemon Description and 8 YuGIOH Description.
     */
    /**
     * Get instance of DBProxy and first case
     */
    @Before
    public void getDBProxyInstance() {
        DBProxy proxy = DBProxy.getInstance();
        this.proxy = proxy;

        Customer c1 = new Customer("USER-1", "User1", "Password1");
        Customer c2 = new Customer("USER-2", "User2", "Password1");
        this.c1 = c1;
        this.c2 = c2;

        //Description used in tests
        BufferedImage bfNull = null;
        PokemonDescription pd1 = new PokemonDescription("Blastoise", "Shellfish Pokémon jewels", bfNull, 1, "Water", 100, 189, "5’3’’", 52);
        this.pd1 = pd1;

        Card card1=new Card(1,pd1);
        this.card1=card1;

    }
    /**
     * Retrieves a single customer from the database given its username (used in LogIn procedure)
     */
    @Test
    public void retrieveSingleCustomer() { //
        Customer cRetrieved1 = proxy.retrieveSingleCustomer("User1");
        assertEquals(c1, cRetrieved1);
        Customer cRetrieved2 = proxy.retrieveSingleCustomer("User2");
        assertEquals(c2, cRetrieved2);
    }

    /**
     * Get all customer's username from db
     */
    @Test
    public void getAllCustomersNames() {
        ArrayList<String> listExpected = new ArrayList<>();
        ArrayList<String> listFounded = new ArrayList<>();
        listFounded = proxy.getAllCustomersNames();
        listExpected.add(c1.getUsername());
        listExpected.add(c2.getUsername());
        assertEquals(listExpected, listFounded);
    }

    /**
     * Retrieves a singles customer given his id
     */
    @Test
    public void retrieveSingleCustomerByID() {
        Customer cFounded = proxy.retrieveSingleCustomerByID("USER-1");
        assertEquals(c1, cFounded);

    }

    /**
     * Add a Customer, with his collection and wishlisti to DB.
     * Comparison between Csutomer added and the same Customer Retrieved.
     * <p>
     * Modify DB
     */
    @Test
    public void addCustomerToDatabase() {
        Customer c3 = new Customer("USER-3", "User3", "Password1");
        BufferedImage bfNull = null;
        c3.addCardToWishList(pd1);
        c3.addCard(new Card(1, pd1));
        proxy.addCustomerToDatabase(c3);
        Customer cRetrieved = proxy.retrieveSingleCustomer("User3");
        assertEquals(c3, cRetrieved);
    }

    /**
     * Update Customer in DB with one card added and one description added in WishList
     * Modify DB: add 1 card in customer's collection, and the description in his wishlist
     */
    @Test
    public void updateCustomer() {
        Card card1 = new Card(1, pd1);
        c1.addCard(card1);
        c1.addCardToWishList(pd1);
        proxy.updateCustomer(c1);
        Customer cRetrieved = proxy.retrieveSingleCustomer("User1");
        assertEquals(c1, cRetrieved);


        c1.removeCard(card1);
        c1.removeFromWishList(pd1);
    }

    /**
     * Remove a description from Customer's Wishlist in DB.
     *
     */
    @Test
    public void removeCardFromWishlist() {
        c1.addCardToWishList(pd1);
        proxy.updateCustomer(c1);
        c1.removeFromWishList(pd1);
        proxy.updateCustomer(c1);
        Customer cRetrieved=proxy.retrieveSingleCustomer("User1");
        assertEquals(c1.getWishList(),cRetrieved.getWishList());
    }

}