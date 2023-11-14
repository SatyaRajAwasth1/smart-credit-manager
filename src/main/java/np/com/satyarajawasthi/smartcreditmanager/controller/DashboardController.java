package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import np.com.satyarajawasthi.smartcreditmanager.model.Credential;
import np.com.satyarajawasthi.smartcreditmanager.manager.CredentialManager;

import java.io.IOException;

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
        credentialTable.getItems().addAll(credentialManager.getAllCredentials());
    }

    @FXML
    private void addCredential() {
        openDialog("Add Credential", null);
    }

    @FXML
    private void editCredential() {
        Credential selectedCredential = credentialTable.getSelectionModel().getSelectedItem();
        if (selectedCredential != null) {
            openDialog("Edit Credential", selectedCredential);
        }
    }


    @FXML
    private void deleteCredential() {
        System.out.println("Delete credential clicked.");
        //TODO: Implement logic to delete the selected credential
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
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
}

