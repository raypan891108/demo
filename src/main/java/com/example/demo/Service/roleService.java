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
import com.example.demo.entities.blackList;
import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class roleService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final blackListResposity blackListResposity;

    @Autowired
    public roleService(MemberRepository memberRepository, RoleRepository roleRepository, blackListResposity blackListResposity){
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.blackListResposity = blackListResposity;
    }


    public ResponseEntity role(HttpServletRequest request){

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            return ResponseEntity.ok(roleRepository.findAll( ));
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }

    }

    public ResponseEntity deleteRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            roleRepository.deleteById(id);
            return ResponseEntity.ok("delete success");
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }


    public ResponseEntity updateRole(@PathVariable("id") Integer id, @RequestBody role updateRole, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){

            role existingRoler = roleRepository.findById(id) 
            .orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));;

            if (existingRoler != null) {
                existingRoler.setAccount(updateRole.getAccount());
                existingRoler.setPassword(updateRole.getPassword());
                existingRoler.setEmail(updateRole.getEmail());
                existingRoler.setRole(updateRole.getRole());
                roleRepository.saveAndFlush(existingRoler);

                return ResponseEntity.ok("edit success");
            }else{
                return  ResponseEntity.ok("NO DATA!!!");
            }
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }

    public ResponseEntity createRole(@RequestBody role role, HttpServletRequest request) {
      
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            roleRepository.save(role);
            return ResponseEntity.ok("新增成功");
        }else{
            return ResponseEntity.ok("permission denied!!!");
        }
        
    }

     //Function

    public String getTokenBody(HttpServletRequest request){
        String authorHeader =  request.getHeader("Authorization");
        String bearer ="Bearer ";
        String token = authorHeader.substring(bearer.length());
        Claims claims = Jwts.parser().setSigningKey("ray891108")
        .parseClaimsJws(token).getBody();
        System.out.println("JWT payload:"+claims.get("sub", String.class));
        String account = claims.get("sub", String.class);
        return account;
    }

    boolean islogin(HttpServletRequest request){

        blackList user = blackListResposity.findByAccount(getTokenBody(request));

        if(user != null) return false ; else return true;
    }

    Integer getRole(HttpServletRequest request){
        Member member = memberRepository.findByaccount(getTokenBody(request));
        role role = roleRepository.findByaccount(member.getAccount());
        return role.getRole();
    };
}
