import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;

public class TestMain {
    public static void main(String[] args) {
        TradeCenter tradeCenter = new TradeCenter();
        Customer customer = new Customer("USER-2", "aaa", "Aa123456");
        Description card = tradeCenter.pokemonCatalog.getCard();

        tradeCenter.addCustomer(customer.getUsername(), "Aa123456");
        tradeCenter.addCardtoCustomer(customer,new Card(2, card));


    }
}
