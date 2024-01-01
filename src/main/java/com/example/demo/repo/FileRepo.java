package com.example.demo.repo;

import com.example.demo.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepo extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findTopByAccountIdOrderByCreatedDateDesc(Integer accountId);

}