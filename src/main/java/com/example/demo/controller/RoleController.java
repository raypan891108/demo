package com.example.demo.controller;

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

import com.example.demo.Service.roleService;
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

@Tag(name = "Role API", description = "員工管理")
@RestController
public class RoleController {


    @Autowired
    public RoleController(){}
 
    @Autowired
    roleService rs;


    @GetMapping("/role")
    public ResponseEntity role(HttpServletRequest request){

        return rs.role(request);

    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity deleteRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        
      return rs.deleteRole(id, request);
    }

    
    @Transactional //保证延迟加载的属性能够被正确初始化
    @PutMapping("/role/{id}")
    public ResponseEntity updateRole(@PathVariable("id") Integer id, @RequestBody role updateRole, HttpServletRequest request) {
        
        return rs.updateRole(id, updateRole, request);
    }

    @PostMapping("/addUser")
    public ResponseEntity createRole(@RequestBody role role, HttpServletRequest request) {
      
       return rs.createRole(role, request);
        
    }


}
