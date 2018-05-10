package Test.CustomerTests;

import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;

public class AddCustomerTest {
    public static void main(String[] args) {
        TradeCenter tradeCenter = new TradeCenter();
        tradeCenter.addCustomer("Gore","1234a");

        //caso favorevole - utente trovato
        Customer customer = tradeCenter.searchCustomer("Gore");
        String id = customer.getId();
        System.out.println("This is my ID: " + id);



        //caso sfavorevole - utente non trovato
        Customer customer1 = tradeCenter.searchCustomer("aaa");     //todo vedere perche printa tutto e non solo il messaggio

    }
}
