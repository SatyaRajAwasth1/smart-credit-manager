package np.com.satyarajawasthi.smartcreditmanager.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class AlertUtil {

    private AlertUtil(){
    }
    private static Timeline alertTimer;

    public static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        // Automatically close the alert after 3 seconds
        if (alertTimer != null) {
            alertTimer.stop();
        }
        alertTimer = new Timeline(new KeyFrame(Duration.seconds(3), e -> alert.close()));
        alertTimer.setCycleCount(1);
        alertTimer.play();
    }

}
