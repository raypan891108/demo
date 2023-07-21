package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.userService;
import com.example.demo.entities.Register;



import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Register API", description = "註冊")
public class registerContorller {
    
    


    @Autowired
    public registerContorller(){}    
    

    @Autowired
    userService urs;
    
    
    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody Register newUser) {
        return urs.userRegister(newUser);
       
    }
}
