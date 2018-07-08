package TradeCenter.DatabaseProxy;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;

import java.util.HashMap;

public interface IProxy {

    // cards methods
    public void populateCatalog(CardCatalog cc, String tablename);

    // customers methods
    public void retrieveCustomers(HashMap<String, Customer> customers);

    public Customer retrieveSingleCustomer(String username);

    public void addCustomerToDatabase(Customer customer);

    public void updateCustomer(Customer customer);

    // trades methods
    public Trade getTrade(int id);

    public void InsertTrade(Trade trade);

    // public void updateTrade(Trade trade);

    // random methods
    public int getNextCardID();

    public int getNextCustomerID();

    public int getNextTradeID();

}
