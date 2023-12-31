package np.com.satyarajawasthi.smartcreditmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * THis class will be removed later is being used just for test purposes ( running this class will direct show Dashboard and its functionality)
 */
public class DashboardApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/np/com/satyarajawasthi/smartcreditmanager/fxml/dashboard.fxml")));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Smart Credit Manager");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
