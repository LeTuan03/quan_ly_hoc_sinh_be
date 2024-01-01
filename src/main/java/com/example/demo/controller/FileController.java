package com.example.demo.controller;

import com.example.demo.dto.AvatarDto;
import com.example.demo.entity.FileEntity;
import com.example.demo.repo.AccountRepo;
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
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("accountId") Integer accountId) {
        try {
            fileService.storeFile(file, accountId);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file.");
        }
    }

    @GetMapping("/avatar/{accountId}")
    public ResponseEntity<byte[]> getAvatarByAccountId(@PathVariable Integer accountId) {
        AvatarDto avatarDto = fileService.getAvatarByAccountId(accountId);

        if (avatarDto == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(avatarDto.getData().length);

        return new ResponseEntity<>(avatarDto.getData(), headers, HttpStatus.OK);
    }

}
