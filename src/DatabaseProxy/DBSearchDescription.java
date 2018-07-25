package DatabaseProxy;

import TradeCenter.Card.Description;

import java.sql.*;
import java.util.HashSet;

class DBSearchDescription {


    /**
     * Search the card, with a string
     *
     * @param connection: Database connection
     * @param s: string for searching
     * @return card's description found
     */
    HashSet<Description> getDescrByString(Connection connection, String s) {
        HashSet<Description> descriptions=new HashSet<>();
        System.err.println("[DBSearchDescription] - Searching descriptions by Name + " + s + " ...");
        try{
            //PokemonDescription Search
            HashSet<Description> pokemonDescr;
            initPokemonView(connection, "InitPokemonViewString");
            whereLikeString(connection,"InitPokemonViewString","FinalViewPoString","Name",s);

            int[] listID1=getIDDescriptionFromView(connection,"FinalViewPoString","pokemon_description_id");
            pokemonDescr=retrievePokemonDescriptionFromID(connection,listID1);

            dropView(connection,"InitPokemonViewString");
            dropView(connection,"FinalViewPoString");

            //YuGiOhDescription Search
            HashSet<Description> yugiohDescr;
            initYuGiOhView(connection, "InitYugiohViewString");
            whereLikeString(connection,"InitYugiohViewString","FinalViewYuString","Name",s);

            int[] listID2=getIDDescriptionFromView(connection,"FinalViewYuString","yugioh_description_id");
            yugiohDescr=retrieveYugiohDescriptionFromID(connection,listID2);

            dropView(connection,"InitYugiohViewString");
            dropView(connection,"FinalViewYuString");

            descriptions.addAll(pokemonDescr);
            descriptions.addAll(yugiohDescr);
            System.err.println("[DBSearchDescription] - Search completed.");
            return  descriptions;

        } catch (SQLException e) {
            System.err.println("[DBSearchDescription] - Exception " + e + " encounterd in method getDescrByString.");
        }
        return null;
    }

    /**
     * Search pokemon cards, with parameters
     *
     * @param connection:   Database connection
     * @param typeInput:    Card Type
     * @param hpInput:      HP
     * @param lev:          Level
     * @param weight:       Weight
     * @param len1:         Length1
     * @param len2:         Length2
     * @return Pokemon's descriptions found
     */
    HashSet<Description> getSearchedDescrPokemon(Connection connection, String text, String typeInput, int hpInput, int lev, int weight, String len1, String len2) {
        //ckeck on length
        String lengthComplete = checkLength(len1, len2);

        HashSet<Description> descrFound;
        System.err.println("[DBSearchDescription] - Searching a Pokémon description...");
        try {
            initPokemonView(connection, "InitPokemonView");
            //String attribute
            whereLikeString(connection, "InitPokemonView", "PoLikeView", "Name", text);
            whereString(connection, "PoLikeView", "PoView1", "Type", typeInput);
            whereString(connection, "PoView1", "PoView2", "Length", lengthComplete);
            //Int attribute
            whereInt(connection, "PoView2", "PoView3", "HP", hpInput, 10);
            whereInt(connection, "PoView3", "PoView4", "Weigth", weight, 50);
            whereInt(connection, "PoView4", "PoFinalView", "Level", lev, 15);

            int[] listID=getIDDescriptionFromView(connection,"PoFinalView", "pokemon_description_id");
            descrFound=retrievePokemonDescriptionFromID(connection,listID);

            dropView(connection, "InitPokemonView");
            dropView(connection, "PoLikeView");
            dropView(connection, "PoView1");
            dropView(connection, "PoView2");
            dropView(connection, "PoView3");
            dropView(connection, "PoView4");
            dropView(connection, "PoFinalView");
            System.err.println("[DBSearchDescription] - Pokémon description search completed.");
            return descrFound;
        } catch (SQLException e) {
            System.err.println("[DBSearchDescription] - Exception " + e + " encounterd in method getSearchedDescrPokemon.");
        }
        //finally drop view;
        return null;
    }

