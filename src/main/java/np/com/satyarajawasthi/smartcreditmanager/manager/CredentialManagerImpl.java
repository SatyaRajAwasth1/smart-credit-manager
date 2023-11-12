package np.com.satyarajawasthi.smartcreditmanager.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import np.com.satyarajawasthi.smartcreditmanager.model.Credential;

public class CredentialManagerImpl implements CredentialManager{
    private final ObservableList<Credential> credentialList;

    public CredentialManagerImpl() {
        // Initialize with mock data
        credentialList = FXCollections.observableArrayList();

        for (int i = 1; i <= 50; i++) {
            Credential credential = new Credential(
                    i,
                    "Tool " + i,
                    "user" + i,
                    "pass" + i,
                    "user" + i + "@email.com",
                    "Remarks " + i
            );
            credentialList.add(credential);
        }
    }

    @Override
    public ObservableList<Credential> getAllCredentials() {
        return FXCollections.unmodifiableObservableList(credentialList);
    }

    //TODO: Implement methods for adding, editing, and deleting credentials as needed
}
