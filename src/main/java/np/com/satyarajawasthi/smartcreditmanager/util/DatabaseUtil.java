package np.com.satyarajawasthi.smartcreditmanager.util;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for database operations.
 *
 * @author SatyaRajAwasth1
 * @since 10/24/2023
 */
public final class DatabaseUtil {
    private static final Logger log = Logger.getLogger(DatabaseUtil.class.getName());
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/np/com/satyarajawasthi/smartcreditmanager/database/SmartCreditManagerDB.db";
    private static Connection connection;

    /**
     * Get a connection to the database.
     *
     * @return A database connection.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(DATABASE_URL);
            log.info("New Database Connection session created.");
        }
        return connection;
    }

    /**
     * Close the database connection.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                log.info("Database Connection session closed.");
            }
        } catch (SQLException e) {
            log.log(Level.WARNING, "Exception occurred while closing the connection", e);
        }
    }


}
