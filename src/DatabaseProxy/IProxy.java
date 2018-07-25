package DatabaseProxy;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;

import java.util.ArrayList;

public interface IProxy {

    // cards methods
    void populateCatalog(CardCatalog cc, String tablename);

    // customers methods
    ArrayList<String> getAllCustomersNames();

    Customer retrieveSingleCustomer(String username);

    Customer retrieveSingleCustomerByID(String id);

    ArrayList<String> getCustomersByDescription(Description description);

    void addCustomerToDatabase(Customer customer);

    void updateCustomer(Customer customer);

    // trades methods
    ArrayList<Trade> getTradeByUser(String id);

    void insertTrade(Trade trade);

    void updateTrade(Trade trade);

    // random methods
    int getNextCardID();

    int getNextCustomerID();

    int getNextTradeID();

}
