package np.com.satyarajawasthi.smartcreditmanager.manager;

import javafx.collections.ObservableList;
import np.com.satyarajawasthi.smartcreditmanager.model.Credential;

public interface CredentialManager {
    ObservableList<Credential> getAllCredentials();
}
