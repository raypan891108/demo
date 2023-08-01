package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;

import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Service
public interface roleService {



    public ResponseEntity role(HttpServletRequest request);

    public ResponseEntity deleteRole(@PathVariable("id") Integer id,
            HttpServletRequest request) ;


    public ResponseEntity updateRole(@PathVariable("id") Integer id,
            @RequestBody role updateRole,
                HttpServletRequest request) ;

    public ResponseEntity createRole(@RequestBody role role,
            HttpServletRequest request) ;


}
