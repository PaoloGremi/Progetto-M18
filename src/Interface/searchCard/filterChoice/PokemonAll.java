package Interface.searchCard.filterChoice;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PokemonAll implements Serializable {
    String type;
    int hp;
    int lev;
    int weigth;
    String len1;
    String len2;
    String text;
    private static final long serialVersionUID = 1L;

    public PokemonAll(String text,String type, int hp, int lev, int weigth, String len1, String len2) {
        this.text=addCaseNull(text);
        this.hp = hp;
        this.lev = lev;
        this.weigth = weigth;
        this.len1 = addCaseNull(len1);
        this.len2 = addCaseNull(len2);
        this.type = addCaseNull(type);


    }

    @Override
    public String toString() {
        return "TYPE: "+type+" HP: "+hp+" LEVEL:   "+lev+" WEIGTH:  "+weigth+" LEN1: "+len1+" LEN2: "+len2;
    }

    public String getType() {
        return type;
    }

    public int getHp() {
        return hp;
    }

    public int getLev() {
        return lev;
    }

    public int getWeigth() {
        return weigth;
    }

    public String getLen1() {
        return len1;
    }

    public String getText() {
        return text;
    }

    /**
     * If len is not be set, becomes null
     * @param len
     * @return
     */
    public String addCaseNull(String len){
        if(len.equals(""))
            return null;
        else return len;

    }
    public String getLen2() {
        return len2;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(1);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
