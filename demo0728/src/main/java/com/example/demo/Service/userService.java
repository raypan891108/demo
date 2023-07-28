package com.example.demo.Service;


import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.entities.Register;

import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Service
public interface userService {



    public ResponseEntity<String> forgotPassword(@RequestBody Member member) ;

    public ResponseEntity logoutUser(HttpServletRequest request) ;
    public ResponseEntity<String> userLogin(@RequestBody role role) ;

    public ResponseEntity<String> userRegister(@RequestBody Register newUser) ;



    
}
