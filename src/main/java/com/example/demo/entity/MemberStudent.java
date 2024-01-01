package com.example.demo.entity;

import com.example.demo.config.Constants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tbl_member")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = Constants.CLASS_NAME)
    private String taskName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @NotNull(message = Constants.ID_AND_NAME_NOT_EMPTY)
    private Integer projectId;

    @NotNull(message = Constants.CLASS_NAME)
    private String projectName;

    @NotNull(message = Constants.ID_MUST_NOT_EMPTY)
    private Long userId;

    @NotNull(message = Constants.USERNAME_EMPTY)
    private String userName;

    @NotNull(message = Constants.HOMEROOM_TEACHER)
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
