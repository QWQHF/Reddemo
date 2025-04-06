package com.example.demo.controller;

import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultCode;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.dto.response.UploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Value("${file.upload-dir}") // 在application.properties中配置
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseBody<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 确保上传目录存在
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成唯一文件名
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // 保存文件
            Path filePath = Paths.get(uploadDir, uniqueFileName);
            Files.write(filePath, file.getBytes());

            // 构建访问URL (实际项目中可能需要配置完整URL)
            String fileUrl = "/uploads/" + uniqueFileName;

            return ResultResponse.success(new UploadResponse(fileUrl, uniqueFileName));
        } catch (IOException e) {
            return ResultResponse.failure(ResultCode.FAIL);
        }
    }
}