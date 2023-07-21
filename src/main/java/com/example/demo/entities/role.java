package com.example.demo.entities;
import jakarta.persistence.*;

import lombok.Data;


@Entity
@Data
@Table(name="role")
public class role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private Integer role;

    public role(){}

  
}
