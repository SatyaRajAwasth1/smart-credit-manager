package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import np.com.satyarajawasthi.smartcreditmanager.manager.UserManager;
import np.com.satyarajawasthi.smartcreditmanager.model.User;

public class ResetPasswordController {

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField newPasswordConfirmationField;

    @FXML
    private TextField passphraseField;

    @FXML
    private Label errorMessageLabel;

    private  Stage dialogStage;

    @FXML
    private void resetPassword() {
        if (isValidData()) {
            User user = UserManager.getUser();
            if (!currentPasswordField.getText().equals(user.getPassword())) {
                errorMessageLabel.setText("Invalid current password.");
            } else if (!passphraseField.getText().equals(user.getPassphrase())) {
                errorMessageLabel.setText("Invalid passphrase.");
            } else {
                // If all checks pass, reset the password and close the dialog
                UserManager.resetPassword(newPasswordConfirmationField.getText());
                dialogStage.close();
            }
        } else {
            errorMessageLabel.setText("Invalid data. Please check the entered values.");
        }
    }

    private boolean isValidData() {
        // Check if fields are non-empty and if the new password matches the confirmation
        return !currentPasswordField.getText().isEmpty()
                && !newPasswordField.getText().isEmpty()
                && !newPasswordConfirmationField.getText().isEmpty()
                && newPasswordField.getText().equals(newPasswordConfirmationField.getText())
                && !passphraseField.getText().isEmpty();
    }

    @FXML
    private void cancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
