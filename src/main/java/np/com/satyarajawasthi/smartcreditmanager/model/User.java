package np.com.satyarajawasthi.smartcreditmanager.model;

/**
 * Represents a User entity.
 *
 * This class encapsulates user information including their unique identifier (id),
 * username, password, passphrase, and whether the password is updated.
 *
 * @author SatyaRajAwasthi
 * @since 10/26/2023
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String passphrase;
    private int isPasswordUpdated;

    /**
     * Constructs a new User instance.
     *
     * @param username         The user's username.
     * @param password         The user's password.
     * @param passphrase       The user's passphrase.
     * @param isPasswordUpdated Whether the user's password is updated (1 for updated, 0 for not updated).
     */
    public User(String username, String password, String passphrase, int isPasswordUpdated) {
        this.username = username;
        this.password = password;
        this.passphrase = passphrase;
        this.isPasswordUpdated = isPasswordUpdated;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the username of the user.
     *
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the passphrase of the user.
     *
     * @return The user's passphrase.
     */
    public String getPassphrase() {
        return passphrase;
    }

    /**
     * Sets the passphrase of the user.
     *
     * @param passphrase The new passphrase.
     */
    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    /**
     * Gets the flag indicating whether the user's password is updated.
     *
     * @return 1 if the password is updated, 0 otherwise.
     */
    public int getIsPasswordUpdated() {
        return isPasswordUpdated;
    }

    /**
     * Sets the flag indicating whether the user's password is updated.
     *
     * @param isPasswordUpdated 1 for updated, 0 for not updated.
     */
    public void setIsPasswordUpdated(int isPasswordUpdated) {
        this.isPasswordUpdated = isPasswordUpdated;
    }
}
