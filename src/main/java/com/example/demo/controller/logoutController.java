package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.userService;
import com.example.demo.entities.Member;
import com.example.demo.entities.blackList;
import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "Logout API", description = "登出")
public class logoutController {
  

    @Autowired
    public logoutController(){
        
    }

    @Autowired
    userService urs;

    @PostMapping("/userlogout")
    public ResponseEntity logoutUser(HttpServletRequest request) {
        return urs.logoutUser(request);
        
       
       
        
    }


  

}

