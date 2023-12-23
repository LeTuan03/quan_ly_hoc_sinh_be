package com.example.demo.controller;

import com.example.demo.entity.FileEntity;
import com.example.demo.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file.");
        }
    }

    //bold
    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        FileEntity fileEntity = fileService.getFile(fileId);
        if (fileEntity != null) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + fileEntity.getFileName())
                    .body(fileEntity.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @GetMapping("/{id}/image")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//        FileEntity fileEntity = fileService.getFile(id);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG); // Đặt loại content type tương ứng với định dạng ảnh của bạn
//
//        return new ResponseEntity<>(fileEntity.getData(), headers, HttpStatus.OK);
//    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        FileEntity fileEntity = fileService.getFile(id);

        if (fileEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(fileEntity.getData(), headers, HttpStatus.OK);
    }
}
