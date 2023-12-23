package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tbl_task")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "TaskName cannot be null")
    private String taskName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @NotNull(message = "ProjectId cannot be null")
    private Integer projectId;

    @NotNull(message = "Project name cannot be null")
    private String projectName;

    @NotNull(message = "UserId cannot be null")
    private Long userId;

    @NotNull(message = "User Name cannot be null")
    private String userName;

    @NotNull(message = "Homeroom teacher cannot be null")
    private String homeroomTeacher;

    private String percentComplete;

    private String note;

    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
