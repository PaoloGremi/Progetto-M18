package TradeCenter.Card;

import java.awt.image.BufferedImage;

/**
 * Class that rapresent the Pokemon collection
 */

public class PokemonDescription extends Description {

    private static final long serialVersionUID = 411009573434955301L;

    private int cardID;
    private String type;
    private int hp;
    private int weight;
    private String length;
    private int level;


    /**
     * Constructor method
     *
     * @param name      name: Descr's name
     * @param text      Descr's text description
     * @param pic       Descr's picture
     * @param cardID    Identificator for Pokemon cards
     * @param type      Pokemon's type
     * @param hp        Pokemon's hit points
     * @param weight    Pokemon's weight
     * @param length    Pokemon's length
     * @param level     Pokemon's level
     */
    public PokemonDescription(String name, String text, BufferedImage pic, int cardID, String type, int hp, int weight, String length, int level) {
        super(name, text, CardType.POKEMON, pic);
        this.cardID = cardID;
        this.type = type;
        this.hp = hp;
        this.weight = weight;
        this.length = length;
        this.level = level;
    }

    /**
     *Getter of the card ID
     *
     * @return the card id
     */
    public int getCardID() {
        return cardID;
    }

    /**
     *Getter of the pokemon type
     *
     * @return the pokemon type
     */
    public String getType() {
        return type;
    }

    /**
     *Getter of the pokemon health
     *
     * @return the pokemon health
     */
    public int getHp() {
        return hp;
    }

    /**
     *Getter of the pokeon weight
     *
     * @return the pokemon weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     *Getter of the pokemon lenght
     *
     * @return the pokemon lenght
     */
    public String getLength() {
        return length;
    }

    /**
     *Getter of the pokemon level
     *
     * @return the pokemon level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Overrive of toString method
     *
     * @return the description of a pokemon
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
