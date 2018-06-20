package cn.lonelythinker.crypt.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES,Base64加解密
 *
 * @author lonelythinker
 */
public class AesUtil {
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 先AES加密再Base64加密
     *
     * @param content    要加密的字符串
     * @param encryptKey 加密Key,必须16位
     * @return 先AES加密再Base64加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        return base64Encode(encryptToBytes(content, encryptKey));
    }

    /**
     * 先Base64解密A再ES解密
     *
     * @param encryptStr 要解密的字符串
     * @param decryptKey 解密Key,必须16位
     * @return 先Base64解密A再ES解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        return decryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    /**
     * AES加密
     *
     * @param content    要加密的字符串
     * @param encryptKey 加密Key,必须16位
     * @return 加密后的byte数组
     * @throws Exception
     */
    public static byte[] encryptToBytes(String content, String encryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 要解密的byte数组
     * @param decryptKey   解密Key,必须16位
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * Base64加密
     *
     * @param bytes 要加密的byte数组
     * @return 加密后的字符串
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64解密
     *
     * @param base64Code 要解密的字符串
     * @return 解密后的byte数组
     */
    public static byte[] base64Decode(String base64Code) {
        return Base64.getDecoder().decode(base64Code);
    }
}
