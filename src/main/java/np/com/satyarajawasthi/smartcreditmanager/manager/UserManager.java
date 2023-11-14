package np.com.satyarajawasthi.smartcreditmanager.manager;

import np.com.satyarajawasthi.smartcreditmanager.repository.CredentialRepository;
import np.com.satyarajawasthi.smartcreditmanager.repository.UserRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static np.com.satyarajawasthi.smartcreditmanager.util.DatabaseUtil.closeConnection;
import static np.com.satyarajawasthi.smartcreditmanager.util.DatabaseUtil.getConnection;

public class UserManager {
    private static final String CONFIG_URL = "/np/com/satyarajawasthi/smartcreditmanager/config.properties";
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());

    public static boolean isFirstLogin() {
        try {
            // Check if it's the first login by reading from the properties file
            if (isFirstLoginInPropertiesFile()) {
                if (!UserRepository.isUserTableExists()) {
                    return true; // Table doesn't exist yet, consider it as the first login
                }
                int passwordUpdatedValue = UserRepository.getPasswordUpdatedValue();
                return (passwordUpdatedValue == 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void onFirstLogin() {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Start a transaction

            UserRepository.createUserTable(connection);
            UserRepository.insertInitialUserRecords(connection);
            UserRepository.restrictUserInsertion(connection);
            CredentialRepository.createCredentialTable(connection);
            connection.commit(); // Commit the transaction

//            TODO: Alert user to change default login credentials
//                markPasswordAsUpdated();
//                markFirstLoginInPropertiesFile();

            logger.info("Users & credentials table created, default user inserted, and insertion restricted.");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback in case of an error
                } catch (SQLException rollbackException) {
                    logger.log(Level.WARNING, "Error during rollback: {0}", rollbackException.getMessage());
                } finally {
                    closeConnection();
                }
            }
            logger.log(Level.SEVERE, "Error during database initialization: {0}", e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private static boolean isFirstLoginInPropertiesFile() {
        try (InputStream input = UserRepository.class.getResourceAsStream(CONFIG_URL)) {
            Properties properties = new Properties();
            properties.load(input);
            return Boolean.parseBoolean(properties.getProperty("isFirstLogin"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading from the properties file: {0}", e.getMessage());
        }
        return true;
    }

    private static void markFirstLoginInPropertiesFile() {
        try (FileOutputStream output = new FileOutputStream(CONFIG_URL)) {
            Properties properties = new Properties();
            properties.setProperty("isFirstLogin", String.valueOf(false));
            properties.store(output, null);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error updating the properties file: {0}", e.getMessage());
        }
    }

}
