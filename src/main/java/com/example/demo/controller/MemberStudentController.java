package com.example.demo.controller;


import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.MemberStudent;
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
public class MemberStudentController {

    @Autowired
    private TaskRepo taskRepo;

    @GetMapping("/getByProjectId/{id}")
    public ResponseEntity<List<MemberStudent>> getByProjectId(@PathVariable Integer id) {
        List<MemberStudent> memberStudentData = taskRepo.findByProjectId(id);

        if (!memberStudentData.isEmpty()) {
            return new ResponseEntity<>(memberStudentData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<MemberStudent> getById(@PathVariable Long id) {
        Optional<MemberStudent> taskData = taskRepo.findById(id);

        if(taskData.isPresent()) {
            return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<MemberStudent>> getByUserId(@PathVariable Long userId) {
        List<MemberStudent> memberStudentData = taskRepo.findByUserId(userId);

        if (!memberStudentData.isEmpty()) {
            return new ResponseEntity<>(memberStudentData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody MemberStudent memberStudent) {

        if (memberStudent.getProjectId() == null) {
            ErrorMessage errorMessage = new ErrorMessage("Tên không được để trống");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (memberStudent.getTaskName() == null) {
            ErrorMessage errorMessage = new ErrorMessage("Tên không được để trống");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        MemberStudent memberStudentObj = taskRepo.save(memberStudent);

        return new ResponseEntity<>(memberStudentObj, HttpStatus.OK);
    }

    @PostMapping("/adds")
    public ResponseEntity<?> addTasks(@RequestBody List<MemberStudent> memberStudents) {
        List<MemberStudent> savedMemberStudents = new ArrayList<>();

        try {
            for (MemberStudent memberStudent : memberStudents) {
                if (memberStudent.getProjectId() == null || memberStudent.getTaskName() == null) {
                    ErrorMessage errorMessage = new ErrorMessage("Ib lớp và mã học sinh không được để trống");
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }

                if (memberStudent.getId() != null) {
                    Optional<MemberStudent> existingTask = taskRepo.findById(memberStudent.getId());

                    if (existingTask.isPresent()) {
                        MemberStudent updatedMemberStudent = existingTask.get();
                        updatedMemberStudent.setTaskName(memberStudent.getTaskName());
                        updatedMemberStudent.setProjectId(memberStudent.getProjectId());
                        updatedMemberStudent.setEndDate(memberStudent.getEndDate());
                        updatedMemberStudent.setStartDate(memberStudent.getStartDate());
                        updatedMemberStudent.setUserId(memberStudent.getUserId());
                        updatedMemberStudent.setUserName(memberStudent.getUserName());
                        updatedMemberStudent.setPercentComplete(memberStudent.getPercentComplete());
                        updatedMemberStudent.setProjectName(memberStudent.getProjectName());
                        updatedMemberStudent.setHomeroomTeacher(memberStudent.getHomeroomTeacher());
                        updatedMemberStudent.setNote(memberStudent.getNote());
                        updatedMemberStudent.setUpdatedAt(new Date());

                        savedMemberStudents.add(taskRepo.save(updatedMemberStudent));
                        continue;
                    }
                }

                savedMemberStudents.add(taskRepo.save(memberStudent));
            }

            return new ResponseEntity<>(savedMemberStudents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("Có lỗi xảy ra"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody MemberStudent memberStudent) {
        Optional<MemberStudent> oldTask = taskRepo.findById(id);

        if (oldTask.isPresent()) {
            MemberStudent updatedMemberStudentData = oldTask.get();

            if (memberStudent.getTaskName() == null) {
                ErrorMessage errorMessage = new ErrorMessage("Task name cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            if (memberStudent.getProjectId() == null) {
                ErrorMessage errorMessage = new ErrorMessage("ProjectId cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            updatedMemberStudentData.setTaskName(memberStudent.getTaskName());
            updatedMemberStudentData.setProjectId(memberStudent.getProjectId());
            updatedMemberStudentData.setEndDate(memberStudent.getEndDate());
            updatedMemberStudentData.setNote(memberStudent.getNote());
            updatedMemberStudentData.setStartDate(memberStudent.getStartDate());
            updatedMemberStudentData.setProjectName(memberStudent.getProjectName());
            updatedMemberStudentData.setHomeroomTeacher(memberStudent.getHomeroomTeacher());
            updatedMemberStudentData.setPercentComplete(memberStudent.getPercentComplete());

            MemberStudent memberStudentObj = taskRepo.save(updatedMemberStudentData);
            return new ResponseEntity<>(memberStudentObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        taskRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
