package rest.utils.crypto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import rest.exceptions.InvalidKeyException;

public class KeyUtils {
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
     * @param signature, This is the signature that we will verify with our public
     *                   key
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @return True case it is the correct signature, otherwise false
     * @throws java.security.InvalidKeyException
     */
    public static boolean verifySignature(byte[] signature)
            throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException,
            SignatureException, IOException, java.security.InvalidKeyException {
        // ? Get original data
        Path path_original_data = Path.of("./src/main/java/Server/verifyFile/verify.txt");
        byte[] origin_data = Files.readAllBytes(path_original_data);

        // ? Load Public Key
        Path path_to_public_key = Path.of("./src/main/java/Server/publickey/public.pem");
        byte[] public_key_bytes = Files.readAllBytes(path_to_public_key.toAbsolutePath());

        // ? Remove the delimiters
        String public_key_without_header_base64 = removeX509Headers(
                new String(public_key_bytes, StandardCharsets.UTF_8));
        byte[] public_key_without_header_bytes = Base64.getDecoder().decode(public_key_without_header_base64);
        // ? Actually get the public key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(public_key_without_header_bytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        // ? Making the actual verification
        java.security.Signature signatureObj = java.security.Signature.getInstance("SHA256withRSA");
        signatureObj.initVerify(publicKey);
        signatureObj.update(origin_data);
        // ? Verification result
        return signatureObj.verify(signature);
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
}
