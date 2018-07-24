package DatabaseProxy;

import TradeCenter.Card.*;
import TradeCenter.Trades.Trade;

import java.sql.*;

/**
 * Handles INSERT commands for database management
 */
class DBAtomicInserter {

    /**
     * Insert a card in the database
     * @param connection: database connection
     * @param card: card to insert in database
     * @param customer: owner of the card
     */
    void insertCard(Connection connection, Card card, String customer) {
        try {
            System.err.println("[DBAtomicInserter] - Adding card number " + card.getId() + " to database...");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO cards VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE customer_id = ?;");
            ps.setInt(1,card.getId());
            ps.setString(2, customer);
            switch (card.getDescription().getDescrType()) {
                case POKEMON:
                    ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), CardType.POKEMON));
                    ps.setString(4, "pokemon");
                    break;
                case YUGIOH:
                    ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), CardType.YUGIOH));
                    ps.setString(4, "yugioh");
                    break;
            }
            ps.setString(5, customer);
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicInserter] - Card " + card.getId() + " added to database.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicInserter] - Exception " + e + " encounterd in method insertCard.");
        }
    }

    /**
     * Insert a card in an active trade in the database
     * @param connection: database connection
     * @param cardID: id of the card to insert in database
     * @param tradeID: id of the trade that contains the card
     * @param offerCol: first or second offer
     */
    void insertActiveTradeCard(Connection connection, int cardID, int tradeID, int offerCol) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO cards_active VALUES (?,?,?) ON DUPLICATE KEY UPDATE offer_col = ?;");
            ps.setInt(1, tradeID);
            ps.setInt(2, cardID);
            ps.setInt(3, offerCol);
            ps.setInt(4, offerCol);
            ps.execute();
            connection.commit();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a wishlist element (description) of a customer in the database
     * @param connection: database connection
     * @param description: description to be added
     * @param customer:
     */
    void insertWishlist(Connection connection, Description description, String customer) {
        try {
            System.err.println("[DBAtomicInserter] - Adding wishlist element " + description.getName() + " to customer " + customer + "...");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO wishlist VALUES (?,?,?);");
            ps.setString(1, customer);
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
            System.err.println("[DBAtomicInserter] - Wishlist element " + description.getName() + " added to customer " + customer + ".");
        } catch (SQLException e) {
            System.err.println("[DBAtomicInserter] - Exception " + e + " encounterd in method insertWishlist.");
        }
    }

    /**
     * Insert a customer in the database (only customer primary data)
     * @param connection: database connection
     * @param customerID: customer's ID
     * @param customerUsername: customer's username
     * @param customerPassword: customer's password
     */
    void insertCustomer(Connection connection, String customerID, String customerUsername, String customerPassword) {
        try {
            System.err.println("[DBAtomicInserter] - Adding customer " + customerID + " to database...");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customers VALUES (?,?,?);");
            ps.setString(1, customerID);
            ps.setString(2, customerUsername);
            ps.setString(3, customerPassword);
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicInserter] - Customer " + customerID + " added to database.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicInserter] - Exception " + e + " encounterd in method insertCustomer.");
        }
    }

    /**
     * Insert a traded card in the database
     * @param connection: database connection
     * @param card: card to be added
     * @param customer_id: owner of the card
     * @param trade_id: id of trade
     * @param offer_col: column offer
     */
    void insertTradedCard(Connection connection, Card card, String customer_id, int trade_id, int offer_col) {
        try {
            System.err.println("[DBAtomicInserter] - Adding traded card " + card.getId() +  " to database...");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO cards_old VALUES (?, ?, ?, ?, ?, ?);");
            ps.setInt(1, card.getId());
            ps.setString(2, customer_id);
            if (card.getDescription() instanceof PokemonDescription) {
                ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), CardType.POKEMON));
                ps.setString(4, "pokemon");
            } else {
                ps.setInt(3, getDescriptionIDByName(connection, card.getDescription().getName(), CardType.YUGIOH));
                ps.setString(4, "yugioh");
            }
            ps.setInt(5, trade_id);
            ps.setInt(6, offer_col);
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicInserter] - Traded card " + card.getId() +  " added to database.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicInserter] - Exception " + e + " encounterd in method insertTradedCard.");
        }
    }

    /**
     * Insert a new trade in the database
     * @param connection: database connection
     * @param trade: trade to be added
     */
    void insertTrade(Connection connection, Trade trade) {
        try {
            System.err.println("[DBAtomicInserter] - Adding trade number " + trade.getId() +  " to database...");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO trades VALUES (?, ?, ?, ?, ?, ?);");
            ps.setInt(1, trade.getId());
            ps.setDate(2, trade.getDate());
            ps.setString(3, trade.getCustomer1());
            ps.setString(4, trade.getCustomer2());
            ps.setBoolean(5, trade.isDoneDeal());
            ps.setBoolean(6, trade.isPositiveEnd());
            ps.execute();
            connection.commit();
            System.err.println("[DBAtomicInserter] - Trade number " + trade.getId() +  " added to database.");
        } catch (SQLException e) {
            System.err.println("[DBAtomicInserter] - Exception " + e + " encounterd in method insertTrade.");
        }
    }

    /**
     * Quick method to get description ID (used when inserting card in database)
     * @param connection: database connection
     * @param descriptionName: description's name
     * @param type: description's type
     * @return ID
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
            System.err.println("[DBAtomicInserter] - Exception " + e + " encounterd in private method getDescriptionIDByName.");
        }
        return id;
    }

}
