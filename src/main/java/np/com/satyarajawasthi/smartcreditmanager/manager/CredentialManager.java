package np.com.satyarajawasthi.smartcreditmanager.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import np.com.satyarajawasthi.smartcreditmanager.model.Credential;
import np.com.satyarajawasthi.smartcreditmanager.repository.CredentialRepository;

import java.util.List;

public class CredentialManager {
    private final CredentialRepository credentialRepository;
    private final String encryptionKey;

    public CredentialManager() {
        credentialRepository = new CredentialRepository();
        encryptionKey = "5a98beed71b7d65e10d914d3456f25b1";
    }

    public ObservableList<Credential> getAllCredentials() {
        List<Credential> credentialList = credentialRepository.getAllCredentials(encryptionKey);
        return FXCollections.observableArrayList(credentialList);
    }

    public void addCredential(Credential credential) {
        credentialRepository.addCredential(credential, encryptionKey);
    }

    public void editCredential(Credential credential) {
        credentialRepository.editCredential(credential, encryptionKey);
    }

    public void deleteCredential(int credentialId) {
        credentialRepository.deleteCredential(credentialId);
    }

}
