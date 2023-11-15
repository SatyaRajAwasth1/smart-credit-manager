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

    private int id;
    private String toolName;
    private String username;
    private String password;
    private String email;
    private String remarks;

    public Credential() {
    }

    public Credential(int id, String toolName, String username, String password, String email, String remarks) {
        this.id = id;
        this.toolName = toolName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

