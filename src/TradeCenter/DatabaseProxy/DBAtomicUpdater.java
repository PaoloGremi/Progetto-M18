package TradeCenter.DatabaseProxy;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Handles SET/UPDATE commands for database management
 * @author Roberto Gallotta
 */
public class DBAtomicUpdater {

    /**
     * Update a card's owner
     * @param connection: database connection
     * @param card: card to be updated
     * @param newCustomerID: new customer's id
     */
    protected void updateCard(Connection connection, Card card, String newCustomerID) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE cards SET customer_id = ? WHERE card_id = ?;");
            ps.setString(1, newCustomerID);
            ps.setInt(2, card.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update a card's trade information
     * @param connection: database connection
     * @param card: card to be updated
     * @param tradeID: trade id
     * @param offer_col: column offer
     */
    protected void updateCard(Connection connection, Card card, int tradeID, int offer_col) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE cards SET trade_id = ?, offer_col = ? WHERE card_id = ?;");
            ps.setInt(1, tradeID);
            ps.setInt(2, offer_col);
            ps.setInt(3, card.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //todo similar thing for when a card is no longer in a trade (use setNull on trade_id and offer_col)

}
