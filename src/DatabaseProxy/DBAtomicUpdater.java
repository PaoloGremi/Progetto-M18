package DatabaseProxy;

import TradeCenter.Trades.Trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles SET/UPDATE commands for database management
 */
class DBAtomicUpdater {

    /**
     * Update a trade
     * @param connection: database connection
     * @param trade: trade to be updated
     */
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
