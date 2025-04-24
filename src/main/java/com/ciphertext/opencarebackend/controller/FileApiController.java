package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileApiController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        String objectName = minioService.uploadFile(file);

        Map<String, String> response = new HashMap<>();
        response.put("filename", file.getOriginalFilename());
        response.put("objectName", objectName);
        response.put("size", String.valueOf(file.getSize()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{objectName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String objectName) {
        InputStream inputStream = minioService.downloadFile(objectName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", objectName);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }

    @DeleteMapping("/{objectName}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String objectName) {
        minioService.deleteFile(objectName);

        Map<String, String> response = new HashMap<>();
        response.put("message", "File deleted successfully");
        response.put("objectName", objectName);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        return ResponseEntity.ok(minioService.listFiles());
    }

    @GetMapping("/url/{objectName}")
    public ResponseEntity<Map<String, String>> getPresignedUrl(
            @PathVariable String objectName,
            @RequestParam(defaultValue = "3600") int expirySeconds) {

        String url = minioService.getPresignedUrl(objectName, expirySeconds);

        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        response.put("expirySeconds", String.valueOf(expirySeconds));

        return ResponseEntity.ok(response);
    }
}