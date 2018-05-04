package TradeCenter.Card;

import java.util.HashSet;

public class Card {

    /**
     * @param id: Card's unique id number
     * @param description: Card's description
     */

    private int id;
    private Description description;

    public Card(int id, Description description) {
        this.id = id;
        this.description = description;
    }

    public HashSet<String> getListTag(){
        return description.getListTag();
    }
    public Description getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        Card c=(Card)obj;
        boolean condition=(this.id==c.id);
        if(condition)
            return true;
        else return false;
    }

    @Override
    public String toString() {
        return "ID: "+id+""+description;
    }
}
