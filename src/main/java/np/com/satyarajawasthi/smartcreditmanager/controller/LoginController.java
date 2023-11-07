package np.com.satyarajawasthi.smartcreditmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import np.com.satyarajawasthi.smartcreditmanager.repository.UserRepository;

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
    private Button changePasswordButton;

    /**
     * Initializes the login view.
     * Sets the label and button text based on the user's first login status.
     */
    public void initialize() {
        boolean isFirstTimeUser = UserRepository.isFirstLogin();

        if (isFirstTimeUser) {
            changePasswordButton.setText("Change Password");
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
            loginMessage.setText("Login successful");
            // Redirect to the main application screen
        } else {
            loginMessage.setText("Invalid username or password");
        }
    }

    /**
     * Handles the change password button click event.
     * Implements the logic for changing or recovering the user's password.
     */
    public void changePassword() {
        // Implement password change or recovery logic here
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
        // For simplicity, using "root" as the username and password for now
        return username.equals("root") && password.equals("root");
    }
}
