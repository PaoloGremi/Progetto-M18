package TradeCenter;

import TradeCenter.Card.CardCatalog;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Customer;

import java.io.*;
import java.sql.*;

/**
 * Proxy class for DataBase connection.
 * @author Roberto Gallotta
 */
public class DBProxy {

    private Connection connection;

    /*
    Scaricate mysql-connector-java-8.0.11.jar e mettetelo sotto src, poi File -> Project Structure -> Libraries e aggiungete il jar.
    Sotto MySQL create il database "CARDS".
    Usate il seguente script SQL sul database "cards" per poter usare correttamente il db:
    CREATE USER 'tradecenter'@'localhost' IDENTIFIED BY 'Password1!';
    GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON CARDS.* TO 'tradecenter'@'localhost';
    Poi usate gli script di Fede per caricare le tabelle e popolarle.
    Customer database:

    -- ------------ TABLE ----------------
    create table customers
    ( ID int primary key,
      customer mediumblob
    );

     */

    /**
     * Connects to database
     * @param database: database name
     */
    private void connectToDB(String database) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC", "tradecenter", "Password1!");
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
            System.err.println(e.getMessage());
        }
        return blob;
    }

    /**
     * Get an Object from a given Blob
     * @param blob: Blob to convert
     * @return: Object
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
     * Populates the provided CardCatalog with the Descriptions from the selected table
     * @param table: Name of the table (pokemon_cards or Yugioh_card)
     * @param cc: Card Catalog
     */
    public void populateCatalog(String table, CardCatalog cc) {
        connectToDB("CARDS");
        PreparedStatement ps;
        ResultSet rs;
        try {
            switch(table) {
                case "pokemon_card":
                    ps = connection.prepareStatement("SELECT * FROM pokemon_card;");
                    rs = ps.executeQuery();

                    // handles NULL values for the different card types
                    String name;
                    String description;
                    File picture;
                    int card_id;
                    String type;
                    int hp;
                    int weight;
                    String length;
                    int level;
                    while (rs.next()) {
                        name = rs.getString("Name");
                        description = rs.getString("Description");
                        picture = (File)getObjFromBlob(rs.getBlob("Picture"));
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
                    break;
                case "yugioh_card":
                    ps = connection.prepareStatement("SELECT * FROM yugioh_card as a left join SELECT b.Monster_Type_ID, b.Name as MonsterTypeName, c.Type_ID, c.Name as CardTypeName FROM Monster_Type as b join Card_Type as c) as d on a.Monster_Type_ID = d.Monster_Type_ID and a.Cards_ID = d.Type_ID;");
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        // todo add NULL values handling
                        cc.addDescription(new YuGiOhDescription(
                                rs.getString("Name"),
                                rs.getString("Description"),
                                (File)getObjFromBlob(rs.getBlob("Picture")),
                                rs.getString("Reference"),
                                rs.getInt("Level"),
                                rs.getInt("Atk"),
                                rs.getInt("Def"),
                                rs.getString("MonsterTypeName"),
                                rs.getString("CardTypeName")
                        ));
                    }
                    break;
                default:
                    throw new SQLException("ERROR: TABLE NOT RECOGNIZED");
            }
        } catch (SQLException | IOException e ) {
            System.err.println(e.getMessage());
        } finally {
            disconnectFromDB();
        }
    }

    /**
     * Calculate customer table size (number of customers saved in the database)
     * @return number of customers
     */
    public int customersSize() {
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
     * @param position: Index of the customer in the database
     */
    public void updateCustomer(Customer customer, int position) {
        connectToDB("CUSTOMERS");

        try {
            // convert class object to blob
            Blob b1 = createBlob(customer);
            // create prepared statement and execute it/commit changes
            PreparedStatement ps = connection.prepareStatement("UPDATE customers SET customer = ? WHERE id = ?;");
            ps.setBlob(1, b1);
            ps.setString(2, "USER-" + position);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectFromDB();
        }

    }

    /**
     * Returns a Customer object from the database, containing all its data.
     * @param i: customer's index in the database
     * @return Customer object (to be referenced)
     */
    public Customer getCostumer(int i) {
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
        }
    }

}
