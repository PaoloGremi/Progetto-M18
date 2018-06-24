package TradeCenter.DatabaseProxy;

import TradeCenter.Card.Card;
import TradeCenter.Card.PokemonDescription;
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
            if (card.getDescription() instanceof PokemonDescription) {
                ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), "pokemon"));
                ps.setString(4, "pokemon");
            } else {
                ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), "yugioh"));
                ps.setString(4, "yugioh");
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
     * Quick method to get description ID (used when inserting card in database)
     * @param connection: database connection
     * @param descriptionName: description's name
     * @param type: description's type
     * @return
     */
    private int getDescriptionIDByName(Connection connection, String descriptionName, String type) {
        int id = 0;
        try {
            PreparedStatement ps = null;
            switch(type) {
                case "pokemon":
                    ps = connection.prepareStatement("SELECT pokemon_description_id FROM pokemon_card WHERE Name = ?");
                    break;
                case "yugioh":
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
