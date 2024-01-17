package com.example.demo.repo;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//tạo ra các phương thức
public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByPassword(String password);

//    định nghĩa một truy vấn JPQL trả về danh sách các tài khoản
    @Query("SELECT p FROM Account p WHERE " +
            "p.username LIKE CONCAT('%',:query, '%')")
    List<Account> searchAcc(String query);

//    trả về danh sách các tài khoản có role giống với giá trị truyền vào.
    @Query("SELECT p FROM Account p WHERE " +
            "p.username LIKE CONCAT('%',:query, '%') AND p.role = :role")
    List<Account> searchAccRoleStudent(@Param("query") String query, @Param("role") String role);

    List<Account> findByRole(String role);

}
