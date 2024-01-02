package com.example.demo.controller;

import com.example.demo.config.Constants;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.repo.MemberStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/student-in-class")
public class MemberStudentController {

    @Autowired
    private MemberStudent memberStudent;

    @GetMapping("/getByProjectId/{id}")
    public ResponseEntity<List<com.example.demo.entity.MemberStudent>> getByProjectId(@PathVariable Integer id) {
        List<com.example.demo.entity.MemberStudent> memberStudentData = memberStudent.findByProjectId(id);

        if (!memberStudentData.isEmpty()) {
            return new ResponseEntity<>(memberStudentData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<com.example.demo.entity.MemberStudent> getById(@PathVariable Long id) {
        Optional<com.example.demo.entity.MemberStudent> taskData = memberStudent.findById(id);

        if(taskData.isPresent()) {
            return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<com.example.demo.entity.MemberStudent>> getByUserId(@PathVariable Long userId) {
        List<com.example.demo.entity.MemberStudent> memberStudentData = memberStudent.findByUserId(userId);

        if (!memberStudentData.isEmpty()) {
            return new ResponseEntity<>(memberStudentData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody com.example.demo.entity.MemberStudent memberStudent) {

        if (memberStudent.getProjectId() == null) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.USERNAME_EMPTY);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (memberStudent.getCode() == null) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.USERNAME_EMPTY);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        com.example.demo.entity.MemberStudent memberStudentObj = this.memberStudent.save(memberStudent);

        return new ResponseEntity<>(memberStudentObj, HttpStatus.OK);
    }

    @PostMapping("/adds")
    public ResponseEntity<?> addTasks(@RequestBody List<com.example.demo.entity.MemberStudent> memberStudents) {
        List<com.example.demo.entity.MemberStudent> savedMemberStudents = new ArrayList<>();

        try {
            Set<Long> userIdSet = new HashSet<>(); // Sử dụng Set để lưu trữ các userId đã xuất hiện

            for (com.example.demo.entity.MemberStudent memberStudent : memberStudents) {
                if (memberStudent.getProjectId() == null || memberStudent.getCode() == null) {
                    ErrorMessage errorMessage = new ErrorMessage(Constants.ID_CLASS_AND_NAME_NOT_EMPTY);
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
                }

                // Kiểm tra trùng userId
                if (!userIdSet.add(memberStudent.getUserId())) {
                    ErrorMessage errorMessage = new ErrorMessage(Constants.STUDENT_IN_LIST);
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
                }

                if (memberStudent.getId() != null) {
                    Optional<com.example.demo.entity.MemberStudent> existingTask = this.memberStudent.findById(memberStudent.getId());

                    if (existingTask.isPresent()) {
                        com.example.demo.entity.MemberStudent updatedMemberStudent = existingTask.get();
                        updatedMemberStudent.setCode(memberStudent.getCode());
                        updatedMemberStudent.setProjectId(memberStudent.getProjectId());
                        updatedMemberStudent.setEndDate(memberStudent.getEndDate());
                        updatedMemberStudent.setStartDate(memberStudent.getStartDate());
                        updatedMemberStudent.setUserId(memberStudent.getUserId());
                        updatedMemberStudent.setUserName(memberStudent.getUserName());
                        updatedMemberStudent.setProjectName(memberStudent.getProjectName());
                        updatedMemberStudent.setHomeroomTeacher(memberStudent.getHomeroomTeacher());
                        updatedMemberStudent.setNote(memberStudent.getNote());
                        updatedMemberStudent.setUpdatedAt(new Date());

                        savedMemberStudents.add(this.memberStudent.save(updatedMemberStudent));
                        continue;
                    }
                }

                savedMemberStudents.add(this.memberStudent.save(memberStudent));
            }

            return new ResponseEntity<>(savedMemberStudents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(Constants.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody com.example.demo.entity.MemberStudent memberStudent) {
        Optional<com.example.demo.entity.MemberStudent> oldTask = this.memberStudent.findById(id);

        if (oldTask.isPresent()) {
            com.example.demo.entity.MemberStudent updatedMemberStudentData = oldTask.get();

            if (memberStudent.getCode() == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.USERNAME_EMPTY);
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            if (memberStudent.getProjectId() == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.USERNAME_EMPTY);
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            updatedMemberStudentData.setCode(memberStudent.getCode());
            updatedMemberStudentData.setProjectId(memberStudent.getProjectId());
            updatedMemberStudentData.setEndDate(memberStudent.getEndDate());
            updatedMemberStudentData.setNote(memberStudent.getNote());
            updatedMemberStudentData.setStartDate(memberStudent.getStartDate());
            updatedMemberStudentData.setProjectName(memberStudent.getProjectName());
            updatedMemberStudentData.setHomeroomTeacher(memberStudent.getHomeroomTeacher());

            com.example.demo.entity.MemberStudent memberStudentObj = this.memberStudent.save(updatedMemberStudentData);
            return new ResponseEntity<>(memberStudentObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        memberStudent.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/searchClasses")
    public ResponseEntity<List<com.example.demo.entity.MemberStudent>> searchAccounts(
            @RequestParam String query,
            @RequestParam(required = false) Long userId) {
        List<com.example.demo.entity.MemberStudent> classes = memberStudent.searchByName(query, userId);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }
}
