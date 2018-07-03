package TradeCenter.DatabaseProxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Management for database connection
 * @author Roberto Gallotta
 */
public class DBConnectionManager {

    private final String DATABASE = "CARDS";
    private final String USERNAME = "tradecenter";
    private final String PASSWORD = "Password1!";

    /**
     * Connects to database
     */
    protected Connection connectToDB(Connection connection) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=UTC&useSSL=false", USERNAME, PASSWORD);
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
