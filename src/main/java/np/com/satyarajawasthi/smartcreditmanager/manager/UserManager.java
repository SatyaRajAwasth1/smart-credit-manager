package np.com.satyarajawasthi.smartcreditmanager.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import np.com.satyarajawasthi.smartcreditmanager.controller.ChangeCredentialsDialogController;
import np.com.satyarajawasthi.smartcreditmanager.model.User;
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

    public static void onFirstLogin
            () {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Start a transaction

            UserRepository.createUserTable(connection);
            UserRepository.insertInitialUserRecords(connection);
            UserRepository.restrictUserInsertion(connection);
            CredentialRepository.createCredentialTable(connection);
            connection.commit(); // Commit the transaction

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

    public static void showChangeCredentialsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(UserManager.class.getResource("/np/com/satyarajawasthi/smartcreditmanager/fxml/change_credentials_dialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Credentials");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initOwner(null); // Set to null or the main stage if you have a reference to it.

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ChangeCredentialsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait for it to be closed
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
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

    public static void finalizeFirstLoginSetup() {
        try (FileOutputStream output = new FileOutputStream(CONFIG_URL)) {
            Properties properties = new Properties();
            properties.setProperty("isFirstLogin", String.valueOf(false));
            properties.store(output, null);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error updating the properties file: {0}", e.getMessage());
        }

        // Call incrementPasswordUpdateCount from UserRepository
        try {
            UserRepository.markPasswordAsUpdated();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error incrementing password update count: {0}", e.getMessage());
        }
    }

    public static void changeDefaultUser(User updatedUser){
        User user = UserRepository.getUser();
        user.setUsername(updatedUser.getUsername());
        user.setPassphrase(updatedUser.getPassphrase());
        user.setPassword(updatedUser.getPassword());
        UserRepository.updateUser(user);
    }
    public static User getUser(){
        return UserRepository.getUser();
    }

}
