package TradeCenter.DatabaseProxy;

import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Trades.Trade;

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
    void updateCard(Connection connection, Card card, int tradeID, int offer_col) {
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

    /**
     * Update a card that is no longer in a trade
     * @param connection: database connection
     * @param card: card to be updated
     */
    void updateCard(Connection connection, Card card) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE cards SET trade_id = ?, offer_col = ? WHERE card_id = ?;");
            ps.setNull(1, Types.INTEGER);
            ps.setNull(2, Types.INTEGER);
            ps.setInt(3, card.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateTrade(Connection connection, Trade trade) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE trades SET date = ?, user1_id = ?, user2_id = ?, donedeal = ? WHERE trade_id = ?;");
            ps.setDate(1, trade.getDate());
            ps.setString(2, trade.getCustomer1());
            ps.setString(3, trade.getCustomer2());
            ps.setBoolean(4, trade.isDoneDeal());
            ps.setInt(5, trade.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
