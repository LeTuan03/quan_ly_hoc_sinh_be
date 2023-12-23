package com.example.demo.repo;

import com.example.demo.entity.Pee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeeRepo extends JpaRepository<Pee, Long> {
    Optional<Pee> findByAccountId(Long accountId);
}
