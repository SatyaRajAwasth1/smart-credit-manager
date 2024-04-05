package np.com.satyarajawasthi.smartcreditmanager.model;

public class Session {
    private Session(){
    }
    private static User currentUser;

    public static User getUser() {
        return currentUser;
    }

    public static void setUser(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
        // Navigate back to login screen
    }
}
