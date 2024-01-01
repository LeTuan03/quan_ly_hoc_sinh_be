package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import com.example.demo.config.Constants;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tbl_files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @NotNull(message = Constants.ID_MUST_NOT_EMPTY)
    private Integer accountId;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Lob
    private byte[] data;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }
}