    /**
     * Search Yugioh cards, with parameters
     *
     * @param connection: database connection
     * @param reference: unique card code
     * @param lev: level
     * @param atk: attack points
     * @param def: defence points
     * @param monsterID: type of monster
     * @param typeID: type of card
     * @return descriptions found that match parameters
     */
    HashSet<Description> getSearchedDescrYuGiOh(Connection connection, String text, String reference, int lev, int atk, int def, String monsterID, String typeID) {
        HashSet<Description> descrFound;
        System.err.println("[DBSearchDescription] - Searching a Yu-Gi-Oh description...");
        try {
            initYuGiOhView(connection, "InitYuGiOhView");
            //String attribute
            whereLikeString(connection, "InitYuGiOhView", "YuLikeView", "Name",text);
            whereString(connection, "YuLikeView", "YuView1", "Reference", reference);
            //Int attribute
            whereInt(connection, "YuView1", "YuView2", "Level", lev, 2);
            whereInt(connection, "YuView2", "YuView3", "Atk", atk, 500);
            whereInt(connection, "YuView3", "YuView4", "Def", def, 500);
            //String attribute
            whereString(connection, "YuView4", "YuView5", "Monster_Type_ID", monsterID);
            whereString(connection, "YuView5", "YuFinalView", "Type_ID", typeID);

            int[] listID = getIDDescriptionFromView(connection,"YuFinalView", "yugioh_description_id");
            descrFound = retrieveYugiohDescriptionFromID(connection,listID);
            dropView(connection, "InitYuGiOhView");
            dropView(connection, "YuLikeView");
            dropView(connection, "YuView1");
            dropView(connection, "YuView2");
            dropView(connection, "YuView3");
            dropView(connection, "YuView4");
            dropView(connection, "YuView5");
            dropView(connection, "YuFinalView");
            System.err.println("[DBSearchDescription] - Yu-Gi_Oh description search completed.");
            return descrFound;
        } catch (SQLException e) {
            System.err.println("[DBSearchDescription] - Exception " + e + " encounterd in method getSearchedDescrYuGiOh.");
        }
        //finally drop view;
        return null;
    }

    /**
     * Retrieve descriptions from final view filtered
     *
     * @param connection: database connection
     * @param lastViewName: last filtered view
     * @return HashSet of description
     * @throws SQLException: SQL exception
     */
    private int[] getIDDescriptionFromView(Connection connection, String lastViewName, String idAttribute) throws SQLException{

        Statement stmt = connection.createStatement();
        String query = "SELECT * FROM " + lastViewName + ";";
        ResultSet rs = stmt.executeQuery(query);

        int length = getViewSize(connection, lastViewName);
        int[] listIDfound = new int[length];
        for(int i = 0; rs.next(); i++){
            listIDfound[i] = rs.getInt(idAttribute);
        }
        return listIDfound;
    }

