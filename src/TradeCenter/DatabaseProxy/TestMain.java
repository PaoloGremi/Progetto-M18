package TradeCenter.DatabaseProxy;

import TradeCenter.Card.*;
import TradeCenter.Customers.Customer;
import TradeCenter.DatabaseProxy.DBAtomicRetriever;
import TradeCenter.DatabaseProxy.DBProxy;

import java.util.ArrayList;
import java.util.HashMap;

public class TestMain {
    public static void main(String[] args) {

        /**
         * full customer retrieval test
         * expected result:
         *
         * USER-1 - TeoGore
         * COLLECTION:
         * ID: 1 Name: Arcanine, ID: 2 Name: Metapod, ID: 3 Name: Dark Hole,
         * WISHLIST:
         * Alakazam
         * Alexandrite Dragon
         *
         * USER-2 - CiccioGamer89
         * COLLECTION:
         * ID: 4 Name: Venusaur
         * WISHLIST:
         * Dark Hole
         */

        DBProxy db = new DBProxy();

        HashMap<String, Customer> customers = new HashMap<>();
        db.retrieveCustomers(customers);

        for(Customer customer : customers.values()) {
            System.out.println("\n"+customer.getId()+" - "+customer.getUsername());
            System.out.println("COLLECTION:");
            System.out.println(customer.getCollection().toString());
            System.out.println("WISHLIST:");
            for(Description des:customer.getWishList()) {
                System.out.println(des);
            }
        }
    }
}
