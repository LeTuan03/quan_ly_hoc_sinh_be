package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_pee")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private Float hocPhi10;
    private Float hocPhi11;
    private Float hocPhi12;

    private Float hocPhi10DaDong;
    private Float hocPhi11DaDong;
    private Float hocPhi12DaDong;
}