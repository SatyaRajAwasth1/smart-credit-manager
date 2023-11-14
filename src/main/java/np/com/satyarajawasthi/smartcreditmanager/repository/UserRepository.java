package np.com.satyarajawasthi.smartcreditmanager.repository;

import np.com.satyarajawasthi.smartcreditmanager.model.User;
import np.com.satyarajawasthi.smartcreditmanager.util.EncryptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import static np.com.satyarajawasthi.smartcreditmanager.util.DatabaseUtil.getConnection;

/**
 * Manages user-related operations, including table creation, insertion of initial records, and insertion restriction.
 *
 * @author SatyaRajAwasthi
 * @since 10/24/2023
 */
public class UserRepository {
    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());
    private static final String KEY = "5a98beed71b7d65e10d914d3456f25b1";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "root";
    private static final String DEFAULT_PASSPHRASE = "DEFAULT";

    public static void createUserTable(Connection connection) throws SQLException {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL,
                    password TEXT NOT NULL,
                    passphrase TEXT NOT NULL,
                    is_password_updated INTEGER NOT NULL DEFAULT 0
                );
                """;
        try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
            statement.executeUpdate();
        }
        logger.info("Users table created.");
    }

    public static void insertInitialUserRecords(Connection connection) throws SQLException {
        String insertRecordQuery = """
                INSERT INTO users (id, username, password, passphrase, is_password_updated)
                VALUES (0, ?, ?, ?, ?);
                """;
        try (PreparedStatement statement = connection.prepareStatement(insertRecordQuery)) {
            statement.setString(1, DEFAULT_USERNAME);
            statement.setString(2, EncryptionUtil.encrypt(DEFAULT_PASSWORD, KEY));
            statement.setString(3, EncryptionUtil.encrypt(DEFAULT_PASSPHRASE, KEY));
            statement.setInt(4, 0);
            statement.executeUpdate();
        }
        logger.info("Initial user records inserted.");
    }

    public static void restrictUserInsertion(Connection connection) throws SQLException {
        String restrictInsertionQuery = """
                CREATE TRIGGER no_insert_users
                BEFORE INSERT ON users
                BEGIN
                    SELECT RAISE(FAIL, 'Insertion into users table is not allowed.');
                END;
                """;
        try (PreparedStatement statement = connection.prepareStatement(restrictInsertionQuery)) {
            statement.executeUpdate();
        }
        logger.info("User insertion restricted.");
    }

    public static User getUser() throws SQLException {
        String query = "SELECT * FROM users LIMIT 1"; // Limit to 1 record, as there is only one user
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapUser(resultSet);
            }
        }
        return null;
    }

    public static int getPasswordUpdatedValue() throws SQLException {
        String query = "SELECT is_password_updated FROM users LIMIT 1"; // Limit to 1 record
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("is_password_updated");
            }
        }
        return 0;
    }

    public static void markPasswordAsUpdated() throws SQLException {
        int currentCount = getPasswordUpdatedValue();
        String query = "UPDATE users SET is_password_updated = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ++currentCount);
            statement.executeUpdate();
        }
    }

    public static boolean isUserTableExists() {
        try (Connection connection = getConnection();
             ResultSet resultSet = connection.getMetaData().getTables(null, null, "users", null)) {
            return resultSet.next(); // If the table exists, result set will have at least one row
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static User mapUser(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String passphrase = resultSet.getString("passphrase");
        int passwordUpdated = resultSet.getInt("is_password_updated");
        return new User(username, password, passphrase, passwordUpdated);
    }
}
