package com.example.demo.entity;

import com.example.demo.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "tbl_family")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = Constants.ID_MUST_NOT_EMPTY)
    private Integer accountId;

    @NotNull(message = Constants.USERNAME_EMPTY)
    @Size(min = 1, message = Constants.USERNAME_EMPTY)
    private String fullName;

    private String email;

    private String phone;

    private String nation;

    private String address;

    private String religion;

    private Date birth;

    private String relationship;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
