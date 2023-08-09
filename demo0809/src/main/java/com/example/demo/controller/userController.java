package com.example.demo.controller;

import com.example.demo.DTO.ForgotPasswordFromRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.userService;
import com.example.demo.entities.Member;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "Forgot Password API", description = "忘記密碼")
public class userController {


    
    @Autowired
    userService urs;

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordFromRequest member) {
        return urs.forgotPassword(member);
        
    }


}
