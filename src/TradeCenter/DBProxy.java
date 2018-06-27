package TradeCenter;

import TradeCenter.Card.*;
import TradeCenter.Customers.Customer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Proxy class for DataBase connection.
 * @author Roberto Gallotta
 */
public class DBProxy {

    private Connection connection;

    /**
     * Connects to database
     * @param database: database name
     */
    private void connectToDB(String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC&useSSL=false", "tradecenter", "Password1!");
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Disconnects from database
     */
    private void disconnectFromDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Creates a Blob from a given Object
     * @param obj: Object to convert
     * @return blob
     */
    private Blob createBlob(Object obj) {
        ByteArrayOutputStream bos;
        ObjectOutputStream oos;
        Blob blob = null;
        try {
            blob = connection.createBlob();
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            blob.setBytes(1, bos.toByteArray());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return blob;
    }

    /**
     * Get an Object from a given Blob
     * @param blob: Blob to convert
     */
    private Object getObjFromBlob(Blob blob) {
        Object obj = null;
        ByteArrayInputStream bis;
        ObjectInputStream ois;
        try {
            bis = new ByteArrayInputStream(blob.getBytes(1, (int) blob.length()));
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return obj;
    }

    /**
     * Temporary method to get a card from the pokemon db
     * @param i: card id
     * @return pokemon card
     */
    public Card getCard(int i) {
        connectToDB("CARDS");
        byte[] bytes;
        Blob blob;
        Card card = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM pokemon_card where Cards_ID = ?;");
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                blob = rs.getBlob("Picture");
                bytes = blob.getBytes(1, (int)blob.length());
                BufferedImage pic = ImageIO.read(new ByteArrayInputStream(bytes));
                card = new Card(i,new PokemonDescription(
                        rs.getString("Name"),
                        rs.getString("Description"),
                        pic,
                        rs.getInt("Cards_ID"),
                        rs.getString("Type"),
                        rs.getInt("Hp"),
                        rs.getInt("Weigth"),
                        rs.getString("Length"),
                        rs.getInt("Level")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnectFromDB();
        return card;
    }

    /**
     * Populates the provided CardCatalog with the Descriptions from the selected table
     * @param table: Name of the table (pokemon_cards or Yugioh_card)
     * @param cc: Card Catalog
     */
    public void populateCatalog(String table, CardCatalog cc) {
        System.err.println("Populating catalog...");
        connectToDB("CARDS");
        PreparedStatement ps;
        ResultSet rs;
        BufferedImage picture = null;
        try {
            switch(table) {
                case "pokemon_card":
                    System.err.println("Dumping Pokémon cards into catalog...");
                    ps = connection.prepareStatement("SELECT * FROM pokemon_card;");
                    rs = ps.executeQuery();

                    // handles NULL values for the different card types
                    String name;
                    String description;
                    int card_id;
                    String type;
                    int hp;
                    int weight;
                    String length;
                    int level;
                    while (rs.next()) {
                        byte[] bytes;
                        Blob blob;

                        blob = rs.getBlob("Picture");
                        bytes = blob.getBytes(1, (int)blob.length());
                        picture = ImageIO.read(new ByteArrayInputStream(bytes));

                        name = rs.getString("Name");
                        description = rs.getString("Description");
                        if(rs.wasNull()) {
                            description = "NO DESCRIPTION AVAILABLE";
                        }
                        card_id = rs.getInt("Cards_ID");
                        type = rs.getString("Type");
                        hp = rs.getInt("Hp");
                        if(rs.wasNull()) {
                            hp = 0;
                        } //could be NULL
                        weight = rs.getInt("Weigth");
                        if(rs.wasNull()) {
                            weight = 0;
                        } //could be NULL
                        length = rs.getString("Length");
                        if(rs.wasNull()) {
                            length = "";
                        } //could be NULL
                        level = rs.getInt("Level");
                        if(rs.wasNull()) {
                            level = 0;
                        } //could be NULL

                        cc.addDescription(new PokemonDescription(name, description, picture, card_id, type, hp, weight, length, level));
                    }
                    System.err.println("Dumping Pokémon cards into catalog completed.");
                    break;
                case "yugioh_card":
                    System.err.println("Dumping Yu-Gi-Oh cards into catalog...");
                    ps = connection.prepareStatement("SELECT * FROM yugioh_card as a left join (SELECT b.Monster_Type_ID, b.Name as MonsterTypeName, c.Type_ID, c.Name as CardTypeName FROM Monster_Type as b join Card_Type as c) as d on a.Monster_Type_ID = d.Monster_Type_ID and a.Cards_ID = d.Type_ID;");
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        byte[] bytes;
                        Blob blob;

                        blob = rs.getBlob("Picture");
                        bytes = blob.getBytes(1, (int) blob.length());
                        picture = ImageIO.read(new ByteArrayInputStream(bytes));

                        level = rs.getInt("Level");
                        if(rs.wasNull()) {
                            level = 0;
                        }
                        int atk = rs.getInt("Atk");
                        if(rs.wasNull()) {
                            atk = 0;
                        }
                        int def = rs.getInt("Def");
                        if(rs.wasNull()) {
                            def = 0;
                        }
                        String monsterTypeName = rs.getString("MonsterTypeName");
                        if(rs.wasNull()) {
                            monsterTypeName = "NOT A MONSTER";
                        }


                        cc.addDescription(new YuGiOhDescription(
                                rs.getString("Name"),
                                rs.getString("Description"),
                                picture,
                                rs.getInt("yugioh_description_id"),
                                rs.getString("Reference"),
                                level,
                                atk,
                                def,
                                monsterTypeName,
                                rs.getString("CardTypeName")
                        ));
                    }
                    System.err.println("Dumping Yu-Gi-Oh cards into catalog completed.");
                    break;
                default:
                    throw new SQLException("ERROR: TABLE NOT RECOGNIZED");
            }
        } catch (SQLException | IOException e ) {
            System.err.println(e.getMessage());
        } finally {
            disconnectFromDB();
            System.err.println("Dumping completed.");
        }
    }

    /**
     * Populates the customers' hashmap
     * @param customers: hashmap for the customers
     * @return number of total customers in the table
     */
    public int retrieveCustomers(HashMap<String, Customer> customers) {
        int n = this.customersSize();
        System.err.println("Retrieving "+n+" customers from DB...");
        for(int i=1; i<= n; i++) {
            Customer customer = this.getCostumer(i);
            customers.put(customer.getId(), customer);
        }
        System.err.println(n + " customers retrieved.");
        return n;
    }

    /**
     * Calculate customer table size (number of customers saved in the database)
     * @return number of customers
     */
    private int customersSize() {
        connectToDB("CUSTOMERS");
        int result = 0;

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM customers;");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                result = rs.getInt(1);
            }
            connection.commit();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectFromDB();
        }

        return result;
    }

    /**
     * Updates a customer in the database (contains all customer's informations)
     * @param customer: Customer object to be updated
     */
    public void updateCustomer(Customer customer) {
        System.err.println("Updating customer...");
        connectToDB("CUSTOMERS");

        try {
            // convert class object to blob
            Blob b1 = createBlob(customer);
            // create prepared statement and execute it/commit changes
            PreparedStatement ps = connection.prepareStatement("UPDATE customers SET customer = ? WHERE id = ?;");
            ps.setBlob(1, b1);
            ps.setString(2, customer.getId());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectFromDB();
            System.err.println("Updating customer completed.");
        }

    }

    /**
     * Returns a Customer object from the database, containing all its data.
     * @param i: customer's index in the database
     * @return Customer object (to be referenced)
     */
    private Customer getCostumer(int i) {
        Object obj = null;
        connectToDB("CUSTOMERS");

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT customer FROM customers WHERE ID = ?;");
            ps.setString(1, "USER-" + i);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Blob b2 = rs.getBlob("customer");
                obj = getObjFromBlob(b2);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectFromDB();
        }
        return (Customer)obj;
    }

    /**
     * Insert a new customer in the database
     * @param customer: Customer to be inserted
     */
    public void insertCustomer(Customer customer) {
        System.err.println("Adding customer to DB...");
        connectToDB("CUSTOMERS");

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customers (ID,customer) VALUES(?,?);");
            Blob blob = createBlob(customer);
            ps.setString(1, customer.getId());
            ps.setBlob(2,blob);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectFromDB();
            System.err.println("Customer added to DB.");
        }
    }

