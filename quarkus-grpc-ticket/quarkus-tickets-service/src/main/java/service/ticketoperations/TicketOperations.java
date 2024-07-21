package service.ticketoperations;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.openmbean.InvalidKeyException;

import org.json.JSONObject;

import service.exceptions.InvalidFieldException;
import service.exceptions.InvalidTicketException;
import service.ticket.Ticket;

public class TicketOperations {

    /**
     * @param pemCertificate, the certificate in string form
     * @return The actual string of the public or private key
     */
    public static String removeX509Headers(String pemCertificate) {
        return pemCertificate
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");
    }

    /**
     * @param privateKeyPem, This is the private key without the headers
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKeyFromKeyWithNoHeaders(String privateKeyPem)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decoded = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * @param privateKey,    privatekey
     * @param encryptedData, encrypted data in string format
     * @return
     * @throws java.security.InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decryptMessage(PrivateKey privateKey, String encryptedData)
            throws java.security.InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * @param str the string we wish to convert to byte
     * @return
     */
    public static byte[] stringToByteArray(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * @param byteArray, the bytearray that we wish to convert to a UTF_8 String
     * @return
     */
    public static String byteArrayToString(byte[] byteArray) {
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    /**
     * @param decrypted, the decrypted version of the ticket in string that we can
     *                   use to construct it back
     * @return
     */
    public static JSONObject convertFromStringToJson(String decrypted) {
        return new JSONObject(decrypted);
    }

    /**
     * @param ticket, the json form of a ticket
     * @return
     * @throws InvalidTicketException
     */
    public static void isValidTicket(JSONObject ticket, String usernameRequest) throws InvalidTicketException {
        int hour = (int) ticket.get("hour");
        int day = (int) ticket.get("day");
        int month = (int) ticket.get("month");
        int year = (int) ticket.get("year");
        String username = (String) ticket.get("username");
        Calendar c = Calendar.getInstance();
        if (username == null || username == "" || username != usernameRequest) {
            return;
        }
        if (year > c.get(Calendar.YEAR)) {
            return;
        }
        if (year == c.get(Calendar.YEAR) && month > (c.get(Calendar.MONTH) + 1)) {
            return;
        }
        if (year == c.get(Calendar.YEAR) && month == (c.get(Calendar.MONTH) + 1)
                && day > c.get(Calendar.DAY_OF_MONTH)) {
            return;
        }
        if (year == c.get(Calendar.YEAR) && month == (c.get(Calendar.MONTH) + 1) && day == c.get(Calendar.DAY_OF_MONTH)
                && hour > c.get(Calendar.HOUR)) {
            return;
        }
        throw new InvalidTicketException("Ticket expired");
    }

    /**
     * @param toCheck, the string to check
     * @param length
     * @return
     */
    public static boolean checkLength(String toCheck, int length) {
        if (toCheck.length() < length) {
            return false;
        }
        return true;
    }

    /**
     * @param toCheck, the string to check
     * @return
     */
    public static boolean isValidString(String toCheck) {
        if (toCheck == null || toCheck.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * @param ticket, ticket to be analyzed
     * @throws InvalidFieldException
     */
    public static void checkTicket(Ticket ticket) throws InvalidFieldException {
        String hashTicket = ticket.getTicket();
        String username = ticket.getUsername();
        if (!isValidString(hashTicket) || !checkLength(hashTicket, 5)) {
            throw new InvalidFieldException("ticket hash");
        }
        if (!isValidString(username) || !checkLength(username, 3)) {
            throw new InvalidFieldException("username");
        }
    }

    /**
     * @param pemCertificate,given private key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static void checkPrivateKey(String pemCertificate) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // ? Check delimiters
        if (!pemCertificate.contains("-----BEGIN PRIVATE KEY-----")
                || !pemCertificate.contains("-----END PRIVATE KEY-----")) {
            throw new InvalidKeyException("bad formated private key");
        }
        // ? Remove the delimiters
        String base64key = removeX509Headers(pemCertificate);
        // ? Decode base64 to byte
        byte[] byte_form = Base64.getDecoder()
                .decode(base64key);
        // ? Try to generate a private key.. in case there are no error everything is
        // ? fine otherwise its a invalid public key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byte_form);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        keyFactory.generatePrivate(keySpec);
    }

    /**
     * @param ticket,                the ticket itself
     * @param pathToPrivateKeyString
     * @throws InvalidFieldException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws java.security.InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidTicketException
     */
    public static void isOkToEnroll(Ticket ticket, Path pathToPrivateKey)
            throws InvalidFieldException, IOException, NoSuchAlgorithmException, InvalidKeySpecException,
            java.security.InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidTicketException {
        checkTicket(ticket);
        byte[] privateKey = Files.readAllBytes(pathToPrivateKey);
        String privateKeyString = byteArrayToString(privateKey);
        String privateKeyWithoutHeaders = removeX509Headers(privateKeyString);
        PrivateKey privateKeyNative = getPrivateKeyFromKeyWithNoHeaders(privateKeyWithoutHeaders);
        String decryptedData = decryptMessage(privateKeyNative, ticket.getTicket());
        JSONObject jsonTicket = convertFromStringToJson(decryptedData);
        isValidTicket(jsonTicket, ticket.getUsername());
    }

    /**
     * @param ticket
     * @return
     * @throws java.security.InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    public static String encryptTicket(String ticketJSONString, PublicKey pubKey)
            throws java.security.InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encryptedBytes = cipher.doFinal(ticketJSONString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * @param publicKeyPem, the public key in string format without the headers
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static PublicKey getPublicKeyFromKeyWithNoHeaders(String publicKeyPem)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] encoded = Base64.getDecoder().decode(publicKeyPem);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keyspec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keyspec);
    }

    /**
     * @param pemCertificate, a base64 string that we want to know if it is a public
     *                        key or not
     * @return boolean, true in case it is a valid public key otherwise false
     */
    public static boolean isItValidPublicKey(String pemCertificate) {
        // ? Check delimiters
        if (!pemCertificate.contains("-----BEGIN PUBLIC KEY-----")
                || !pemCertificate.contains("-----END PUBLIC KEY-----")) {
            return false;
        }
        // ? Remove the delimiters
        String base64key = removeX509Headers(pemCertificate);
        // ? Decode base64 to byte
        byte[] byte_form = Base64.getDecoder()
                .decode(base64key);
        // ? Try to generate a public key.. in case there are no error everything is
        // ? fine otherwise its a invalid public key
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(byte_form);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @param privateKey,private key string
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static void changePrivateKey(String privateKey, Path privateKeyDirectory)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        checkPrivateKey(privateKey);
        byte[] privateKeyNative = stringToByteArray(privateKey);
        Files.write(privateKeyDirectory, privateKeyNative);
    }
}
