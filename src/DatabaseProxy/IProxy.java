package DatabaseProxy;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;

import java.util.ArrayList;

public interface IProxy {

    // cards methods
    public void populateCatalog(CardCatalog cc, String tablename);

    // customers methods
    public ArrayList<String> getAllCustomersNames();

    public Customer retrieveSingleCustomer(String username);

    public Customer retrieveSingleCustomerByID(String id);

    public ArrayList<String> getCustomersByDescription(Description description);

    public void addCustomerToDatabase(Customer customer);

    public void updateCustomer(Customer customer);

    // trades methods
    public ArrayList<Trade> getTradeByUser(String id);

    public void insertTrade(Trade trade);

    public void updateTrade(Trade trade);

    // random methods
    public int getNextCardID();

    public int getNextCustomerID();

    public int getNextTradeID();

}
