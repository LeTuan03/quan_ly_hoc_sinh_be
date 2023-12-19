package com.example.demo.repo;

import com.example.demo.entity.LOP12;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LOP12Repo extends JpaRepository<LOP12, Long> {
    List<LOP12> findByAccountId(Integer accountId);
}
