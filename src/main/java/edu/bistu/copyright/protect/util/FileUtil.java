package edu.bistu.copyright.protect.util;

/**
 * @author Chanvo
 * @date 2023/5/10 23:09
 * @description
 */
public class FileUtil {
    // 根据content-type获取文件后缀
    public static String getFileExtension(String contentType) {
        if ("image/jpeg".equals(contentType)) {
            return ".jpg";
        } else if ("image/png".equals(contentType)) {
            return ".png";
        } else if ("application/pdf".equals(contentType)) {
            return ".pdf";
        } else {
            return "";
        }
    }
}
