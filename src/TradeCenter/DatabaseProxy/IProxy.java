package TradeCenter.DatabaseProxy;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Customers.Customer;

import java.util.HashMap;

public interface IProxy {

    public void populateCatalog(CardCatalog cc, String tablename);

    public void retrieveCustomers(HashMap<String, Customer> customers);

    public void addCustomerToDatabase(Customer customer);

    public int getNextCardID();

    public int getNextCustomerID();

    // public int updateCustomer();

    // public void retrieveTrades(ArrayList<Trade> trades, boolean doneDeal);

    // public void addTrade(Trade trade);

    // public void updateTrade(Trade trade);

}
