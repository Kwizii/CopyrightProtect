package edu.bistu.copyright.protect.util;

import edu.bistu.copyright.protect.common.HashType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Chanvo
 * @date 2023/5/12 20:09
 * @description
 */
public class HashUtil {

    public static String getFileHash(Path path, HashType hashType) throws IOException {
        return bytes2HashHex(readFileBytes(path), hashType);
    }

    public static byte[] readFileBytes(Path path) throws IOException {
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }
        throw new IOException("File not exist");
    }

    public static String string2HashHex(String content, HashType hashType) {
        return bytes2HashHex(content.getBytes(StandardCharsets.UTF_8), hashType);
    }

    public static String bytes2HashHex(byte[] content, HashType hashType) {
        try {
            MessageDigest md = switch (hashType) {
                case MD5 -> MessageDigest.getInstance("MD5");
                case SHA1 -> MessageDigest.getInstance("SHA-1");
                case SHA256 -> MessageDigest.getInstance("SHA-256");
            };
            byte[] bytes = md.digest(content);
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
