package np.com.satyarajawasthi.smartcreditmanager.manager;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SessionManager {
    private static final long TIMEOUT_DURATION = 30 * 60 * 1000L; // 30 minutes
    private static Timer timer;
    private SessionManager() {
    }

    public static void startSessionTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    logout();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, TIMEOUT_DURATION);
    }

    public static void resetSessionTimer() {
        startSessionTimer();
    }

    public static void logout() throws IOException {
        // Invalidate session and navigate back to login screen
        timer.cancel();

        Parent root = FXMLLoader.load(Objects.requireNonNull(
                SessionManager.class.getResource("/np/com/satyarajawasthi/smartcreditmanager/fxml/login.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Smart Credit Manager - Login");
        stage.setScene(new Scene(root, 600, 400));
        stage.setResizable(false);  // Disable window resizing

        // Center the stage on the screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - 600) / 2);
        stage.setY((screenBounds.getHeight() - 400) / 2);

        stage.show();
    }
}
