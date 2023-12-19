package com.example.demo.repo;

import com.example.demo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findByAccountId(Integer accountId);

    @Query("SELECT p FROM Project p WHERE p.accountId = :accountId AND p.name LIKE %:name%")
    List<Project> searchProjects(@Param("accountId") Integer accountId,
                                 @Param("name") String name);
}
