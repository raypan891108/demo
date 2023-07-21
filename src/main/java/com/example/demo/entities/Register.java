package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Table(name="register")
public class Register {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    
    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;


    @Column(name="Againpassword")
    private String Againpassword;

    @Column(name="userName")
    private String userName;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;


    public Register(){}

 
}

