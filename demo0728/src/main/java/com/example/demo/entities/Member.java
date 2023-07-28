package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Entity
@Data
@Schema(description = "會員")
@Table(name="Member")
public class Member {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;


    @Column(name="userName")
    private String userName;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;


    public Member(){}

    

}
