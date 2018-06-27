package TradeCenter.DatabaseProxy;

import TradeCenter.Card.*;
import TradeCenter.Customers.Customer;

import java.sql.*;

/**
 * Handles INSERT commands for database management
 * @author Roberto Gallotta
 */
public class DBAtomicInserter {

    /**
     * Insert a card in the database
     * @param connection: database connection
     * @param card: card to insert in database
     * @param customer: owner of the card
     */
    public void insertCard(Connection connection, Card card, Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO cards VALUES (?,?,?,?,?,?);");
            ps.setInt(1,card.getId());
            ps.setString(2, customer.getId());
            switch (card.getDescription().getDescrType()) {
                case POKEMON:
                    ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), CardType.POKEMON));
                    ps.setString(4, "POKEMON");
                    break;
                case YUGIOH:
                    ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), CardType.YUGIOH));
                    ps.setString(4, "YUGIOH");
                    break;
            }
            ps.setNull(5, Types.INTEGER);
            ps.setNull(6, Types.INTEGER);
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a wishlist element (description) of a customer in the database
     * @param connection: database connection
     * @param description: description to be added
     * @param customer:
     */
    public void insertWishlist(Connection connection, Description description, Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO wishlist VALUES (?,?,?);");
            ps.setString(1, customer.getId());
            switch (description.getDescrType()) {
                case POKEMON:
                    ps.setInt(2, getDescriptionIDByName(connection, description.getName(), CardType.POKEMON));
                    ps.setString(3, "pokemon");
                    break;
                case YUGIOH:
                    ps.setInt(2, getDescriptionIDByName(connection, description.getName(), CardType.YUGIOH));
                    ps.setString(3, "yugioh");
                    break;
            }
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a customer in the database (only customer primary data)
     * @param connection: database connection
     * @param customer: customer to be added //TODO Maybe pass only ID, Username and Password?
     */
    public void insertCustomer(Connection connection, Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customers VALUES (?,?,?);");
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getUsername());
            ps.setString(3, customer.getPassword());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Quick method to get description ID (used when inserting card in database)
     * @param connection: database connection
     * @param descriptionName: description's name
     * @param type: description's type
     * @return
     */
    private int getDescriptionIDByName(Connection connection, String descriptionName, CardType type) {
        int id = 0;
        try {
            PreparedStatement ps = null;
            switch(type) {
                case POKEMON:
                    ps = connection.prepareStatement("SELECT pokemon_description_id FROM pokemon_card WHERE Name = ?");
                    break;
                case YUGIOH:
                    ps = connection.prepareStatement("SELECT yugioh_description_id FROM yugioh_card WHERE Name = ?");
                    break;
            }
            ps.setString(1, descriptionName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
