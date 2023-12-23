package com.example.demo.services;

import com.example.demo.entity.FileEntity;
import com.example.demo.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    @Autowired
    private FileRepo fileRepository;

    public FileEntity  storeFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setData(file.getBytes());
        return fileRepository.save(fileEntity);
    }

    public FileEntity getFile(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }
}
