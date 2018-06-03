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
    private static final long serialVersionUID = 1L;

    public PokemonAll(String type, int hp, int lev, int weigth, String len1, String len2) {
        this.type = type;
        this.hp = hp;
        this.lev = lev;
        this.weigth = weigth;
        this.len1 = len1;
        this.len2 = len2;
        //todo controllo se 0 diventar null
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
