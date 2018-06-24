package TradeCenter.DatabaseProxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Management for database connection
 * @author Roberto Gallotta
 */
public class DBConnectionManager {

    /**
     * Connects to database
     * @param database: database name
     */
    protected Connection connectToDB(Connection connection, String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC&useSSL=false", "tradecenter", "Password1!");
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Disconnects from database
     */
    protected Connection disconnectFromDB(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

}
