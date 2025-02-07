package com.ld.myapplication.t3yz;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtils {

    private static final String RSA_ALGORITHM = "RSA";

    /**
     * 从字符串中加载公钥
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        byte[] buffer = Base64.decode(publicKeyStr.replaceAll("-----\\w+ PUBLIC KEY-----", "").replace("\n", ""), Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(new X509EncodedKeySpec(buffer));
    }

    /**
     * 使用公钥加密
     */
    public static String encryptWithPublicKey(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    /**
     * 使用公钥解密
     */
    public static String decryptWithPublicKey(String encryptedData, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT));
        return new String(decryptedBytes);
    }
}
