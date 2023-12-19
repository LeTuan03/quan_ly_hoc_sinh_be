package com.example.demo.controller;

import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.Account;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountRepo accountsRepo;


    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        try {
            List<Account> accountList = new ArrayList<>();
            accountsRepo.findAll().forEach(accountList::add);
            if(accountList.isEmpty()) {
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(accountList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListAccounts")
    public ResponseEntity<List<AccountResponseDTO>> getListAccounts() {
        try {
            List<AccountResponseDTO> accountList = new ArrayList<>();
            accountsRepo.findAll().forEach(account -> {
                AccountResponseDTO dto = new AccountResponseDTO();
                dto.setId(account.getId());
                dto.setUsername(account.getUsername());
                dto.setRole(account.getRole());
                accountList.add(dto);
            });

            if (accountList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(accountList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAccountById/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> accountData = accountsRepo.findById(id);

        if(accountData.isPresent()) {
            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addAccount")
    public ResponseEntity<?>  addAccount(@RequestBody Account accounts) {

        if (accounts.getRole() == null) {
            ErrorMessage errorMessage = new ErrorMessage("Role cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (accounts.getUsername() == null) {
            ErrorMessage errorMessage = new ErrorMessage("Username cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (accounts.getPassword() == null) {
            ErrorMessage errorMessage = new ErrorMessage("Password cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (accountsRepo.existsByUsername(accounts.getUsername())) {
            ErrorMessage errorMessage = new ErrorMessage("Username is already taken");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Account accountObj = accountsRepo.save(accounts);

        return new ResponseEntity<>(accountObj, HttpStatus.OK);
    }

    @PostMapping("updateAccountById/{id}")
    public ResponseEntity<Account> updateAccountById(@PathVariable Long id, @RequestBody Account newAccountData) {
        Optional<Account> oldAccountData = accountsRepo.findById(id);

        if (oldAccountData.isPresent()) {
            Account updatedAccountData = oldAccountData.get();
            if (newAccountData.getUsername() != null) {
                updatedAccountData.setUsername(newAccountData.getUsername());
            }
            if (newAccountData.getPassword() != null) {
                updatedAccountData.setPassword(newAccountData.getPassword());
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
            if (newAccountData.getStatus() != null) {
                updatedAccountData.setStatus(newAccountData.getStatus());
            }
            if (newAccountData.getAddress() != null) {
                updatedAccountData.setAddress(newAccountData.getAddress());
            }
            if (newAccountData.getNation() != null) {
                updatedAccountData.setNation(newAccountData.getNation());
            }
            Account accountObj = accountsRepo.save(updatedAccountData);
            return new ResponseEntity<>(accountObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/deleteAccountById/{id}")
    public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable Long id) {
        accountsRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {

            if (loginRequest.getUsername() == null) {
                ErrorMessage errorMessage = new ErrorMessage("Username cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
            if (loginRequest.getPassword() == null) {
                ErrorMessage errorMessage = new ErrorMessage("Password cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            Account account = accountsRepo.findByUsernameAndPassword(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            if (account != null) {
                return new ResponseEntity<>(account, HttpStatus.OK);
            } else {
                ErrorMessage errorMessage = new ErrorMessage("Account or password is incorrect!!");
                return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchAcc")
    public ResponseEntity<List<Account>> searchAcc(@RequestParam("query") String query){
        List<Account> projects = accountsRepo.searchAcc(query);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/getMember/{role}")
    public ResponseEntity<List<AccountResponseDTO>> getMember(@PathVariable String role) {
        try {
            List<Account> memberList = accountsRepo.findByRole(role);

            if (memberList == null || memberList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<AccountResponseDTO> responseList = convertToResponseDTOList(memberList);

            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<AccountResponseDTO> convertToResponseDTOList(List<Account> accountList) {
        List<AccountResponseDTO> responseList = new ArrayList<>();
        for (Account account : accountList) {
            AccountResponseDTO dto = new AccountResponseDTO();
            dto.setId(account.getId());
            dto.setUsername(account.getUsername());
            dto.setRole(account.getRole());
            dto.setPhone(account.getPhone());
            dto.setEmail(account.getEmail());
            responseList.add(dto);
        }
        return responseList;
    }


}
