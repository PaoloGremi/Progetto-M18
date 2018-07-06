package Interface.searchCard.filterChoice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class YuGiOhAll {
    String reference;
    int lev;
    int atk;
    int def;
    int monsterID;
    int typeID;
    private static final long serialVersionUID = 2L;
    public YuGiOhAll(String reference, int lev, int atk, int def, int monsterID, int typeID) {
        reference = addCaseNull(reference);
        this.lev = lev;
        this.atk = atk;
        this.def = def;
        this.monsterID = monsterID;
        this.typeID = typeID;
    }
    public String addCaseNull(String string){
        if(string.equals(""))
            return null;
        else return string;

    }

    @Override
    public String toString() {
        return "TYPE: "+typeID+" Reference: "+reference+" ATK:   "+atk+" DEF:  "+def+" Monster_ID: "+monsterID+" LEV: "+lev;
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

    public int getMonsterID() {
        return monsterID;
    }

    public int getTypeID() {
        return typeID;
    }
}
