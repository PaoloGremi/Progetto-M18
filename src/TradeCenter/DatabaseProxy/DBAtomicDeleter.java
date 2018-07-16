package TradeCenter.DatabaseProxy;

import TradeCenter.Card.Description;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DBAtomicDeleter {

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

}
