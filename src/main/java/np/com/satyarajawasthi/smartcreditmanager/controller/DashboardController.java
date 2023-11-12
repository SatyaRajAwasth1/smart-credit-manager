package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import np.com.satyarajawasthi.smartcreditmanager.manager.CredentialManagerImpl;
import np.com.satyarajawasthi.smartcreditmanager.model.Credential;
import np.com.satyarajawasthi.smartcreditmanager.manager.CredentialManager;

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
        credentialManager = new CredentialManagerImpl();
        credentialTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initializeTable();
    }

    private void initializeTable() {
        // Initialize table columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        toolNameColumn.setCellValueFactory(cellData -> cellData.getValue().toolNameProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        remarksColumn.setCellValueFactory(cellData -> cellData.getValue().remarksProperty());

        // Load data into the table
        credentialTable.getItems().addAll(credentialManager.getAllCredentials());
    }

    @FXML
    private void addCredential() {
        //TODO: Implement logic to show a dialog for adding a new credential
    }

    @FXML
    private void editCredential() {
        //TODO: Implement logic to show a dialog for editing the selected credential
    }

    @FXML
    private void deleteCredential() {
        //TODO: Implement logic to delete the selected credential
    }

    public void searchCredential(ActionEvent actionEvent) {
        //TODO: Implement search logic with search by tool name
    }
}

