package edu.bistu.copyright.protect.controller;

import edu.bistu.copyright.protect.util.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

/**
 * @author Chanvo
 * @date 2023/5/10 20:27
 * @description
 */

@Slf4j
@Tag(name = "文件控制器", description = "文件上传相关")
@RestController
@RequestMapping("/upload")
public class FileController {

    @Value("${upload.path}")
    private String uploadDir; // 文件上传路径，从配置文件中获取

    @Operation(description = "上传文件")
    @PostMapping(value = "/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 获取文件类型
            String contentType = file.getContentType();
            // 生成唯一文件名
            String fileName = UUID.randomUUID() + FileUtil.getFileExtension(contentType);
            Path savePath = Paths.get(uploadDir, fileName);
            Files.write(savePath, file.getBytes());
            return ResponseEntity.ok(fileName);
        } catch (IOException | IllegalArgumentException e) {
            log.error("文件保存失败", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件保存失败");
    }

    @Operation(description = "上传图像base64编码保存图像")
    @PostMapping(value = "/image", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadImage(@RequestBody String data) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(data.substring(data.indexOf(",") + 1));
            String fileName = UUID.randomUUID() + ".png"; // 生成唯一文件名
            Path savePath = Paths.get(uploadDir, fileName);
            Files.write(savePath, imageBytes);
            return ResponseEntity.ok(fileName);
        } catch (IOException | IllegalArgumentException e) {
            log.error("文件保存失败", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件保存失败");
    }
}
