package TradeCenter.DatabaseProxy;

import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;

/**
 * Singleton
 */
public class DBSearchDescription {
    static DBSearchDescription instance;

    public static DBSearchDescription getInstance(){
        if(instance==null)
            instance=new DBSearchDescription();
        return instance;
    }

    /**
     * @param connection: Database connection
     * @param typeInput:  Card Type
     * @param hpInput:    HP
     * @param lev:Level
     * @param weigth:     Weight
     * @param len1:       Length1
     * @param len2:       Length2
     * @return Pokemon's descriptions founded
     */
    public HashSet<PokemonDescription> getSearchedDescrPokemon(Connection connection, String typeInput, int hpInput, int lev, int weigth, String len1, String len2) {
        //ckeck on lenght
        String lenghtComplete = checkLength(len1, len2);

        HashSet<PokemonDescription> descrFounded = new HashSet<>();
        System.err.println("Searching a Pokemon description...");
        PreparedStatement ps;
        ResultSet rs;
        try {
            initPokemonView(connection, "InitPokemonView");
            //String attribute
            whereString(connection, "InitPokemonView", "PoView1", "Type", typeInput);
            whereString(connection, "PoView1", "PoView2", "Length", lenghtComplete);
            //Int attribute
            whereInt(connection, "PoView2", "PoView3", "HP", hpInput,30);
            whereInt(connection, "PoView3", "PoView4", "Weigth", weigth,50);
            whereInt(connection, "PoView4", "PoFinalView", "Level", lev,15);

            int[] listID=getIDDescriptionFromView(connection,"PoFinalView","pokemon_description_id");
            descrFounded=retrievePokemonDescriptionFromID(connection,listID);

            dropView(connection, "InitPokemonView");
            dropView(connection, "PoView1");
            dropView(connection, "PoView2");
            dropView(connection, "PoView3");
            dropView(connection, "PoView4");
            dropView(connection, "PoFinalView");
            return descrFounded;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //finally drop view;

        return null;
    }

    /**
     * @param connection
     * @param reference
     * @param lev
     * @param atk
     * @param def
     * @param monsterID
     * @param typeID
     * @return
     */
    public HashSet<YuGiOhDescription> getSearchedDescrYuGiOh(Connection connection, String reference, int lev, int atk, int def, String monsterID, String typeID) {
        HashSet<YuGiOhDescription> descrFounded = new HashSet<>();
        System.err.println("Searching  YuGiOh descriptions...");
        PreparedStatement ps;
        ResultSet rs;
        try {
            initYuGiOhView(connection, "InitYuGiOhView");
            //String attribute
            whereString(connection, "InitYuGiOhView", "YuView1", "Reference", reference);
            //Int attribute
            whereInt(connection, "YuView1", "YuView2", "Level", lev,2);
            whereInt(connection, "YuView2", "YuView3", "Atk", atk,500);
            whereInt(connection, "YuView3", "YuView4", "Def", def,500);
            //String attribute
            whereString(connection, "YuView4", "YuView5", "Monster_Type_ID", monsterID);
            whereString(connection, "YuView5", "YuFinalView", "Type_ID", typeID);

            int[] listID=getIDDescriptionFromView(connection,"YuFinalView","yugioh_description_id");
            descrFounded=retrieveYugiohDescriptionFromID(connection,listID);
            dropView(connection, "InitYuGiOhView");
            dropView(connection, "YuView1");
            dropView(connection, "YuView2");
            dropView(connection, "YuView3");
            dropView(connection, "YuView4");
            dropView(connection, "YuView5");
            dropView(connection, "YuFinalView");
            return descrFounded;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //finally drop view;

        return null;
    }

    /**
     * Retrieve descriptions from final view filtered
     *
     * @param connection:   connection to db
     * @param lastViewName: last filtered view
     * @return HashSet of description
     * @throws SQLException
     * @throws IOException
     */
    private int[] getIDDescriptionFromView(Connection connection, String lastViewName,String idAttribute) throws SQLException, IOException {

        Statement stmt;
        ResultSet rs;
        String quey = "select *\n" + "from " + lastViewName + ";";
        stmt = connection.createStatement();
        rs = stmt.executeQuery(quey);

        int length = getViewSize(connection, lastViewName);
        int[] listIDfounded = new int[length];
        int i = 0;
        while (rs.next()) {
            int idCurrent = rs.getInt(idAttribute);
            listIDfounded[i] = idCurrent;
            i++;
        }
        return listIDfounded;
    }

    /**
     * From a vector of ID of PokemonDescription in DB, returns PokemonDescription
     * It uses DBAtomicRetriever
     *
     * @param connection:connection to db
     * @param vectID: Vector of ID of Pokemon Card which is searching
     * @return PokemonDescription matched vectID
     */
    private HashSet<PokemonDescription> retrievePokemonDescriptionFromID(Connection connection,int[] vectID){
        System.err.println("Retrieving " + " Pokemon description");
        HashSet<PokemonDescription> descrCreated = new HashSet<>();
        DBAtomicRetriever DBret = new DBAtomicRetriever();
        for (int i = 0; i < vectID.length; i++) {
            descrCreated.add(DBret.retrieveSinglePokemonDescription(connection, vectID[i]));
        }
        return descrCreated;
    }

    /**
     *
     *  From a vector of ID of YugiohDescription in DB, returns YuGiOhDescription
     *  It uses DBAtomicRetriever
     * @param connection: connection to DB
     * @param vectID: Vector of ID of YuGiOh Card which is searching
     * @return
     */

    private HashSet<YuGiOhDescription> retrieveYugiohDescriptionFromID(Connection connection,int[] vectID){
        HashSet<YuGiOhDescription> descrCreated = new HashSet<>();
        DBAtomicRetriever DBret = new DBAtomicRetriever();
        for (int i = 0; i < vectID.length; i++) {
            descrCreated.add(DBret.retrieveSingleYugiohDescription(connection, vectID[i]));
        }
        return descrCreated;
    }


    /**
     * Size of a View
     *
     * @param connection:Connection to DB
     * @param viewname: View
     * @return size of View
     */
    int getViewSize(Connection connection, String viewname) {
        int size = 0;
        try {
            PreparedStatement ps = null;
            String query="SELECT COUNT(*) FROM cards."+viewname+";";
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                size = rs.getInt(1);
                return size;
            }
        }catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * Check on Pokemon's length parameters
     *
     * @param len1:first part of length
     * @param len2:second part of length
     * @return union if not null, null if some of them are null
     */
    private String checkLength(String len1,String len2){
        String lenghtComplete;
        if(!(len1==null && len2==null))
            lenghtComplete=len1+"’"+len2+"’’";
        else lenghtComplete=null;
        return lenghtComplete;
    }

    /**
     * Initialize a View in DB with all Pokemon or YuGiOh Description
     *
     * @param connection: Connection to DB
     * @param nameView: Name of View to create
     */
    private void initPokemonView(Connection connection,String nameView) throws SQLException {
        Statement stmt ;
        String query = "create view "+nameView+"(pokemon_description_id,Name,Type,Hp,Description,Length,Weigth,Level,Picture) as " +
                "select *" +
                "    from cards.pokemon_card;";

            stmt = connection.createStatement();
            int rs = stmt.executeUpdate(query);
    }
    private void initYuGiOhView(Connection connection,String nameView) throws SQLException {
        Statement stmt ;
        String query = "create view "+nameView+"(yugioh_description_id,Name,Description,Reference,Level,Atk,Def,Monster_Type_ID,Type_ID) as SELECT yugioh_description_id,Name,Description,Reference,Level,Atk,Def,MonsterTypeName,CardTypeName\n" +
                "FROM yugioh_card AS a LEFT JOIN (SELECT Monster_Type_ID, Name AS MonsterTypeName FROM monster_Type) AS b ON a.Monster_Type_ID = b.Monster_Type_ID LEFT JOIN (SELECT Type_ID, Name AS CardTypeName FROM card_Type) AS c ON a.Type_ID = c.Type_ID;";

        stmt = connection.createStatement();
        int rs = stmt.executeUpdate(query);
    }


    /**
     * Create a View from an other view with where clause and a string attribute
     *
     * @param connection: Conncection to DB
     * @param NameViewPrevious: Name of the previous view
     * @param NameViewNext: Name of the nex view created
     * @param attribute: attribute for where clause
     * @param value: value of attribute
     * @throws SQLException
     */

    private void whereString(Connection connection,String NameViewPrevious,String NameViewNext,String attribute,String value) throws  SQLException{
        Statement stmt;
        int rs;
        String query;
        if(!(value==(null))) {
             query = "create view " + NameViewNext + " as " +
                    " select * " +
                    "from " + NameViewPrevious + " where " + attribute + "='" + value + "';";
        }
        else {
            query="create view " + NameViewNext + " as " +
                    " select *" +
                    "from " + NameViewPrevious+";";
        }
        stmt=connection.createStatement();
        rs=stmt.executeUpdate(query);
    }


    /**
     * Create a View from an other view with where clause and int as attribute
     *
     * @param connection: Conncection to DB
     * @param NameViewPrevious: Name of the previous view
     * @param NameViewNext: Name of the nex view created
     * @param attribute: attribute for where clause
     * @param value: value of attribute
     * @throws SQLException
     */

    private void whereInt(Connection connection,String NameViewPrevious,String NameViewNext,String attribute,int value, int range) throws  SQLException{
        int rangeMin=value-range;
        int rangeMax=value+range;
        if(rangeMin<0) //forse non serve
            rangeMin=0;

        Statement stmt;
        int rs;
        String query;
        if(!(value==0)) {
            query = "create view " + NameViewNext + " as " +
                    "select * " +
                    "from " + NameViewPrevious + " where " + attribute + "<=" + rangeMax + " and "+attribute+">="+rangeMin+";";
        }
        else {
            query="create view " + NameViewNext + " as " +
                    "select *" +
                    "from " + NameViewPrevious+";";
        }
        stmt=connection.createStatement();
        rs=stmt.executeUpdate(query);
    }

    /**
     * Drop a View
     *
     * @param connection: Connection to DB
     * @param NameView: Name of View to be dropped
     *
     */
    private void dropView(Connection connection,String NameView) throws SQLException {
        Statement stmt ;
        String query = "DROP VIEW "+NameView+";";

            stmt = connection.createStatement();
            int rs = stmt.executeUpdate(query);
    }
}