    /**
     *
     * @param typeInput
     * @param hpInput
     * @param lev
     * @param weigth
     * @param len1
     * @param len2
     * @return
     */
    public HashSet<PokemonDescription> getSearchedDescrPokemon(String typeInput,int hpInput,int lev, int weigth,String len1,String len2){
        HashSet<PokemonDescription> descrCreated=new HashSet<>();
        System.err.println("Searching a Pokemon description...");
        connectToDB("CARDS");
        PreparedStatement ps;
        ResultSet rs;
        BufferedImage picture = null;
        try {
            ps=connection.prepareStatement("SELECT * FROM pokemon_card WHERE Type=? and Hp=? and length=? and weigth=? and level=?");
            ps.setString(1,typeInput);
            ps.setInt(2,hpInput);
            ps.setString(3,len1+"’"+len2+"’’");
            ps.setInt(4,weigth);
            ps.setInt(5,lev);
            rs=ps.executeQuery();

            String name;
            String description;
            int card_id;
            String type;
            int hp;
            int weight;
            String length;
            int level;

            while (rs.next()){

                byte[] bytes;
                Blob blob;

                blob = rs.getBlob("Picture");
                bytes = blob.getBytes(1, (int)blob.length());
                picture = ImageIO.read(new ByteArrayInputStream(bytes));

                name = rs.getString("Name");
                description = rs.getString("Description");
                if(rs.wasNull()) {
                    description = "NO DESCRIPTION AVAILABLE";
                }
                card_id = rs.getInt("Cards_ID");
                type = rs.getString("Type");
                hp = rs.getInt("Hp");
                if(rs.wasNull()) {
                    hp = 0;
                } //could be NULL
                weight = rs.getInt("Weigth");
                if(rs.wasNull()) {
                    weight = 0;
                } //could be NULL
                length = rs.getString("Length");
                if(rs.wasNull()) {
                    length = "";
                } //could be NULL
                level = rs.getInt("Level");
                if(rs.wasNull()) {
                    level = 0;
                } //could be NULL

                descrCreated.add(new PokemonDescription(name, description, picture, card_id, type, hp, weight, length, level));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return descrCreated;
    }


}
