package TradeCentre.card;

import java.util.HashSet;

public class CardCatalog {

    private HashSet<Description> catalog;

    // This method return true if the TradeCentre.card descrption is added to the TradeCentre.card catalog for the first time
    public boolean addDescription(Description description){
        if (this.catalog.add(description)){
            return true;
        }
        else {
            return false;
        }
    }

    // This method return true if the TradeCentre.card description was in the catalog and is deleted from that
    public boolean removeDescription(Description description){
        if (this.catalog.remove(description)){
            return true;
        }
        else {
            return false;
        }
    }
}
