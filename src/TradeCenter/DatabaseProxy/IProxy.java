package TradeCenter.DatabaseProxy;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Customers.Customer;
import TradeCenter.Trades.Trade;

public interface IProxy {

    // cards methods
    public void populateCatalog(CardCatalog cc, String tablename);

    // customers methods
    //public int retrieveCustomers(HashMap<String, Customer> customers);

    public Customer retrieveSingleCustomer(String username);

    public void addCustomerToDatabase(Customer customer);

    public void updateCustomer(Customer customer);

    // trades methods
    public Trade getTrade(int id);

    public void insertTrade(Trade trade);

    public void updateTrade(Trade trade);

    // random methods
    public int getNextCardID();

    public int getNextCustomerID();

    public int getNextTradeID();

}
