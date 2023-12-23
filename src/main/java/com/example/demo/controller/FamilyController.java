package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.Family;
import com.example.demo.entity.MemberStudent;
import com.example.demo.repo.FamilyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/family")
@CrossOrigin(origins = "*")
public class FamilyController {

    @Autowired
    private FamilyRepo familyRepo;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Family family) {

        if (family.getAccountId() == null) {
            ErrorMessage errorMessage = new ErrorMessage("AccountId cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        if (family.getFullName() == null) {
            ErrorMessage errorMessage = new ErrorMessage("Tên không được để trống");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Family familyObj = familyRepo.save(family);

        return new ResponseEntity<>(familyObj, HttpStatus.OK);
    }

    @GetMapping("/getByAccountId/{id}")
    public ResponseEntity<?> getByAccountId(@PathVariable Long id) {
        try {
            List<Family> familyList = familyRepo.findByAccountId(id);

            if (familyList.isEmpty()) {
                ErrorMessage errorMessage = new ErrorMessage("No families found for the given account ID");
                return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(familyList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("updateById/{id}")
    public ResponseEntity<Family> updateAccountById(@PathVariable Long id, @RequestBody Family newAccountData) {
        Optional<Family> oldAccountData = familyRepo.findById(id);

        if (oldAccountData.isPresent()) {
            Family updatedAccountData = oldAccountData.get();
            if (newAccountData.getFullName() != null) {
                updatedAccountData.setFullName(newAccountData.getFullName());
            }
            if (newAccountData.getEmail() != null) {
                updatedAccountData.setEmail(newAccountData.getEmail());
            }
            if (newAccountData.getPhone() != null) {
                updatedAccountData.setPhone(newAccountData.getPhone());
            }
            if (newAccountData.getAddress() != null) {
                updatedAccountData.setAddress(newAccountData.getAddress());
            }
            if (newAccountData.getBirth() != null) {
                updatedAccountData.setBirth(newAccountData.getBirth());
            }
            if (newAccountData.getAddress() != null) {
                updatedAccountData.setAddress(newAccountData.getAddress());
            }
            if (newAccountData.getNation() != null) {
                updatedAccountData.setNation(newAccountData.getNation());
            }
            if (newAccountData.getRelationship() != null) {
                updatedAccountData.setRelationship(newAccountData.getRelationship());
            }
            if (newAccountData.getReligion() != null) {
                updatedAccountData.setReligion(newAccountData.getReligion());
            }
            Family familyObj = familyRepo.save(updatedAccountData);
            return new ResponseEntity<>(familyObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        familyRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/adds")
    public ResponseEntity<?> addTasks(@RequestBody List<Family> memberStudents) {
        List<Family> savedMemberStudents = new ArrayList<>();

        try {
            for (Family memberStudent : memberStudents) {
                if (memberStudent.getAccountId() == null || memberStudent.getFullName() == null) {
                    ErrorMessage errorMessage = new ErrorMessage("Ib học sinh và tên không được để trống");
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }

                if (memberStudent.getId() != null) {
                    Optional<Family> existingTask = familyRepo.findById(memberStudent.getId());

                    if (existingTask.isPresent()) {
                        Family updatedMemberStudent = existingTask.get();
                        if (updatedMemberStudent.getFullName() != null) {
                            updatedMemberStudent.setFullName(memberStudent.getFullName());
                        }
                        if (memberStudent.getEmail() != null) {
                            updatedMemberStudent.setEmail(memberStudent.getEmail());
                        }
                        if (memberStudent.getPhone() != null) {
                            updatedMemberStudent.setPhone(memberStudent.getPhone());
                        }
                        if (memberStudent.getAddress() != null) {
                            updatedMemberStudent.setAddress(memberStudent.getAddress());
                        }
                        if (memberStudent.getBirth() != null) {
                            updatedMemberStudent.setBirth(memberStudent.getBirth());
                        }
                        if (memberStudent.getAddress() != null) {
                            updatedMemberStudent.setAddress(memberStudent.getAddress());
                        }
                        if (memberStudent.getNation() != null) {
                            updatedMemberStudent.setNation(memberStudent.getNation());
                        }
                        if (memberStudent.getRelationship() != null) {
                            updatedMemberStudent.setRelationship(memberStudent.getRelationship());
                        }
                        if (memberStudent.getReligion() != null) {
                            updatedMemberStudent.setReligion(memberStudent.getReligion());
                        }
                        savedMemberStudents.add(familyRepo.save(updatedMemberStudent));
                        continue;
                    }
                }

                savedMemberStudents.add(familyRepo.save(memberStudent));
            }

            return new ResponseEntity<>(savedMemberStudents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("Có lỗi xảy ra"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
