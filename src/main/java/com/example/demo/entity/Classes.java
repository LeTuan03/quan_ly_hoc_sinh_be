package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_classes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "AccountId cannot be null")
    private Integer accountId;

    private Integer memberId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private Date startDate;

    private  Date endDate;

    private String createdBy;

    private Date updatedAt;

    private String note;

    private String description;

    private String classroom;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberStudent> memberStudents = new ArrayList<>();
    public Classes(String errorMessage) {
        this.name = errorMessage;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
