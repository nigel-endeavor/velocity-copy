package com.endeavorms.velocity.qto;


import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.security.SecureRandom;


/**
 * Encryption test cases.
 */
@SuppressWarnings("checkstyle:linelength")
@Disabled
public class EncryptionTest {

    /**
     * Tests encryption and decryption.
     */
    @Test
    public void encryptionTest()  {
        String password = "";
        String secret = "";

        String encryptedPassword = "";
        String encryptedSecret = "";
        String decryptedPassword = "";
        String decryptedSecret = "";

        try {
            encryptedPassword = encrypt(password);
            encryptedSecret = encrypt(secret);

            decryptedPassword = decrypt(encryptedPassword);
            decryptedSecret = decrypt(encryptedSecret);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------Encrypted----------------------------");
        System.out.println("Encrypted password: " +  encryptedPassword);
        System.out.println("Encrypted secret: " + encryptedSecret);
        System.out.println("---------------Decrypted----------------------------");
        System.out.println("Decrypted password: " +  decryptedPassword);
        System.out.println("Decrypted secret: " + decryptedSecret);
        System.out.println("----------------------------------------------------");
    }


    /**
     * Tests decryption.
     */
    @Test
    public void decryptionTest() {
        String encrypted = "";

        String decrypted = "";
        String encryptedAgain = "";

        try {
            decrypted = decrypt(encrypted);
            encryptedAgain = encrypt(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------Decrypted----------------------------");
        System.out.println("Decrypted: " +  decrypted);
        System.out.println("---------------Encrypted----------------------------");
        System.out.println("Encrypted: " +  encryptedAgain);
    }


    /**
     * Returns encrypted value.
     * @param plainText unencrypted value.
     * @return String
     * @throws Exception if encryption fails
     */
    public String encrypt(final String plainText) throws Exception {

        byte[] keyBytes = DatatypeConverter.parseBase64Binary("WZ6EA4iIluQJ6yAgrVF2ZQ==");
        byte[] ivBytes = DatatypeConverter.parseBase64Binary("DkcP5+idr8uXBVWuHAVxUA==");

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));

        return DatatypeConverter.printBase64Binary(cipher.doFinal(plainText.getBytes()));
    }

    /**
     * Returns decrypted value.
     * @param cipherText encrypted value
     * @return String
     * @throws Exception when failed to dycrypt.
     */
    public String decrypt(final String cipherText) throws Exception {

        byte[] keyBytes = DatatypeConverter.parseBase64Binary("WZ6EA4iIluQJ6yAgrVF2ZQ==");
        byte[] ivBytes = DatatypeConverter.parseBase64Binary("DkcP5+idr8uXBVWuHAVxUA==");

        byte[] cipherTextBytes = DatatypeConverter.parseBase64Binary(cipherText);

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));

        return new String(cipher.doFinal(cipherTextBytes));
    }


    /**
     * Generate and return a cryptographically strong random number and base64 encode it..
     * @return a String.
     */
    public static String generateSecureRandomBase64Encoded() {
        final int keyLenBytes = 16;
        return DatatypeConverter.printBase64Binary(generateSecureRandom(keyLenBytes));
    }



    /**
     * Generate and return cryptographically strong random number.
     * @param keyLenBytes length in bytes of the key
     * @return a byte array.
     */
    public static byte[] generateSecureRandom(final int keyLenBytes) {
        byte[] b = new byte[keyLenBytes];
        new SecureRandom().nextBytes(b);
        return b;
    }

    //    /**
//     * Gently wiggles the mouse every 5 minutes to keep your computer unlocked, but also not interfere with work.
//     * Used for letting computer handle long running processes. Probably a better way to do this.
//     */
    @Test
    public void wiggler() {
        int fiveMinutes = 300000;
        while (true) {
            try {
                // These coordinates are screen coordinates
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
                System.out.println("x: " + x + ", y: " + y);
               // Move the cursor
                Robot robot = new Robot();
                // My intent is that
                robot.mouseMove(x + 1, y + 1);
                Thread.sleep(fiveMinutes);
            } catch (Exception e) {
            }
        }
    }
}
