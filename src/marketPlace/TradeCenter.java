package marketPlace;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeCenter {

    //private DescriptionCatalog catalog;        // manca ancora la classe
    private HashMap<Integer, Customer> customers;       //vedere se serve cambiare da int a long
    private ArrayList<Trade> activeTrades;

    public TradeCenter() {                    //puo servire passare il catalogo??
        //this.catalog = catalog;
        this.customers = new HashMap<Integer, Customer>();
        this.activeTrades = new ArrayList<Trade>();
    }

    boolean addCustomer(String username, String password) {
        customers.put(customers.size(), new Customer(customers.size(),username,password));
        //mettere al posto dell'id la stringa fatta come dice roby univoca
    }

    boolean removeCustomer(int id) {
        customers.remove(id);
    }

    Customer searchCustomer(String user){

    }

    boolean addDescription(Description description){

    }

    Card[] searchByString(String search){

    }

    boolean switchCards(Trade trade){

    }

    boolean checkDoneDeals(Trade trade){        //meglio mettere altro metodo che fa check per ognuno e ritornare un array?

    }


}
