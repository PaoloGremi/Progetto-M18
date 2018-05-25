package TradeCenter.Card;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PokemonDescription extends Description {


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
     * @param type      Pokemons type
     * @param hp        Pokemons hit points
     * @param weight    Pokemons weight
     * @param length    Pokemons length
     * @param level     Pokemons level
     */
    public PokemonDescription(String name, String text, BufferedImage pic, int cardID, String type, int hp, int weight, String length, int level) throws IOException {
        super(name, text, CardType.POKEMON, pic);
        this.cardID = cardID;
        this.type = type;
        this.hp = hp;
        this.weight = weight;
        this.length = length;
        this.level = level;
    }


    public int getCardID() {
        return cardID;
    }

    public String getType() {
        return type;
    }

    public int getHp() {
        return hp;
    }

    public int getWeigth() {
        return weight;
    }

    public String getLength() {
        return length;
    }

    public int getLevel() {
        return level;
    }
}
