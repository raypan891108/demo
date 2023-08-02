package com.example.demo.Service;


import com.example.demo.DTO.ForgotPasswordFromRequest;
import com.example.demo.DTO.LoginFromRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entities.Member;
import com.example.demo.DTO.RegisterFromRequest;


import jakarta.servlet.http.HttpServletRequest;

@Service
public interface userService {



    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordFromRequest member) ;

    public ResponseEntity logoutUser(HttpServletRequest request) ;
    public ResponseEntity<String> userLogin(@RequestBody LoginFromRequest member) ;

    public ResponseEntity<String> userRegister(@RequestBody RegisterFromRequest newUser) ;



    
}
