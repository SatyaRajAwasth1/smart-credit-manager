package np.com.satyarajawasthi.smartcreditmanager.manager;

import np.com.satyarajawasthi.smartcreditmanager.model.Credential;
import np.com.satyarajawasthi.smartcreditmanager.repository.CredentialRepository;

import java.util.ArrayList;
import java.util.List;

public class CredentialManager {
    private final List<Credential> credentialList;
    private final CredentialRepository credentialRepository;
    private final String encryptionKey;

    public CredentialManager() {
        // Initialize with mock data
        credentialList = new ArrayList<>();
        credentialRepository = new CredentialRepository();
        encryptionKey = "SMART";

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

    public List<Credential> getAllCredentials() {
        return credentialList;
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
