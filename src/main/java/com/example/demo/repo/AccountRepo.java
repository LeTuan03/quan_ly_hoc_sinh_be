package com.example.demo.repo;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByPassword(String password);

    @Query("SELECT p FROM Account p WHERE " +
            "p.username LIKE CONCAT('%',:query, '%')")
    List<Account> searchAcc(String query);

    List<Account> findByRole(String role);

}
