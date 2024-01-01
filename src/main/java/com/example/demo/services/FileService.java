package com.example.demo.services;

import com.example.demo.dto.AvatarDto;
import com.example.demo.entity.FileEntity;
import com.example.demo.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepo fileRepository;

    public void storeFile(MultipartFile file, Integer accountId) throws IOException {
        Optional<FileEntity> existingFileEntity = fileRepository.findByAccountId(accountId);

        if (existingFileEntity.isPresent()) {
            // Nếu đã có file với accountId, cập nhật nó
            FileEntity fileEntity = existingFileEntity.get();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setData(file.getBytes());
            fileRepository.save(fileEntity);
        } else {
            // Nếu chưa có file với accountId, thêm mới
            FileEntity newFileEntity = new FileEntity();
            newFileEntity.setFileName(file.getOriginalFilename());
            newFileEntity.setData(file.getBytes());
            newFileEntity.setAccountId(accountId);
            fileRepository.save(newFileEntity);
        }
    }


    public FileEntity getFile(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    public AvatarDto getAvatarByAccountId(Integer accountId) {
        Optional<FileEntity> fileEntityOptional = fileRepository.findTopByAccountIdOrderByCreatedDateDesc(accountId);

        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            return new AvatarDto(fileEntity.getFileName(), fileEntity.getData());
        }

        return null;
    }

}
