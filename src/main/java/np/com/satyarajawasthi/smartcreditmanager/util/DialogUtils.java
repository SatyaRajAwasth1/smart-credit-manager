package np.com.satyarajawasthi.smartcreditmanager.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DialogUtils {

    private static final Logger logger = Logger.getLogger(DialogUtils.class.getName());

    private DialogUtils() {
    }

    public static void showDialog(String fxmlPath, String title, Class<?> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(controllerClass.getResource(fxmlPath));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initOwner(null); // Set to null or the main stage if you have a reference to it.

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            Object controller = loader.getController();
            if (controllerClass.isInstance(controller)) {
                Method setDialogStageMethod = controllerClass.getMethod("setDialogStage", Stage.class);
                setDialogStageMethod.invoke(controller, dialogStage);
            } else {
                throw new IllegalArgumentException("Controller is not an instance of " + controllerClass.getName());
            }

            // Show the dialog and wait for it to be closed
            dialogStage.showAndWait();

        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.log(Level.SEVERE, "Error while loading dialog: {0}", e.getMessage());
        }
    }

}
