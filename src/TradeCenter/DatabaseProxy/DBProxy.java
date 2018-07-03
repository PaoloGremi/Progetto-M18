package TradeCenter.DatabaseProxy;

import TradeCenter.Card.Card;
import TradeCenter.Card.CardCatalog;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.FakeOffer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Proxy for database management
 * @author Roberto Gallotta
 */
public class DBProxy implements IProxy{

    private Connection connection;
    private DBAtomicRetriever dbRet = new DBAtomicRetriever();
    private DBAtomicInserter dbIns = new DBAtomicInserter();
    private DBAtomicUpdater dbUp = new DBAtomicUpdater();
    private DBAtomicDeleter dbDel = new DBAtomicDeleter();
    private DBConnectionManager dbConn = new DBConnectionManager();

    /**
     * Populates catalog with chosen descriptions
     * @param cc: catalog to populate
     * @param tablename: type of descriptions to load
     */
    @Override
    public void populateCatalog(CardCatalog cc, String tablename) {
        connection = dbConn.connectToDB(connection);
        int size;
        switch(tablename) {
            case "pokemon_card":
                size = dbRet.getTableSize(connection, tablename);
                for(int i = 0; i<size; i++) {
                    cc.addDescription(dbRet.retrieveSinglePokemonDescription(connection, i));
                }
                break;
            case "yugioh_card":
                size = dbRet.getTableSize(connection, tablename);
                for(int i = 0; i<size; i++) {
                    cc.addDescription(dbRet.retrieveSingleYugiohDescription(connection, i));
                }
                break;
        }
        connection = dbConn.disconnectFromDB(connection);

    }

    /**
     * Adds full customer to customer hashmap
     * @param customers: hashmap to be populated
     */
    @Override
    public void retrieveCustomers(HashMap<String, Customer> customers) {
        connection = dbConn.connectToDB(connection);
        int n = dbRet.getTableSize(connection, "customers");
        for(int i=1; i<= n; i++) {
            Customer customer = dbRet.retrieveSingleCustomerByUserID(connection, i);
            // add card collection
            for(Card card: dbRet.retrieveCardsInCustomerCollection(connection, customer)) {
                customer.addCard(card);
            }
            // add card wishlist
            for(Description description: dbRet.retrieveDescriptionsInCustomerWishlist(connection, customer)) {
                customer.addCardToWishList(description);
            }
            // add customer
            customers.put(customer.getId(), customer);
        }
        connection = dbConn.disconnectFromDB(connection);
    }

    /**
     * Retrieve a single customer from the database given its username (used in LogIn procedure)
     * @param username: customer's username
     * @return customer
     */
    @Override
    public Customer retrieveSingleCustomer(String username) {
        connection = dbConn.connectToDB(connection);
        Customer customer = null;
        customer = dbRet.retrieveSingleCustomerByUsername(connection, username);
        connection = dbConn.disconnectFromDB(connection);
        return customer;
    }

    /**
     * Adds a customer in the database
     * @param customer: customer to be added
     */
    @Override
    public void addCustomerToDatabase(Customer customer) {
        connection = dbConn.connectToDB(connection);
        // add customer
        dbIns.insertCustomer(connection, customer);
        // add customer's wishlist
        for (Description description : customer.getWishList()) {
            dbIns.insertWishlist(connection, description, customer);
        }
        // add customer's collection
        for (Card card : customer.getCollection()) {
            dbIns.insertCard(connection, card, customer);
        }
        connection = dbConn.disconnectFromDB(connection);
    }

    /**
     * Updates a customer (customer's collection and wishlist)
     * @param customer: customer to be updated
     */
    @Override
    public void updateCustomer(Customer customer) {
        connection = dbConn.connectToDB(connection);
        // update customer's cards
            //get db's customer's collection
        ArrayList<Card> oldCollection = dbRet.retrieveCardsInCustomerCollection(connection, customer);
            //get cards to update
        ArrayList<Card> toUpdate = new ArrayList<Card>(customer.getCollection().getSet());
        toUpdate.removeAll(oldCollection);
        for(Card card:toUpdate) {
            dbUp.updateCard(connection, card, customer.getId());
        }
            //free memory
        oldCollection.clear();
        toUpdate.clear();
        // update customer's wishlist
            //get db's wishlist
        ArrayList<Description> oldWishlist = dbRet.retrieveDescriptionsInCustomerWishlist(connection, customer);
            //get wishlists to add
        ArrayList<Description> toAdd = new ArrayList<Description>(customer.getWishList());
        toAdd.removeAll(oldWishlist);
            //update db
        for(Description description:toAdd) {
            dbIns.insertWishlist(connection, description, customer);
        }
            //get wishlists to remove
        oldWishlist.removeAll(customer.getWishList());
            //update db
        for(Description description:oldWishlist) {
            dbDel.removeWishlist(connection, customer, description);
        }
            //free memory
        toAdd.clear();
        oldWishlist.clear();
        connection = dbConn.disconnectFromDB(connection);
    }

    public FakeOffer getTrade(int id) {
        connection = dbConn.connectToDB(connection);
        FakeOffer trade = dbRet.retrieveTrade(connection, id);
        connection = dbConn.disconnectFromDB(connection);
        return trade;
    }

    /**
     * Quick method to get next card ID
     * @return: next card ID
     */
    @Override
    public int getNextCardID() {
        connection = dbConn.connectToDB(connection);
        int n = dbRet.getTableSize(connection, "cards") + 1;
        connection = dbConn.disconnectFromDB(connection);
        return n;
    }

    /**
     * Quick method to get next customer ID
     * @return: next customer ID
     */
    @Override
    public int getNextCustomerID() {
        connection = dbConn.connectToDB(connection);
        int n = dbRet.getTableSize(connection, "customers") + 1;
        connection = dbConn.disconnectFromDB(connection);
        return n;
    }
}
