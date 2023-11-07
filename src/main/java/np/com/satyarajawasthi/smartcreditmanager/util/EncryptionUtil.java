package np.com.satyarajawasthi.smartcreditmanager.util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * Utility class for encryption and decryption using AES encryption with GCM mode.
 *
 * @author SatyaRajAwasth1
 * @since 10/24/2023
 */
public final class EncryptionUtil {
    private static final Logger log = Logger.getLogger(EncryptionUtil.class.getName());
    private static final int AES_KEY_SIZE = 32; // 32 characters i.e. 256 bits
    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    private static final String AES_ALGORITHM = "AES/GCM/NoPadding";

    private static final Cipher cipher;
    private static final SecureRandom secureRandom;

    static {
        try {
            cipher = Cipher.getInstance(AES_ALGORITHM);
            secureRandom = new SecureRandom();
        } catch (Exception e) {
            log.severe("Failed to initialize encryption cipher: " + e.getMessage());
            throw new RuntimeException("Failed to initialize encryption cipher", e);
        }
    }

    /**
     * Encrypts the given data using AES encryption with GCM mode.
     *
     * @param data The data to be encrypted.
     * @param encryptionKey The encryption key (256 bits).
     * @return The encrypted data in Base64-encoded format.
     */
    public static String encrypt(String data, String encryptionKey) {
        try {
            if (encryptionKey.length() != AES_KEY_SIZE) {
                throw new IllegalArgumentException("Invalid encryption key length.");
            }

            byte[] keyBytes = Base64.getDecoder().decode(encryptionKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            secureRandom.nextBytes(nonce);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] combined = new byte[GCM_NONCE_LENGTH + encryptedBytes.length];
            System.arraycopy(nonce, 0, combined, 0, GCM_NONCE_LENGTH);
            System.arraycopy(encryptedBytes, 0, combined, GCM_NONCE_LENGTH, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            log.severe("Encryption failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Decrypts the given encrypted data using AES decryption with GCM mode.
     *
     * @param encryptedData The encrypted data in Base64-encoded format.
     * @param encryptionKey The encryption key (256 bits).
     * @return The decrypted data.
     */
    public static String decrypt(String encryptedData, String encryptionKey) {
        try {
            if (encryptionKey.length() != AES_KEY_SIZE) {
                throw new IllegalArgumentException("Invalid encryption key length.");
            }

            byte[] combined = Base64.getDecoder().decode(encryptedData);
            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            System.arraycopy(combined, 0, nonce, 0, GCM_NONCE_LENGTH);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);

            byte[] keyBytes = Base64.getDecoder().decode(encryptionKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
            byte[] encryptedBytes = new byte[combined.length - GCM_NONCE_LENGTH];
            System.arraycopy(combined, GCM_NONCE_LENGTH, encryptedBytes, 0, encryptedBytes.length);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.severe("Decryption failed: " + e.getMessage());
            return null;
        }
    }
}
