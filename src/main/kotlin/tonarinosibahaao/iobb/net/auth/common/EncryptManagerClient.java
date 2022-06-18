package tonarinosibahaao.iobb.net.auth.common;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;

public class EncryptManagerClient {

    private static final String KEY = "aLbAjQ5BNSwFHQTY";
    private static final String ALGORITHM = "AES";

    public static String encrypt(String source, String key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm));
        return new String(Base64.getEncoder().encode(cipher.doFinal(source.getBytes())));
    }

    /* TalonAuthでは復号はしないので不要 */
    // public static String decrypt(String encryptSource, String key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IllegalArgumentException {
    //     Cipher cipher = Cipher.getInstance(algorithm);
    //     cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm));
    //     return new String(cipher.doFinal(Base64.getDecoder().decode(encryptSource.getBytes())));
    // }

    public static String encryptElement(String element) {
        try {
            String encryptedElement = EncryptManagerClient.encrypt(element, KEY, ALGORITHM);
            return encryptedElement;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /* TalonAuthでは復号はしないので不要 */
    // public static String decryptElement(String element) {
    //     try {
    //         String decryptedElement = EncryptManager.decrypt(element, KEY, ALGORITHM);
    //         return decryptedElement;
    //     } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
    //         System.out.println(e.getMessage());
    //         return null;
    //     } catch (IllegalArgumentException e) {
    //         System.out.println(e.getMessage());
    //         return null;
    //     }
    // }


    static public String addTimeStamp(String encryptedUsername){
        final String timestamp = new Timestamp(new Date().getTime()).toString();
        String timestampStr = (timestamp.replaceAll("[^0-9]", "")).substring(0,8);
        return encryptedUsername + timestampStr;
    }

    static public String encryptUsername(String username){
        return EncryptManagerClient.encryptElement(addTimeStamp(username));
    }
}
