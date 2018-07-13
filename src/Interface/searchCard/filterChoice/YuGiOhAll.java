package Interface.searchCard.filterChoice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class YuGiOhAll implements Serializable {
    String reference;
    int lev;
    int atk;
    int def;
    String monsterType;
    String type;
    String text;
    private static final long serialVersionUID = 3L;
    public YuGiOhAll(String text,String reference, int lev, int atk, int def, String monsterType, String type) {
        this.text=addCaseNull(text);
        this.reference = addCaseNull(reference);
        this.lev = lev;
        this.atk = atk;
        this.def = def;
        this.monsterType = addCaseNull(monsterType);
        this.type = addCaseNull(type);
    }
    public String addCaseNull(String string){
        if(string.equals(""))
            return null;
        else return string;

    }

    @Override
    public String toString() {
        return "TYPE: "+type+" Reference: "+reference+" ATK:   "+atk+" DEF:  "+def+" Monster_ID: "+monsterType+" LEV: "+lev;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(1);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public String getReference() {
        return reference;
    }

    public int getLev() {
        return lev;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
