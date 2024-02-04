package backend.backend.utils.encryptation;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64.Decoder;

public class EncryptionUtils {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "ThisIsAValidKey!"; // Reemplaza con tu clave secreta

    public static String encrypt(String plainText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            Encoder encoder = Base64.getUrlEncoder().withoutPadding();
            return encoder.encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al encriptar el texto: " + e.getMessage());
        }
    }

    public static String decrypt(String encryptedText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            Decoder decoder = Base64.getUrlDecoder();
            byte[] decodedBytes = decoder.decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al desencriptar el texto: " + e.getMessage());
        }
    }
}