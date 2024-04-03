package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import np.com.satyarajawasthi.smartcreditmanager.manager.UserManager;
import np.com.satyarajawasthi.smartcreditmanager.model.User;

/**
 * Controller for the Change Credentials dialog.
 *
 * @author SatyaRajAwasthi
 * @since 11/15/2023
 */
public class ChangeUserLoginCredentialsController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField newPasswordConfirmationField;

    @FXML
    private TextField newPassphraseField;

    @FXML
    private Label errorMessageLabel;

    private Stage dialogStage;

    @FXML
    private void initialize() {
        // Initialize the fields with default user values
        User defaultUser = UserManager.getUser();
        newUsernameField.setText(defaultUser.getUsername());
        newPasswordField.setText(defaultUser.getPassword());
        newPassphraseField.setText(defaultUser.getPassphrase());
    }

    @FXML
    private void saveChanges() {
        if (isValidData()) {
            User user = new User(newUsernameField.getText(), newPasswordField.getText(), newPassphraseField.getText(), 1);
            UserManager.changeDefaultUser(user);
            UserManager.finalizeFirstLoginSetup();
            dialogStage.close();
        } else {
            errorMessageLabel.setText("Invalid data. Please check the entered values.");
        }
    }

    private boolean isValidData() {
        // Check if fields are non-empty and if the password matches the confirmation
        return !newUsernameField.getText().isEmpty()
                && !newPasswordField.getText().isEmpty()
                && !newPassphraseField.getText().isEmpty()
                && newPasswordField.getText().equals(newPasswordConfirmationField.getText());
    }

    @FXML
    private void cancel() {
        dialogStage.close();
    }
}
