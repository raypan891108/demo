package com.example.demo.controller;


import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Service.userService;
import com.example.demo.entities.Member;
import com.example.demo.entities.blackList;
import com.example.demo.entities.role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "Login API", description = "登入")
public class loginContorller {


    @Autowired
    public loginContorller(){}

  
    @Autowired
    userService urs;
    
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "login success", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "401", description = "No authentication", 
            content = @Content) })
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody role role) {
        return urs.userLogin(role);
        
    }

    
   

}
