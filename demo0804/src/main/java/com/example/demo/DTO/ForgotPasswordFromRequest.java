package com.example.demo.DTO;

import lombok.Data;

@Data
public class ForgotPasswordFromRequest {
    private String account;

    private String password;

    private String email;
}
