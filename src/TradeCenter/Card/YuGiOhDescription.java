package TradeCenter.Card;

import java.awt.image.BufferedImage;

public class YuGiOhDescription extends Description{


    private String cardID;
    private int level;
    private int atk;
    private int def;
    private String monster_type_id;
    private String card_type_id;


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
    public YuGiOhDescription(String name, String text, BufferedImage pic, String cardID, int level, int atk, int def, String monster_type_id, String card_type_id) {
        super(name, text, CardType.YUGIOH, pic);
        this.cardID = cardID;
        this.level = level;     //if not a monster, everthing already handled while taking the object
        this.atk = atk;
        this.def = def;
        this.monster_type_id = monster_type_id;
        this.card_type_id = card_type_id;
    }

    /**
     *
     * @return the description of the card
     */
    public String getCardID() {
        return cardID;
    }

    /**
     *
     * @return the stars value of the yugioh card
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return the attack value of the yugioh card
     */
    public int getAtk() {
        return atk;
    }

    /**
     *
     * @return the defense value of the yugioh card
     */
    public int getDef() {
        return def;
    }

    /**
     *
     * @return the type of monster of the yugioh card
     */
    public String getMonster_type_id() {
        return monster_type_id;
    }

    /**
     *
     * @return if the card is a monster, a magical or a trap
     */
    public String getCard_type_id() {
        return card_type_id;
    }

    /**
     *
     * @return the card name
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
