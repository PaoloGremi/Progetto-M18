package TradeCenter.Card;

import java.awt.image.BufferedImage;

/**
 * Calss that represents the YuGiOh collection
 */

public class YuGiOhDescription extends Description{


    private int cardID;
    private int level;
    private int atk;
    private int def;
    private String reference;
    private String monster_type_id;
    private String card_type_id;

    private static final long serialVersionUID = 8794060548502010738L;
    /**
     * Constructor method
     *
     * @param name              card name
     * @param text              card description (effects)
     * @param pic               the image
     * @param cardID                card's id
     * @param level             Card level (the stars)
     * @param atk               The attack for monster cards
     * @param def               The defense for monsters cards
     * @param monster_type_id   The type of a monster
     * @param card_type_id           Type of the card: monster, magical or trap
     */
    public YuGiOhDescription(String name, String text, BufferedImage pic, int cardID, String reference, int level, int atk, int def, String monster_type_id, String card_type_id) {
        super(name, text, CardType.YUGIOH, pic);
        this.cardID = cardID;
        this.reference = reference;
        this.level = level;     //if not a monster, everthing already handled while taking the object
        this.atk = atk;
        this.def = def;
        this.monster_type_id = monster_type_id;
        this.card_type_id = card_type_id;
    }

    /**
     *Getter of the card ID
     *
     * @return the description of the card
     */
    public int getCardID() {
        return cardID;
    }

    /**
     *Getter of the level
     *
     * @return the stars value of the yugioh card
     */
    public int getLevel() {
        return level;
    }

    /**
     *Getter of he attack
     *
     * @return the attack value of the yugioh card
     */
    public int getAtk() {
        return atk;
    }

    /**
     *Getter of the defense
     *
     * @return the defense value of the yugioh card
     */
    public int getDef() {
        return def;
    }

    /**
     *Getter of the monster type
     *
     * @return the type of monster of the yugioh card
     */
    public String getMonster_type_id() {
        return monster_type_id;
    }

    /**
     *Getter of the card type
     *
     * @return if the card is a monster, a magical or a trap
     */
    public String getCard_type_id() {
        return card_type_id;
    }

    /**
     * Getter of the reference
     * @return: reference (a code of a card)
     */
    public String getReference() {
        return reference;
    }

    /**
     *Override toString method
     *
     * @return the card name
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
