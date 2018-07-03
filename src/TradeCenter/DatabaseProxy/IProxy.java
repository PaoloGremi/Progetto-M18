package TradeCenter.DatabaseProxy;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Customers.Customer;

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
    // public void retrieveTrades(ArrayList<Trade> trades, boolean doneDeal);

    // public void addTrade(Trade trade);

    // public void updateTrade(Trade trade);

    // random methods
    public int getNextCardID();

    public int getNextCustomerID();

}
