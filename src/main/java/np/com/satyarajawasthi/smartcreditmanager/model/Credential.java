package np.com.satyarajawasthi.smartcreditmanager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a Credential entity.
 * This class encapsulates credential information including id, tool name, username, password & remarks.
 *
 * @author SatyaRajAwasth1
 * @since 10/24/2023
 */

public class Credential {

    private final IntegerProperty id;
    private final StringProperty toolName;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty email;
    private final StringProperty remarks;

    public Credential(int id, String toolName, String username, String password, String email, String remarks) {
        this.id = new SimpleIntegerProperty(id);
        this.toolName = new SimpleStringProperty(toolName);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.remarks = new SimpleStringProperty(remarks);
    }

    // Getters for JavaFX properties
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty toolNameProperty() {
        return toolName;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    // Getters for regular Java properties
    public int getId() {
        return id.get();
    }

    public String getToolName() {
        return toolName.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getRemarks() {
        return remarks.get();
    }
}

