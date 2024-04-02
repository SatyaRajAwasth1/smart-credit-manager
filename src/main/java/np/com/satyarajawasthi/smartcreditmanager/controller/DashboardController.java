package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import np.com.satyarajawasthi.smartcreditmanager.manager.UserManager;
import np.com.satyarajawasthi.smartcreditmanager.model.Credential;
import np.com.satyarajawasthi.smartcreditmanager.manager.CredentialManager;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for the dashboard view which will be shown after successful user login
 * Handles credential fetch, update, insertion and deletion
 *
 * @author SatyaRajAwasth1  on 10/24/2023
 */
public class DashboardController {

    @FXML
    private TableView<Credential> credentialTable;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Credential, Integer> idColumn;

    @FXML
    private TableColumn<Credential, String> toolNameColumn;

    @FXML
    private TableColumn<Credential, String> usernameColumn;

    @FXML
    private TableColumn<Credential, String> passwordColumn;

    @FXML
    private TableColumn<Credential, String> emailColumn;

    @FXML
    private TableColumn<Credential, String> remarksColumn;

    private CredentialManager credentialManager;

    @FXML
    private void initialize() {
        credentialManager = new CredentialManager();
        credentialTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initializeTable();
        if (UserManager.isFirstLogin()) {
            UserManager.showChangeCredentialsDialog();
        }
    }

    private void initializeTable() {
        // Initialize table columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        toolNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getToolName()));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        remarksColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRemarks()));

        // Load data into the table
        ObservableList<Credential> credentials = credentialManager.getAllCredentials();
        credentialTable.setItems(credentials);
    }

    @FXML
    private void addCredential() {
        openDialog("Add Credential", null);
        credentialTable.setItems(credentialManager.getAllCredentials());
    }

    @FXML
    private void editCredential() {
        Credential selectedCredential = credentialTable.getSelectionModel().getSelectedItem();
        if (selectedCredential != null) {
            openDialog("Edit Credential", selectedCredential);
            credentialTable.setItems(credentialManager.getAllCredentials());
        } else {
            showAlert("Please select a record to edit.", "You haven't selected any credential record.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void deleteCredential() {
        Credential selectedCredential = credentialTable.getSelectionModel().getSelectedItem();
        if (selectedCredential != null) {
            boolean confirmed = showConfirmationDialog("Delete Credential", "The selected record wil be permanently deleted.", "Are you sure you want to delete this credential?");
            if (confirmed) {
                credentialManager.deleteCredential(selectedCredential.getId());
                // Reload the data after deletion
                credentialTable.setItems(credentialManager.getAllCredentials());
                showAlert("Credential deleted successfully!", "Please add new record if it was mistake.", Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Please select a credential to delete.", "You haven't selected any record.", Alert.AlertType.WARNING);
        }
    }

    public void searchCredential(ActionEvent actionEvent) {
        //TODO: Implement search logic with search by tool name
    }

    private void openDialog(String title, Credential credential) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/np/com/satyarajawasthi/smartcreditmanager/fxml/credential_dialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initOwner(credentialTable.getScene().getWindow());

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            CredentialDialogController controller = loader.getController();
            controller.setCredentialManager(credentialManager);
            controller.setDialogStage(dialogStage);
            controller.setCredential(credential);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmationDialog(String title, String headerText, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}

