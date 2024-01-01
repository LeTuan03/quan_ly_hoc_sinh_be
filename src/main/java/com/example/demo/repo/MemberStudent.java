package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberStudent extends JpaRepository<com.example.demo.entity.MemberStudent, Long> {
    List<com.example.demo.entity.MemberStudent> findByProjectId(Integer projectId);

    List<com.example.demo.entity.MemberStudent> findByUserId(Long userId);
    @Query("SELECT c FROM MemberStudent c WHERE " +
            "c.projectName LIKE CONCAT('%', :query, '%') " +
            "AND (:userId IS NULL OR c.userId = :userId)")
    List<com.example.demo.entity.MemberStudent> searchByName(@Param("query") String query, @Param("userId") Long userId);

}
