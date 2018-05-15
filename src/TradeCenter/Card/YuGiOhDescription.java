package TradeCenter.Card;

import java.io.IOException;

public class YuGiOhDescription extends Description{


    private String reference;
    private int level;
    private int atk;
    private int def;
    private int monster_type_id;
    private int type_id;


    /**
     * Constructor method
     *
     * @param name              name: Descr's name
     * @param text              Descr's text description
     * @param picUrl            : Url to load descr's picture
     * @param reference         The description of the card
     * @param level             Card level
     * @param atk               The attack for monster cards
     * @param def               The defense for monsters cards
     * @param monster_type_id   The type of a monster
     * @param type_id           Type of the card: monster, magical or trap
     */
    public YuGiOhDescription(String name,String text,String picUrl, String reference, int level, int atk, int def, int monster_type_id, int type_id) throws IOException {
        super(name, text, CardType.YUGIOH, picUrl);
        //if(type_id = ) //todo FIX
        this.reference = reference;
        this.level = level;
        this.atk = atk;
        this.def = def;
        this.monster_type_id = monster_type_id;
        this.type_id = type_id;
    }

    public String getReference() {
        return reference;
    }

    public int getLevel() {
        return level;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getMonster_type_id() {
        return monster_type_id;
    }

    public int getType_id() {
        return type_id;
    }

    //todo ADD THE TOSTRING() METHOD
}
