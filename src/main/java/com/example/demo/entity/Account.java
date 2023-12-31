package com.example.demo.entity;

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

    @NotNull(message = "Username cannot be null")
    @Size(min = 1, message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 1, message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Role cannot be null")
    private String role;

    private String email;

    private String phone;

    private String nation;

    private String address;

    private Date birth;

    private Long status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Project> projects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LOP10> lop10;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LOP11> lop11;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LOP12> lop12;
}