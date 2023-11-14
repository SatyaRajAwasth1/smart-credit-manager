package np.com.satyarajawasthi.smartcreditmanager.repository;

import np.com.satyarajawasthi.smartcreditmanager.model.Credential;
import np.com.satyarajawasthi.smartcreditmanager.util.DatabaseUtil;
import np.com.satyarajawasthi.smartcreditmanager.util.EncryptionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Repository class for performing CRUD operations on credentials.
 *
 * @author SatyaRajAwasthi1
 * @version 1.0
 * @since 10/24/2023
 */
public class CredentialRepository {
    private static final Logger log = Logger.getLogger(CredentialRepository.class.getName());

    /**
     * Creates the credentials table if it doesn't exist.
     *
     * @param connection The database connection.
     * @throws SQLException If a SQL exception occurs.
     */
    public static void createCredentialTable(Connection connection) throws SQLException {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS credentials (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    tool_name TEXT NOT NULL,
                    username TEXT NOT NULL,
                    password TEXT NOT NULL,
                    email TEXT NOT NULL,
                    remarks TEXT NOT NULL
                );
                """;
        try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
            statement.executeUpdate();
        }
        log.info("Credentials table created.");
    }

    /**
     * Adds a new credential to the database.
     *
     * @param credential The credential to add.
     */
    public void addCredential(Credential credential, String encryptionKey) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO credentials (tool_name, username, password, email, remarks) VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, credential.getToolName());
            statement.setString(2, credential.getUsername());
            statement.setString(3, EncryptionUtil.encrypt(credential.getPassword(), encryptionKey));
            statement.setString(4, credential.getEmail());
            statement.setString(5, credential.getRemarks());

            statement.executeUpdate();
            log.info("Credential added successfully: " + credential);

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error adding credential: " + e.getMessage(), e);
        }
    }

    /**
     * Edits an existing credential in the database.
     *
     * @param credential The updated credential.
     */
    public void editCredential(Credential credential, String encryptionKey) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE credentials SET tool_name = ?, username = ?, password = ?, email = ?, remarks = ? WHERE id = ?")) {

            statement.setString(1, credential.getToolName());
            statement.setString(2, credential.getUsername());
            statement.setString(3, EncryptionUtil.encrypt(credential.getPassword(), encryptionKey));
            statement.setString(4, credential.getEmail());
            statement.setString(5, credential.getRemarks());
            statement.setInt(6, credential.getId());

            statement.executeUpdate();
            log.info("Credential edited successfully: " + credential);

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error editing credential: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a credential from the database.
     *
     * @param credentialId The ID of the credential to delete.
     */
    public void deleteCredential(int credentialId) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM credentials WHERE id = ?")) {

            statement.setInt(1, credentialId);
            statement.executeUpdate();
            log.info("Credential deleted successfully with ID: " + credentialId);

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error deleting credential: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all credentials from the database.
     *
     * @return A list of all credentials.
     */
    public List<Credential> getAllCredentials(String encryptionKey) {
        List<Credential> credentials = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM credentials")) {

            while (resultSet.next()) {
                Credential credential = mapResultSetToCredential(resultSet, encryptionKey);
                credentials.add(credential);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error retrieving credentials: " + e.getMessage(), e);
        }
        return credentials;
    }

    private Credential mapResultSetToCredential(ResultSet resultSet, String encryptionKey) throws SQLException {
        Credential credential = new Credential();
        credential.setId(resultSet.getInt("id"));
        credential.setToolName(resultSet.getString("tool_name"));
        credential.setUsername(resultSet.getString("username"));
        credential.setPassword(EncryptionUtil.decrypt(resultSet.getString("password"),encryptionKey));
        credential.setEmail(resultSet.getString("email"));
        credential.setRemarks(resultSet.getString("remarks"));
        return credential;
    }
}
