package com.example.demo.entity;

import com.example.demo.config.Constants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tbl_11")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LOP11 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = Constants.ID_MUST_NOT_EMPTY)
    private Integer accountId;

    private String maths;
    private String physics;
    private String chemistry;
    private String biology;
    private String geography;
    private String fineArt;
    private String literature;
    private String classification;
    private String history;
    private String english;
    private String civicEducation;
    private String Conduct;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

}