package com.example.srprs.util;

import com.example.srprs.exception.dto.AlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class AES256Util {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";


    public static String encrypt(final String key, final String data) {
        try {
            byte[] keyData = key.getBytes(StandardCharsets.UTF_8);
            byte[] ivData = key.substring(0, 16).getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(keyData, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivData));

            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
                | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));

            throw new AlgorithmException("암호화 오류: " + e.getMessage());
        }
    }

    public static String decrypt(final String key, final String encryptedData) {
        byte[] keyData;
        try {
            keyData = key.getBytes(StandardCharsets.UTF_8);
            byte[] ivData = key.substring(0, 16).getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(keyData, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivData));
            byte[] decrypted = Base64.getDecoder().decode(encryptedData.getBytes(StandardCharsets.UTF_8));

            return new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
                | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {

            throw new AlgorithmException("복호화 오류: " + e.getMessage());
        }
    }
}
