package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.Pee;
import com.example.demo.repo.PeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pees")
public class PeeController {


    @Autowired
    private PeeRepo peeRepo;

    @GetMapping("/all")
    public ResponseEntity<List<Pee>> getAllPees() {
        try {
            List<Pee> accountList = new ArrayList<>();
            peeRepo.findAll().forEach(accountList::add);
            if(accountList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(accountList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{peeId}")
    public Object getPeeById(@PathVariable Long peeId) {
        Optional<Pee> accountData = peeRepo.findById(peeId);
        if (accountData.isPresent()) {
            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Học sinh hiện tại không có thông tin học phí", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByAccountId/{id}")
    public ResponseEntity<Object> getPeeAccountById(@PathVariable Long id) {
        Optional<Pee> accountData = peeRepo.findByAccountId(id);
        try {
            if (!accountData.isPresent()) {
                Pee newPee = new Pee();
                newPee.setAccountId(id);
                Pee createdPee = peeRepo.save(newPee);
                return new ResponseEntity<>(createdPee, HttpStatus.OK);
            }

            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<?> createPee(@RequestBody Pee pee) {
        try {

            if (pee.getAccountId() == null) {
                ErrorMessage errorMessage = new ErrorMessage("AccountId cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
            if (pee.getId() != null) {
                ErrorMessage errorMessage = new ErrorMessage("Người dùng hiện tại không thể cập nhật thông tin học phí");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            Pee createdPee = peeRepo.save(pee);
            return new ResponseEntity<>(createdPee, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{peeId}")
    public ResponseEntity<Pee> updatePee(@PathVariable Long peeId, @RequestBody Pee pee) {
        Optional<Pee> peeData = peeRepo.findById(peeId);
        if (peeData.isPresent()) {
            Pee updatedPeeData = peeData.get();
            if (pee.getAccountId() != null) {
                updatedPeeData.setAccountId(pee.getAccountId());
            }
            if (pee.getHocPhi10() != null) {
                updatedPeeData.setHocPhi10(pee.getHocPhi10());
            }
            if (pee.getHocPhi11() != null) {
                updatedPeeData.setHocPhi11(pee.getHocPhi10());
            }
            if (pee.getHocPhi12() != null) {
                updatedPeeData.setHocPhi12(pee.getHocPhi12());
            }
            if (pee.getHocPhi10DaDong() != null) {
                updatedPeeData.setHocPhi10DaDong(pee.getHocPhi10DaDong());
            }
            if (pee.getHocPhi11DaDong() != null) {
                updatedPeeData.setHocPhi11DaDong(pee.getHocPhi11DaDong());
            }
            if (pee.getHocPhi12DaDong() != null) {
                updatedPeeData.setHocPhi12DaDong(pee.getHocPhi12DaDong());
            }

            Pee peeObj = peeRepo.save(updatedPeeData);
            return new ResponseEntity<>(peeObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{peeId}")
    public ResponseEntity<Void> deletePee(@PathVariable Long peeId) {
        peeRepo.deleteById(peeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
