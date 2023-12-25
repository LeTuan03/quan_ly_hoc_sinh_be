package com.example.demo.repo;

import com.example.demo.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassesRepo extends JpaRepository<Classes, Long> {
    List<Classes> findByAccountId(Integer accountId);

    @Query("SELECT p FROM Classes p WHERE p.accountId = :accountId AND p.name LIKE %:name%")
    List<Classes> searchProjects(@Param("accountId") Integer accountId,
                                 @Param("name") String name);

    @Query("SELECT c FROM Classes c WHERE " +
            "c.name LIKE CONCAT('%', :query, '%') " +
            "AND (:accountId IS NULL OR c.accountId = :accountId)")
    List<Classes> searchByName(@Param("query") String query, @Param("accountId") Integer accountId);

}

