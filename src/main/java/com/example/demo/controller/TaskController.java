package com.example.demo.controller;


import com.example.demo.entity.Account;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.Task;
import com.example.demo.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskRepo taskRepo;

    @GetMapping("/getByProjectId/{id}")
    public ResponseEntity<List<Task>> getByProjectId(@PathVariable Integer id) {
        List<Task> taskData = taskRepo.findByProjectId(id);

        if (!taskData.isEmpty()) {
            return new ResponseEntity<>(taskData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        Optional<Task> taskData = taskRepo.findById(id);

        if(taskData.isPresent()) {
            return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<Task>> getByUserId(@PathVariable Long userId) {
        List<Task> taskData = taskRepo.findByUserId(userId);

        if (!taskData.isEmpty()) {
            return new ResponseEntity<>(taskData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody Task task) {

        if (task.getProjectId() == null) {
            ErrorMessage errorMessage = new ErrorMessage("ProjectId cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (task.getTaskName() == null) {
            ErrorMessage errorMessage = new ErrorMessage("Taskname cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Task taskObj = taskRepo.save(task);

        return new ResponseEntity<>(taskObj, HttpStatus.OK);
    }

    @PostMapping("/adds")
    public ResponseEntity<?> addTasks(@RequestBody List<Task> tasks) {
        List<Task> savedTasks = new ArrayList<>();

        try {
            for (Task task : tasks) {
                if (task.getProjectId() == null || task.getTaskName() == null) {
                    ErrorMessage errorMessage = new ErrorMessage("ProjectId and Taskname cannot be null");
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }

                if (task.getId() != null) {
                    Optional<Task> existingTask = taskRepo.findById(task.getId());

                    if (existingTask.isPresent()) {
                        Task updatedTask = existingTask.get();
                        updatedTask.setTaskName(task.getTaskName());
                        updatedTask.setProjectId(task.getProjectId());
                        updatedTask.setDueDate(task.getDueDate());
                        updatedTask.setUserId(task.getUserId());
                        updatedTask.setUserName(task.getUserName());
                        updatedTask.setPercentComplete(task.getPercentComplete());
                        updatedTask.setProjectName(task.getProjectName());
                        updatedTask.setHomeroomTeacher(task.getHomeroomTeacher());
                        updatedTask.setNote(task.getNote());
                        updatedTask.setUpdatedAt(new Date());

                        savedTasks.add(taskRepo.save(updatedTask));
                        continue;
                    }
                }

                savedTasks.add(taskRepo.save(task));
            }

            return new ResponseEntity<>(savedTasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody Task task) {
        Optional<Task> oldTask = taskRepo.findById(id);

        if (oldTask.isPresent()) {
            Task updatedTaskData = oldTask.get();

            if (task.getTaskName() == null) {
                ErrorMessage errorMessage = new ErrorMessage("Task name cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            if (task.getProjectId() == null) {
                ErrorMessage errorMessage = new ErrorMessage("ProjectId cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            updatedTaskData.setTaskName(task.getTaskName());
            updatedTaskData.setProjectId(task.getProjectId());
            updatedTaskData.setDueDate(task.getDueDate());
            updatedTaskData.setNote(task.getNote());
            updatedTaskData.setStartDate(task.getStartDate());
            updatedTaskData.setDueDate(task.getDueDate());
            updatedTaskData.setProjectName(task.getProjectName());
            updatedTaskData.setHomeroomTeacher(task.getHomeroomTeacher());
            updatedTaskData.setPercentComplete(task.getPercentComplete());

            Task taskObj = taskRepo.save(updatedTaskData);
            return new ResponseEntity<>(taskObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        taskRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
