package TradeCenter;

import TradeCenter.Customers.Customer;

import java.io.*;
import java.sql.*;

/**
 * Proxy class for DataBase connection.
 */
public class DBProxy {

    Connection connection;

    /*
    Scaricate mysql-connector-java-8.0.11.jar e mettetelo sotto src, poi File -> Project Structure -> Libraries e aggiungete il jar.
    Sotto MySQL create il databse "cards".
    Usate il seguente script SQL sul database "cards" per poter usare correttamente il db:
    CREATE USER 'tradecenter'@'localhost' IDENTIFIED BY 'password';
    GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON cards.* TO 'tradecenter'@'localhost';
    Poi usate gli script di Fede per caricare le tabelle e popolarle.
    //todo add a customer database
     */

    /**
     * Connects to database
     * @param database: database name
     */
    private void connectToDB(String database) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC", "tradecenter", "password");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
    }

    /**
     * Disconnects from database
     */
    private void disconnectFromDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Temporary example method: method should partly be either be implemented by catalog or tradecenter itself
     */
    public void getPokemonData() {
        connectToDB("cards");
        StringBuilder name = new StringBuilder();
        try {
            PreparedStatement ps = connection.prepareStatement("select `Name` from pokemon_card where `hp` < 50");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name.append(rs.getString("Name") + "\n");
            }
        } catch (Exception e ) {
            e.printStackTrace();
        } finally {
            System.out.println(name);
            disconnectFromDB();
        }
    }

    /**
     * Updates a customer in the database (contains all customer's informations)
     * @param customer: Customer object to be updated
     */
    public void updateCustomer(Customer customer) {
        connectToDB("cards");

        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            // convert class object to blob
            Blob b1 = connection.createBlob();
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(customer);
            oos.flush();
            b1.setBytes(1, bos.toByteArray());
            // create prepared statement and execute it/commit changes
            PreparedStatement ps = connection.prepareStatement("update t1 set customer = ? where `id` = ?");
            ps.setBlob(1, b1);
            ps.setInt(2, Integer.parseInt(customer.getId())); //todo change how the id is used
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            disconnectFromDB();
        }

    }

    /**
     * Returns a Customer object from the database, containing all its data.
     * @param i: customer's index in the database //todo change this
     * @return Customer object (to be referenced)
     */
    public Customer getCostumer(int i) {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        connectToDB("cards");

        try {
            PreparedStatement ps = connection.prepareStatement("select customer from t1 where `id` = ?");
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Blob b2 = rs.getBlob("customer");
                try {
                    bis = new ByteArrayInputStream(b2.getBytes(1, (int) b2.length()));
                    ois = new ObjectInputStream(bis);
                    obj = ois.readObject();
                } catch (Exception e) {
                    System.err.println(e);
                }
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
}