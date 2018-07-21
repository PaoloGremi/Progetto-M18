package TradeCenter.DatabaseProxy;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Trades.Trade;

import javax.sql.rowset.serial.SerialRef;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Handles SET/UPDATE commands for database management
 * @author Roberto Gallotta
 */
class DBAtomicUpdater {

    /**
     * Update a card's owner
     * @param connection: database connection
     * @param card: card to be updated
     * @param newCustomerID: new customer's id
     */
    void updateCard(Connection connection, Card card, String newCustomerID) {
        try {
            System.err.println("[DBAtomicUpdater] - Updating card " + card.getId() + " with new customer " + newCustomerID + "...");
            PreparedStatement ps = connection.prepareStatement("UPDATE cards SET customer_id = ? WHERE card_id = ?;");
            ps.setString(1, newCustomerID);
            ps.setInt(2, card.getId());
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicUpdater] - Card " + card.getId() + " with new customer " + newCustomerID + " updated.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicUpdater] - Exception " + e + " encounterd in method updateCard with new customer ID.");
        }
    }

    /**
     * Update a card's trade information
     * @param connection: database connection
     * @param card: card to be updated
     * @param tradeID: trade id
     * @param offer_col: column offer
     */
    void updateCard(Connection connection, Card card, int tradeID, int offer_col) {
        try {
            System.err.println("[DBAtomicUpdater] - Updating card " + card.getId() + " in new trade " + tradeID + " in offer " + offer_col + "...");
            PreparedStatement ps = connection.prepareStatement("UPDATE cards_active SET trade_id = ?, offer_col = ? WHERE card_id = ?;");
            ps.setInt(1, tradeID);
            ps.setInt(2, offer_col);
            ps.setInt(3, card.getId());
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicUpdater] - Card " + card.getId() + " in new trade " + tradeID + " in offer " + offer_col + " updated.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicUpdater] - Exception " + e + " encounterd in method updateCard with new trade parameters.");
        }
    }

    void updateTrade(Connection connection, Trade trade) {
        try {
            System.err.println("[DBAtomicUpdater] - Updating trade " + trade.getId() + "...");
            PreparedStatement ps = connection.prepareStatement("UPDATE trades SET date = ?, user1_id = ?, user2_id = ?, donedeal = ? , positive_end = ? WHERE trade_id = ?;");
            ps.setDate(1, trade.getDate());
            ps.setString(2, trade.getCustomer1());
            ps.setString(3, trade.getCustomer2());
            ps.setBoolean(4, trade.isDoneDeal());
            ps.setBoolean(5, trade.isPositiveEnd());
            ps.setInt(6, trade.getId());
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicUpdater] - Trade " + trade.getId() + " updated.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicUpdater] - Exception " + e + " encounterd in method updateTrade.");
        }
    }
}
