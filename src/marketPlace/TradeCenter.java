package marketPlace;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeCenter {

    //private DescriptionCatalog catalog;               // manca ancora la classe
    private HashMap<Integer, Customer> customers;       //vedere se serve cambiare da int a long
    private ArrayList<Trade> activeTrades;

    public TradeCenter() {                               //puo servire passare il catalogo??
        //this.catalog = catalog;
        this.customers = new HashMap<Integer, Customer>();
        this.activeTrades = new ArrayList<Trade>();
    }

    void addCustomer(String username, String password) {
        customers.put(customers.size(), new Customer(customers.size(),username,password));
        //mettere al posto dell'id la stringa fatta come dice roby univoca
    }

    void removeCustomer(int id) {
        customers.remove(id);                           //capire come passare id
    }

    Customer searchCustomer(String username){
        for(Integer key : customers.keySet()){
            if((customers.get(key)).getUsername().equals(username)){        //assunto USERNAME UNIVOCO
                return customers.get(key);
            }
        }
        return null;
    }

    void addDescription(Description description){
        //vuoto fino a che non viene creata la classe catalogo
    }

    HashMap<Customer, Card[]> searchByString(String searchString){
        HashMap<Customer, Card[]> tmp = new HashMap<>();
        for(int key : customers.keySet()){
            tmp.put(customers.get(key), customers.get(key).searchByString(searchString));
        }
        return tmp;
    }
/*FINIRE
    boolean switchCards(Trade trade){
            //ricontrollare trade prima di fare questo metodo e il successivo
    }

    boolean checkDoneDeals(Trade trade){        //meglio mettere altro metodo che fa check per ognuno e ritornare un array?
            //capire cosa vuole fare roberto con questo metodo (mettiamo lista di trade finiti?)
    }

*/
}
