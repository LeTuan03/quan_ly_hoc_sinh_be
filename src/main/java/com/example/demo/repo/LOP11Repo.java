package com.example.demo.repo;

import com.example.demo.entity.LOP11;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LOP11Repo extends JpaRepository<LOP11, Long> {
    List<LOP11> findByAccountId(Integer accountId);
}