    /**
     * From a vector of ID of PokemonDescription in DB, returns PokemonDescription
     * It uses DBAtomicRetriever
     *
     * @param connection: database connection
     * @param vectID: Vector of ID of Pokemon Card which is searching
     * @return PokemonDescription matching vectID
     */
    private HashSet<Description> retrievePokemonDescriptionFromID(Connection connection,int[] vectID){
        System.err.println("[DBSearchDescription] - Retrieving Pokémon description...");
        HashSet<Description> descrCreated = new HashSet<>();
        DBAtomicRetriever DBret = new DBAtomicRetriever();
        for (int i = 0; i < vectID.length; i++) {
            descrCreated.add(DBret.retrieveSinglePokemonDescription(connection, vectID[i]));
        }
        System.err.println("[DBSearchDescription] - Pokémon description retrieved.");
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
    private HashSet<Description> retrieveYugiohDescriptionFromID(Connection connection,int[] vectID){
        System.err.println("[DBSearchDescription] - Retrieving a Yu-Gi-Oh description...");
        HashSet<Description> descrCreated = new HashSet<>();
        DBAtomicRetriever DBret = new DBAtomicRetriever();
        for (int i = 0; i < vectID.length; i++) {
            descrCreated.add(DBret.retrieveSingleYugiohDescription(connection, vectID[i]));
        }
        System.err.println("[DBSearchDescription] - Yu-Gi-Oh description retrieved.");
        return descrCreated;
    }

    /**
     * Check on Pokemon's length parameters
     *
     * @param len1: first part of length
     * @param len2: second part of length
     * @return union if not null, null if either is null
     */
    private String checkLength(String len1,String len2){
        if(!(len1 == null && len2 == null))
            return len1 + "’" + len2 + "’’";
        else return null;
    }

    /**
     * Create a View from an other view with where clause and a string attribute
     *
     * @param connection: Database connection
     * @param NameViewPrevious: Name of the previous view
     * @param NameViewNext: Name of the next view created
     * @param attribute: attribute for 'WHERE' clause
     * @param value: value of attribute
     * @throws SQLException: SQL exception
     */
    private void whereString(Connection connection,String NameViewPrevious,String NameViewNext,String attribute,String value) throws  SQLException{
        Statement stmt = connection.createStatement();
        String query;
        if(value!=null) {
            query = "CREATE VIEW " + NameViewNext + " AS SELECT * FROM " + NameViewPrevious + " WHERE " + attribute + " = '" + value + "';";
        }
        else {
            query = "CREATE VIEW " + NameViewNext + " AS  SELECT * FROM " + NameViewPrevious + ";";
        }
        stmt.executeUpdate(query);
    }

    /**
     * Where clause of string using query as LIKE 'xxx%'
     *
     * @param connection: Connection to DB
     * @param NameViewPrevious: Previous view
     * @param NameViewNext: View created
     * @param attribute: Attribute of Name of description
     * @param value: String
     * @throws SQLException: SQL exception
     */
    private void whereLikeString(Connection connection,String NameViewPrevious,String NameViewNext,String attribute,String value) throws SQLException {
        Statement stmt = connection.createStatement();
        String query;
        if(value!=null) {
            query = "CREATE VIEW " + NameViewNext + " AS SELECT * FROM " + NameViewPrevious + " WHERE " + attribute + " LIKE '%" + value + "%';";
        }
        else {
            query = "CREATE VIEW " + NameViewNext + " AS  SELECT * FROM " + NameViewPrevious + ";";
        }
        stmt.executeUpdate(query);

    }

    /**
     * Create a View from an other view with where clause and int as attribute
     *
     * @param connection: Conncection to DB
     * @param NameViewPrevious: Name of the previous view
     * @param NameViewNext: Name of the next view created
     * @param attribute: Attribute for where clause
     * @param value: Value of attribute
     * @throws SQLException: SQL exception
     */
    private void whereInt(Connection connection,String NameViewPrevious,String NameViewNext,String attribute,int value, int range) throws  SQLException{
        int rangeMin = value - range < 0? 0 : value - range;
        int rangeMax = value + range;

        Statement stmt = connection.createStatement();
        String query;
        if(value!=0) {
            query = "CREATE VIEW " + NameViewNext + " AS SELECT * FROM " + NameViewPrevious + " WHERE " + attribute + " <= " + rangeMax + " AND "+ attribute + " >= " + rangeMin + ";";
        }
        else {
            query = "CREATE VIEW " + NameViewNext + " AS  SELECT * FROM " + NameViewPrevious + ";";
        }
        stmt.executeUpdate(query);
    }

    /**
     * Initialize a View in DB with all Pokemon's Description
     *
     * @param connection: Connection to DB
     * @param nameView: Name of View to create
     */
    private void initPokemonView(Connection connection,String nameView) throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "CREATE VIEW " + nameView + "(pokemon_description_id, Name, Type, Hp, Description, Length, Weigth, Level, Picture) AS SELECT * FROM pokemon_card;";
        stmt.executeUpdate(query);
    }

    /**
     * Initialize a View in DB with all YuGiOh's Description
     *
     * @param connection : Database connection
     * @param nameView: Name of View to create
     * @throws SQLException: SQL exception
     */
    private void initYuGiOhView(Connection connection,String nameView) throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "CREATE VIEW " + nameView + "(yugioh_description_id, Name, Description, Reference, Level, Atk, Def, Monster_Type_ID, Type_ID) AS SELECT yugioh_description_id, Name, Description, Reference, Level, Atk, Def, MonsterTypeName, CardTypeName FROM yugioh_card AS a LEFT JOIN (SELECT Monster_Type_ID, Name AS MonsterTypeName FROM monster_Type) AS b ON a.Monster_Type_ID = b.Monster_Type_ID LEFT JOIN (SELECT Type_ID, Name AS CardTypeName FROM card_Type) AS c ON a.Type_ID = c.Type_ID;";
        stmt.executeUpdate(query);
    }

    /**
     * Size of a View
     *
     * @param connection: Connection to DB
     * @param viewname: View's name
     * @return size of View
     */
    private int getViewSize(Connection connection, String viewname) {
        int size = 0;
        try {
            String query="SELECT COUNT(*) FROM " + viewname + ";";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            rs.next();
            size = rs.getInt(1);
            return size;
        }catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * Drop a View
     *
     * @param connection: Connection to DB
     * @param NameView: Name of View to be dropped
     *
     */
    private void dropView(Connection connection,String NameView) throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "DROP VIEW " + NameView + ";";
        stmt.executeUpdate(query);
    }
}