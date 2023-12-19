package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_project")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
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

    private String status;

    private String note;

    private String description;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
    public Project(String errorMessage) {
        this.name = errorMessage;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
