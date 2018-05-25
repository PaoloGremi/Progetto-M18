package TradeCenter.Card;

import java.util.HashSet;

public class CardCatalog {

    private HashSet<Description> catalog = new HashSet<>();

    // This method return true if the TradeCenter.Card descrption is added to the TradeCenter.Card catalog for the first time
    public void addDescription(Description description){
        this.catalog.add(description);
    }

    // This method return true if the TradeCenter.Card description was in the catalog and is deleted from that
    public void removeDescription(Description description){
        this.catalog.remove(description);
    }
}
