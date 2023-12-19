package com.example.demo.repo;

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Integer projectId);

    List<Task> findByUserId(Long userId);
}
