package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import np.com.satyarajawasthi.smartcreditmanager.manager.UserManager;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the login view.
 * Handles user authentication and password management.
 *
 * @author Satya Raj Awasthi
 * @since 10/24/2023
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginMessage;

    @FXML
    private Button loginButton;

    @FXML
    private Button changePasswordButton;

    /**
     * Initializes the login view.
     * Sets the label and button text based on the user's first login status.
     */
    public void initialize() {
        boolean isFirstTimeUser = UserManager.isFirstLogin();

        if (isFirstTimeUser) {
            changePasswordButton.setText("Change Password");
            UserManager.onFirstLogin();
        } else {
            changePasswordButton.setText("Forgot Password");
        }
    }

    /**
     * Handles the login button click event.
     * Validates the user's login credentials and updates the message label.
     */
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidLogin(username, password)) {
            onSuccessfulLogin();
            // Redirect to the main application screen
        } else {
            loginMessage.setTextFill(Color.RED);
            loginMessage.setText("Invalid username or password");
        }
    }

    /**
     * Handles the change password button click event.
     * Implements the logic for changing or recovering the user's password.
     */
    public void changePassword() {
        UserManager.showChangeCredentialsDialog();
    }

    /**
     * Validates the user's login credentials.
     *
     * @param username The entered username.
     * @param password The entered password.
     * @return True if the credentials are valid, false otherwise.
     */
    private boolean isValidLogin(String username, String password) {
        // TODO Implement login validation logic here
        return username.equals("root") && password.equals("root");
    }

    private void onSuccessfulLogin() {
        try {
            // Load the FXML file of the dashboard
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/np/com/satyarajawasthi/smartcreditmanager/fxml/dashboard.fxml")));
            // Switch to the dashboard scene
            Stage primaryStage = (Stage) loginButton.getScene().getWindow();
            primaryStage.setResizable(true);
            primaryStage.setTitle("Smart Credit Manager");
            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(800);
            primaryStage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
