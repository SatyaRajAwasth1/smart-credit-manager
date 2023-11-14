// CredentialDialogController.java
package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import np.com.satyarajawasthi.smartcreditmanager.model.Credential;
import np.com.satyarajawasthi.smartcreditmanager.manager.CredentialManager;

public class CredentialDialogController {

    @FXML
    private TextField toolNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField remarksField;

    private CredentialManager credentialManager;
    private Stage dialogStage;
    private Credential credential; // For storing the credential to edit

    public void setCredentialManager(CredentialManager credentialManager) {
        this.credentialManager = credentialManager;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
        if (credential != null) {
            // If editing, pre-fill the fields with the existing credential details
            toolNameField.setText(credential.getToolName());
            usernameField.setText(credential.getUsername());
            passwordField.setText(credential.getPassword());
            emailField.setText(credential.getEmail());
            remarksField.setText(credential.getRemarks());
        }
    }

    @FXML
    private void saveCredential() {
        if (credential == null) {
            // Adding a new credential
            Credential newCredential = new Credential(
                    0, // ID is set to 0 for a new credential, assuming it's auto-incremented
                    toolNameField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    emailField.getText(),
                    remarksField.getText()
            );

            credentialManager.addCredential(newCredential);
        } else {
            // Editing an existing credential
            credential.setToolName(toolNameField.getText());
            credential.setUsername(usernameField.getText());
            credential.setPassword(passwordField.getText());
            credential.setEmail(emailField.getText());
            credential.setRemarks(remarksField.getText());

            credentialManager.editCredential(credential);
        }

        dialogStage.close();
    }
}
