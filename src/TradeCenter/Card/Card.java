package TradeCenter.Card;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Class that represents a Card
 */
public class Card implements Serializable {

    /**
     * @param id: Card's unique id number
     * @param description: Card's description
     */
    private int id;
    private Description description;

    private static final long serialVersionUID = -8262822988735054829L;

    /**
     * Constructor of the cards
     * @param id card's id
     * @param description card's description
     */
    public Card(int id, Description description) {
        this.id = id;
        this.description = description;
    }

    /**
     * Getter of the List Tag
     *
     * @return the list tag
     */
    public HashSet<String> getListTag(){
        return description.getListTag();
    }

    /**
     * Getter of the description
     *
     * @return the description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Override of the equals method
     * @param obj a card
     * @return if two cards are equals to each other
     */
    @Override
    public boolean equals(Object obj) {
        Card c=(Card)obj;
        if(this.id==c.id)
            return true;
        else return false;
    }

    /**
     *Method that make the same card have the same ID
     *
     * @return hashcode
     */
    public int hashCode() {
        int hash = 1;
        hash = hash * this.id;
        return hash;
    }

    /**
     * Getter of the ID
     *
     * @return the card's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Override toString method
     *
     * @return the description of the card
     */
    @Override
    public String toString() {
        return "ID: " + id + " Name: " + description;
    }
}
