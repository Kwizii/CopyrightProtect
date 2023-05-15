package edu.bistu.copyright.protect.util;

import edu.bistu.copyright.protect.common.HashType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Chanvo
 * @date 2023/5/12 20:09
 * @description
 */
public class HashUtil {

    public static String string2HashHex(String content, HashType hashType) {
        try {
            MessageDigest md = switch (hashType) {
                case MD5 -> MessageDigest.getInstance("MD5");
                case SHA1 -> MessageDigest.getInstance("SHA-1");
                case SHA256 -> MessageDigest.getInstance("SHA-256");
            };
            byte[] bytes = md.digest(content.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
