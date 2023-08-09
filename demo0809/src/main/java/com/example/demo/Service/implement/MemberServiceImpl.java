package com.example.demo.Service.implement;

import com.example.demo.DTO.AddMemberForRequest;
import com.example.demo.Service.MemberService;
import com.example.demo.Service.RedisService;
import com.example.demo.entities.Member;
//import com.example.demo.entities.role;
import com.example.demo.entities.Role;
import com.example.demo.repositories.MemberRepository;
//import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.RoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  RedisService redisService;

    public ResponseEntity getMemberList(HttpServletRequest request){

        if(!function.islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(function.getRole(request) == 1){
            System.out.println(memberRepository.findAll());
            return ResponseEntity.ok(memberRepository.findAll( ));
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }

    }


    public ResponseEntity deleteMember(@PathVariable("id") Integer id, HttpServletRequest request) {

        if(!function.islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(function.getRole(request) == 1){

            Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
            member.getRoles().clear();
            memberRepository.deleteById(id);
            return ResponseEntity.ok("delete success");
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }



    public ResponseEntity updateMember(@PathVariable("id") Integer id, @RequestBody AddMemberForRequest updateMember, HttpServletRequest request) {

        if(!function.islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(function.getRole(request) == 1){

            Member existingMember = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

            if (existingMember != null) {
                existingMember.setAccount(updateMember.getAccount());
                existingMember.setPassword(updateMember.getPassword());
                existingMember.setUserName(updateMember.getUserName());
                existingMember.setPhone(updateMember.getPhone());
                existingMember.setEmail(updateMember.getEmail());
                existingMember.getRoles().clear();
                IsPresentWithRoles(updateMember, existingMember);
                memberRepository.save(existingMember);

                return ResponseEntity.ok("edit success");
            }else{
                return  ResponseEntity.ok("NO DATA!!!");
            }
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }

    public ResponseEntity SearchMember(@PathVariable("account") String account, HttpServletRequest request) {

        if(!function.islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(function.getRole(request) == 1){
            Member member = memberRepository.findByaccount(account);
            if(member != null)
                return ResponseEntity.ok(member);
            else
                return ResponseEntity.ok("NO DATA!!!!");

        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }


    public ResponseEntity createMember(@RequestBody AddMemberForRequest member, HttpServletRequest request) {

        if(!function.islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(function.getRole(request) == 1){
            Member newMember = new Member();
            newMember.setAccount(member.getAccount());
            newMember.setPhone(member.getPhone());
            newMember.setPassword(member.getPassword());
            newMember.setUserName(member.getUserName());
            newMember.setEmail(member.getEmail());
            IsPresentWithRoles(member, newMember);

            memberRepository.save(newMember);

            //memberRepository.save(member);
            return ResponseEntity.ok("新增成功");
        }else{
            return ResponseEntity.ok("permission denied!!!");
        }

    }

    public void IsPresentWithRoles(AddMemberForRequest member, Member newMember){
        Set<String> roles = member.getRoles();

        for(String r1 : roles){
            Role r = roleRepository.findByRoleName(r1);
            if(r == null) {
                r = new Role();
                r.setRoleName(r1);
                roleRepository.save(r);
            }
            r = roleRepository.findByRoleName(r1);
            newMember.getRoles().add(r);
        }
    }





}
