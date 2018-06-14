package TradeCenter.Card;

import java.io.Serializable;
import java.util.HashSet;

public class Card implements Serializable {

    /**
     * @param id: Card's unique id number
     * @param description: Card's description
     */
    private int id;
    private Description description;

    private static final long serialVersionUID = -8262822988735054829L;

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
        if(this.id==c.id)
            return true;
        else return false;
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + description;
    }
}
