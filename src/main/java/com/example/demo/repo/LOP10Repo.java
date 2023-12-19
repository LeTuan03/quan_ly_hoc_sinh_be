package com.example.demo.repo;

import com.example.demo.entity.LOP10;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LOP10Repo extends JpaRepository<LOP10, Long> {
    List<LOP10> findByAccountId(Integer accountId);

//    @Query("SELECT p FROM Project p WHERE p.accountId = :accountId AND p.name LIKE %:name%")
//    List<Project> searchProjects(@Param("accountId") Integer accountId,
//                                 @Param("name") String name);
}
