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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static np.com.satyarajawasthi.smartcreditmanager.util.DatabaseUtil.closeConnection;
import static np.com.satyarajawasthi.smartcreditmanager.util.DatabaseUtil.getConnection;

public class UserManager {
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());

    private UserManager() {
    }

    public static boolean isFirstLogin() {
        try {
            // Check if it's the first login by reading from the properties file
            if (!UserRepository.isUserTableExists()) {
                return true; // Table doesn't exist yet, consider it as the first login
            }
            int passwordUpdatedValue = UserRepository.getPasswordUpdatedValue();
            return (passwordUpdatedValue == 0);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting password updated value: {0}", e.getMessage());
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
            logger.log(Level.SEVERE, "Error while loading credential frame: {0}", e.getMessage());
        }
    }

    public static void finalizeFirstLoginSetup() {
        try {
            UserRepository.markPasswordAsUpdated();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error incrementing password update count: {0}", e.getMessage());
        }
    }

    public static void changeDefaultUser(User updatedUser) {
        UserRepository.updateUser(updatedUser);
    }

    public static User getUser() {
        return UserRepository.getUser();
    }

}
