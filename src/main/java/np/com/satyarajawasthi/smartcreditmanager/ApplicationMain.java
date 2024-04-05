package np.com.satyarajawasthi.smartcreditmanager;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The main class for the Smart Credit Manager application.
 * This class initializes the JavaFX application and sets up the primary stage.
 *
 * @author SatyaRajAwasth1
 * @since 10/24/2023
 */
public class ApplicationMain extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the main login scene from FXML
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/np/com/satyarajawasthi/smartcreditmanager/fxml/login.fxml")));

        // Configure the primary stage
        stage.setTitle("Smart Credit Manager - Login");
        stage.setScene(new Scene(root, 600, 400));
        stage.setResizable(false);  // Disable window resizing

        // Center the stage on the screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - 600) / 2);
        stage.setY((screenBounds.getHeight() - 400) / 2);

        stage.show();  // Display the application window
    }
}
