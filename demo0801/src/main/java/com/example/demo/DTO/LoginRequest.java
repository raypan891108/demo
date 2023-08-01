package com.example.demo.DTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoginRequest {
    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;

}
