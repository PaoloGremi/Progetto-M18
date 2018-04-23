package marketPlace;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeCenter {

    //private DescriptionCatalog catalog;        // manca ancora la classe
    private HashMap<String, Customer> customers;
    private ArrayList<Trade> activeTrades;

    public TradeCenter() {                    //puo servire passare il catalogo??
        //this.catalog = catalog;
        this.customers = new HashMap<String, Customer>();
        this.activeTrades = new ArrayList<Trade>();
    }

    boolean addCustomer(Customer customer) {

    }

    boolean removeCustomer(Customer customer) {

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
