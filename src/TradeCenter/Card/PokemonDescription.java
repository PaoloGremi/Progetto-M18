package TradeCenter.Card;

import java.awt.image.BufferedImage;

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
     *
     * @return the card id
     */
    public int getCardID() {
        return cardID;
    }

    /**
     *
     * @return the pokemon type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return the pokemon health
     */
    public int getHp() {
        return hp;
    }

    /**
     *
     * @return the pokemon weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     *
     * @return the pokemon lenght
     */
    public String getLength() {
        return length;
    }

    /**
     *
     * @return the pokemon level
     */
    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
