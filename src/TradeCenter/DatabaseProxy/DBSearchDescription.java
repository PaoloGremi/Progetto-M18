package TradeCenter.DatabaseProxy;

import TradeCenter.Card.PokemonDescription;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;

public class DBSearchDescription {

    /**
     *
     * @param connection: Database connection
     * @param typeInput: Card Type
     * @param hpInput: HP
     * @param lev:Level
     * @param weigth: Weight
     * @param len1: Length1
     * @param len2: Length2
     * @return Pokemon's descriptions founded
     */
    public HashSet<PokemonDescription> getSearchedDescrPokemon(Connection connection, String typeInput, int hpInput, int lev, int weigth, String len1, String len2) {

        //connection = dbConn.connectToDB(connection);

        //ckeck on lenght
        String lenghtComplete=checkLength(len1,len2);

        HashSet<PokemonDescription> descrFounded = new HashSet<>();
        System.err.println("Searching a Pokemon description...");
        PreparedStatement ps;
        ResultSet rs;
        BufferedImage picture = null;
        try {
            initPokemonView(connection,"InitView");
            //String attribute
            whereString(connection,"InitView","PoView1","Type",typeInput);
            whereString(connection,"PoView1","PoView2","Length",lenghtComplete);
            //Int attribute
            whereInt(connection,"PoView2","PoView3","HP",hpInput);
            whereInt(connection,"PoView3","PoView4","Weigth",weigth);
            whereInt(connection,"PoView4","PoFinalView","Level",lev);

            descrFounded=retrieveDescriptionFromFinalView(connection,"PoFinalView");
            dropPokemonView(connection,"InitView");
            dropPokemonView(connection,"PoView1");
            dropPokemonView(connection,"PoView2");
            dropPokemonView(connection,"PoView3");
            dropPokemonView(connection,"PoView4");
            dropPokemonView(connection,"PoFinalView");
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
     * Retrieve description from final view filtered
     *
     * @param connection: connection to db
     * @param lastViewName: last filtered view
     * @return HashSet of description
     * @throws SQLException
     * @throws IOException
     */
    private HashSet<PokemonDescription> retrieveDescriptionFromFinalView(Connection connection,String lastViewName) throws SQLException, IOException {
        HashSet<PokemonDescription> descrCreated=new HashSet<>();

        Statement stmt;
        ResultSet rs;
        String quey="select *\n" + "from "+lastViewName+";";
        stmt=connection.createStatement();
        rs=stmt.executeQuery(quey);

        String name;
        String description;
        int card_id;
        String type;
        int hp;
        int weight;
        String length;
        int level;
        BufferedImage picture = null;
        System.err.println("Retrieving "+" Pokemon description"); //todo implementare count(*)
        while (rs.next()) {

            byte[] bytes;
            Blob blob;

            blob = rs.getBlob("Picture");
            bytes = blob.getBytes(1, (int) blob.length());
            picture = ImageIO.read(new ByteArrayInputStream(bytes));

            name = rs.getString("Name");
            description = rs.getString("Description");
            if (rs.wasNull()) {
                description = "NO DESCRIPTION AVAILABLE";
            }
            card_id = rs.getInt("Cards_ID");
            type = rs.getString("Type");
            hp = rs.getInt("Hp");
            if (rs.wasNull()) {
                hp = 0;
            } //could be NULL
            weight = rs.getInt("Weigth");
            if (rs.wasNull()) {
                weight = 0;
            } //could be NULL
            length = rs.getString("Length");
            if (rs.wasNull()) {
                length = "";
            } //could be NULL
            level = rs.getInt("Level");
            if (rs.wasNull()) {
                level = 0;
            } //could be NULL
            descrCreated.add(new PokemonDescription(name, description, picture, card_id, type, hp, weight, length, level));
        }
        return descrCreated;

        }
    /**
     * Check on length parameters
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
     * Initialize a View in DB with all Pokemon Description
     *
     * @param connection: Connection to DB
     * @param NameView: Name of View to create
     */
    private void initPokemonView(Connection connection,String NameView) throws SQLException {
        Statement stmt ;
        String query = "create view "+NameView+"(Cards_ID,Name,Type,Hp,Description,Length,Weigth,Level,Picture) as \n" +
                "\tselect *\n" +
                "    from pokemon_card;";

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
             query = "create view " + NameViewNext + "(Cards_ID,Name,Type,Hp,Description,Length,Weigth,Level,Picture) as " +
                    " select * " +
                    "from " + NameViewPrevious + " where " + attribute + "='" + value + "';";
        }
        else {
            query="create view " + NameViewNext + "(Cards_ID,Name,Type,Hp,Description,Length,Weigth,Level,Picture) as " +
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

    private void whereInt(Connection connection,String NameViewPrevious,String NameViewNext,String attribute,int value) throws  SQLException{
        Statement stmt;
        int rs;
        String query;
        if(!(value==0)) {
            query = "create view " + NameViewNext + "(Cards_ID,Name,Type,Hp,Description,Length,Weigth,Level,Picture) as " +
                    "select * " +
                    "from " + NameViewPrevious + " where " + attribute + "<=" + value + ";";
        }
        else {
            query="create view " + NameViewNext + "(Cards_ID,Name,Type,Hp,Description,Length,Weigth,Level,Picture) as " +
                    "select *" +
                    "from " + NameViewPrevious+";";
        }
        stmt=connection.createStatement();
        rs=stmt.executeUpdate(query);
    }

    /**
     * Drop View in Pokemon
     *
     * @param connection: Connection to DB
     * @param NameView: Name of View to be dropped
     *
     */
    private void dropPokemonView(Connection connection,String NameView) throws SQLException {
        Statement stmt ;
        String query = "DROP VIEW "+NameView+";";

            stmt = connection.createStatement();
            int rs = stmt.executeUpdate(query);



    }
}