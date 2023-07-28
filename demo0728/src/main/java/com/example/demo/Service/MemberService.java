package com.example.demo.Service;



import org.springframework.stereotype.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;

import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface MemberService {
   


    public ResponseEntity getMemberList(HttpServletRequest request);
    public ResponseEntity deleteMember(@PathVariable("id") Integer id,
                                       HttpServletRequest request);
    public ResponseEntity updateMember(@PathVariable("id") Integer id,
                                       @RequestBody Member updateMember,
                                       HttpServletRequest request);
    public ResponseEntity SearchMember(@PathVariable("account") String account,
                                       HttpServletRequest request) ;

    public ResponseEntity createMember(@RequestBody Member member,
                                       HttpServletRequest request);


}






