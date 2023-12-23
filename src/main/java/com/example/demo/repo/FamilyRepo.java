package com.example.demo.repo;

import com.example.demo.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyRepo extends JpaRepository<Family, Long> {

    List<Family> findByAccountId(Long accountId);

}
