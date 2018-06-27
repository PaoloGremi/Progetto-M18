package TradeCenter.Card;

import java.util.HashSet;
import java.util.Iterator;

public class CardCatalog {

    private HashSet<Description> catalog = new HashSet<>();

    /**
     * Add a description to the catalog
     * @param description the card itself
     */
    public void addDescription(Description description){
        this.catalog.add(description);
    }
}
