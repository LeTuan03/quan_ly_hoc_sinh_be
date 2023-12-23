package com.example.demo.repo;

import com.example.demo.entity.MemberStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<MemberStudent, Long> {
    List<MemberStudent> findByProjectId(Integer projectId);

    List<MemberStudent> findByUserId(Long userId);
}
