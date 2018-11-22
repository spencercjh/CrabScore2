package top.spencer.crabscore.common.util;

import org.apache.commons.codec.binary.Base64;

/**
 * @author spencercjh
 */
public class Base64Util {
    /**
     * 使用Base64加密算法加密字符串
     *
     * @param plainText 原文
     * @return 密文
     */
    public static String encodeStr(String plainText) {
        byte[] b = plainText.getBytes();
        Base64 base64 = new Base64();
        b = base64.encode(b);
        return new String(b);
    }

    /**
     * 使用Base64加密算法解密字符串
     *
     * @param encodeStr 密文
     * @return 明文
     */
    public static String decodeStr(String encodeStr) {
        byte[] b = encodeStr.getBytes();
        Base64 base64 = new Base64();
        b = base64.decode(b);
        return new String(b);
    }
}