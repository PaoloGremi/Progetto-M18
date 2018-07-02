package TradeCenter.DatabaseProxy;

import TradeCenter.Card.Card;
import TradeCenter.Card.CardCatalog;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Proxy for database management
 * @author Roberto Gallotta
 */
public class DBProxy implements IProxy{

    private Connection connection;
    private DBAtomicRetriever dbAtom = new DBAtomicRetriever();
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
        connection = dbConn.connectToDB(connection, "CARDS");
        int size;
        switch(tablename) {
            case "pokemon_card":
                size = dbAtom.getTableSize(connection, tablename);
                for(int i = 0; i<size; i++) {
                    cc.addDescription(dbAtom.retrieveSinglePokemonDescription(connection, i));
                }
                break;
            case "yugioh_card":
                size = dbAtom.getTableSize(connection, tablename);
                for(int i = 0; i<size; i++) {
                    cc.addDescription(dbAtom.retrieveSingleYugiohDescription(connection, i));
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
        connection = dbConn.connectToDB(connection, "CARDS");
        int n = dbAtom.getTableSize(connection, "customers");
        for(int i=1; i<= n; i++) {
            Customer customer = dbAtom.retrieveSingleCustomerByUserID(connection, i);
            // add card collection
            for(Card card:dbAtom.retrieveCardsInCustomerCollection(connection, customer)) {
                customer.addCard(card);
            }
            // add card wishlist
            for(Description description:dbAtom.retrieveDescriptionsInCustomerWishlist(connection, customer)) {
                customer.addCardToWishList(description);
            }
            // add customer
            customers.put(customer.getId(), customer);
        }
        connection = dbConn.disconnectFromDB(connection);
    }

    /**
     * Adds a customer in the database
     * @param customer: customer to be added
     */
    @Override
    public void addCustomerToDatabase(Customer customer) {
        connection = dbConn.connectToDB(connection, "CARDS");
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
        connection = dbConn.connectToDB(connection, "CARDS");
        // update customer's cards
            //get db's customer's collection
        ArrayList<Card> oldCollection = dbAtom.retrieveCardsInCustomerCollection(connection, customer);
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
        ArrayList<Description> oldWishlist = dbAtom.retrieveDescriptionsInCustomerWishlist(connection, customer);
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

    /**
     * Quick method to get next card ID
     * @return: next card ID
     */
    public int getNextCardID() {
        connection = dbConn.connectToDB(connection, "CARDS");
        int n = dbAtom.getTableSize(connection, "cards") + 1;
        connection = dbConn.disconnectFromDB(connection);
        return n;
    }

    /**
     * Quick method to get next customer ID
     * @return: next customer ID
     */
    @Override
    public int getNextCustomerID() {
        connection = dbConn.connectToDB(connection, "CARDS");
        int n = dbAtom.getTableSize(connection, "customers") + 1;
        connection = dbConn.disconnectFromDB(connection);
        return n;
    }
}
