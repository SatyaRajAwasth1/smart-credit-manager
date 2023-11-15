package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    @FXML
    private DialogPane dialogPane;

    private CredentialManager credentialManager;
    private Stage dialogStage;
    private Credential credential; // For storing the credential to edit

    @FXML
    private void initialize() {
        Button applyButton = (Button) dialogPane.lookupButton(ButtonType.APPLY);
        applyButton.setText(credential == null ? " Save " : "Update");
        applyButton.setOnAction(event -> saveCredential());

        Button closeButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        closeButton.setOnAction(actionEvent -> dialogStage.close());
    }

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
        }else {
            this.credential = new Credential();
            this.credential.setId(0);
        }
        // Add listeners to update the credential as the user types
        Credential finalCredential = this.credential;
        toolNameField.textProperty().addListener((observable, oldValue, newValue) -> finalCredential.setToolName(newValue));
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> finalCredential.setUsername(newValue));
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> finalCredential.setPassword(newValue));
        emailField.textProperty().addListener((observable, oldValue, newValue) -> finalCredential.setEmail(newValue));
        remarksField.textProperty().addListener((observable, oldValue, newValue) -> finalCredential.setRemarks(newValue));
    }

    @FXML
    private void saveCredential() {
        String toolName = toolNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String remarks = remarksField.getText().trim();

        if (toolName.isEmpty() || password.isEmpty()) {
            showAlert( "Empty records cannot be saved. It seems one or more required field are not filled.", "Please fill the details to save or edit.", Alert.AlertType.ERROR);
            return;
        }

        if (credential != null && credential.getId() != 0) {
            // Editing an existing credential
            credential.setToolName(toolName);
            credential.setUsername(username);
            credential.setPassword(password);
            credential.setEmail(email);
            credential.setRemarks(remarks);

            credentialManager.editCredential(credential);
            showAlert("Credential updated successfully!", "Selected record update done!", Alert.AlertType.INFORMATION);
            dialogStage.close();
        } else {
            // Adding a new credential
            Credential newCredential = new Credential(
                    0, // ID is set to 0 for a new credential, assuming it's auto-incremented
                    toolName,
                    username,
                    password,
                    email,
                    remarks
            );
            credentialManager.addCredential(newCredential);
            showAlert("Credential saved successfully!", "Records are inserted successfully.", Alert.AlertType.INFORMATION);
            dialogStage.close();
        }
    }

    private void showAlert(String message, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        // Auto-close the alert after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(5),
                event -> alert.close()
        ));
        timeline.setCycleCount(1);
        timeline.play();

        alert.showAndWait();
    }
}
