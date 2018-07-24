package DatabaseProxy;

import TradeCenter.Card.Description;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DBAtomicDeleter {

    /**
     * Remove wishlist description from given customer
     * @param connection: database connection
     * @param customer: customer
     * @param description: description to be removed
     */
    void removeWishlist(Connection connection, Customer customer, Description description) {
        System.err.println("[DBAtomicDeleter] - Removing wishlist number " + description.getName() + " from customer " + customer.getId() + "...");
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM wishlist WHERE customer_id = ? AND description_id = ? AND type = ?;");
            ps.setString(1, customer.getId());
            if(description instanceof PokemonDescription) {
                ps.setInt(2, ((PokemonDescription) description).getCardID());
                ps.setString(3, "pokemon");
            } else {
                ps.setInt(2, ((YuGiOhDescription) description).getCardID());
                ps.setString(3, "yugioh");
            }
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicDeleter] - Wishlist number " + description.getName() + " from customer " + customer.getId() + " removed.");
        }catch (SQLException e){
            System.err.println("[DBAtomicDeleter] - Exception " + e + " encounterd in method removeWishlist.");
        }
    }

    /**
     * Delete specific card from a trade
     * @param connection: database connection
     * @param cardID: id of card to be removed
     * @param tradeID: trade's id
     * @param offerCol: offer column
     */
    void removeActiveTradeCard(Connection connection, int cardID, int tradeID, int offerCol) {
        try {
            System.err.println("[DBAtomicDeleter] - Removing card " + cardID + " from trade " + tradeID + "...");
            PreparedStatement ps = connection.prepareStatement("DELETE FROM cards_active WHERE trade_id = ? AND offer_col = ? AND card_id = ?;");
            ps.setInt(1, tradeID);
            ps.setInt(2, offerCol);
            ps.setInt(3, cardID);
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicDeleter] - Card " + cardID + " in trade " + tradeID + " removed.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicDeleter] - Exception " + e + " encounterd in method removeActiveTradeCard.");
        }
    }

    /**
     * Delete all cards from a specific trade
     * @param connection: database connection
     * @param tradeID: trade's id
     */
    void removeActiveTradeCard(Connection connection, int tradeID) {
        try {
            System.err.println("[DBAtomicDeleter] - Removing cards in  trade " + tradeID + "...");
            PreparedStatement ps = connection.prepareStatement("DELETE FROM cards_active WHERE trade_id = ?");
            ps.setInt(1, tradeID);
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicDeleter] - Cards in trade " + tradeID + " removed.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicDeleter] - Exception " + e + " encounterd in method removeActiveTradeCard.");
        }
    }

    /**
     * Remove trade's informations
     * @param connection: database connection
     * @param tradeID: trade's id
     */
    void removeTradeInfo(Connection connection, int tradeID) {
        try {
            System.err.println("[DBAtomicDeleter] - Removing trade's informations...");
            PreparedStatement ps = connection.prepareStatement("DELETE FROM trades WHERE trade_id = ?;");
            ps.setInt(1, tradeID);
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicDeleter] - Trade's informations removed.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicDeleter] - Exception " + e + " encounterd in method removeTradeInfo.");
        }
    }

    /**
     * TEST ONLY METHOD: Remove cards from a given customer and return updated cards count
     * @param connection: database connection
     * @param customerID: customer's id
     */
    void removeCardsFromCustomer(Connection connection, String customerID) {
        try {
            System.err.println("[DBAtomicDeleter] - *TEST ONLY* Removing customer " + customerID + "'s cards...");
            PreparedStatement ps = connection.prepareStatement("DELETE FROM cards WHERE customer_id = ?;");
            ps.setString(1, customerID);
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicDeleter] - *TEST ONLY* Customer " + customerID + "'s cards removed.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicDeleter] - *TEST ONLY* Exception " + e + " encounterd in method removeCardsFromCustomer.");
        }
    }

}
