package edu.bistu.copyright.protect.controller;

import edu.bistu.copyright.protect.util.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
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
}
