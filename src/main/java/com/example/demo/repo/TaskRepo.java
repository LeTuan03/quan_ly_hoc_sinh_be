package com.example.demo.repo;

import com.example.demo.entity.MemberStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepo extends JpaRepository<MemberStudent, Long> {
    List<MemberStudent> findByProjectId(Integer projectId);

    List<MemberStudent> findByUserId(Long userId);
    @Query("SELECT c FROM MemberStudent c WHERE " +
            "c.projectName LIKE CONCAT('%', :query, '%') " +
            "AND (:userId IS NULL OR c.userId = :userId)")
    List<MemberStudent> searchByName(@Param("query") String query, @Param("userId") Long userId);

}
