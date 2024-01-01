package com.example.demo.entity;

import com.example.demo.config.Constants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_account")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = Constants.USERNAME_EMPTY)
    @Size(min = 1, message = Constants.USERNAME_EMPTY)
    private String username;

    @NotNull(message = Constants.PASS_NOT_EMPTY)
    @Size(min = 1, message = Constants.PASS_NOT_EMPTY)
    private String password;

    @NotNull(message = Constants.ROLE_NOT_EMPTY)
    private String role;

    private String email;

    private String phone;

    private String nation;

    private String address;

    private String religion;

    private Date birth;

    private Long status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Classes> classes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LOP10> lop10;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LOP11> lop11;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LOP12> lop12;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Family> families;

}