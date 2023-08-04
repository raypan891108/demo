package com.example.demo.Service.implement;

import com.example.demo.entities.Member;
//import com.example.demo.entities.role;
import com.example.demo.entities.Role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class function {
//
    @Autowired
    private static MemberRepository memberRepository;

    public function(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public static Integer getRole(HttpServletRequest request){
        Member member = memberRepository.findByaccount(getTokenBody(request));
        Set<Role> roles = member.getRoles();
        int roleINT = 0;
        for(Role r : roles) {
            //System.out.println(r.getRoleName()+"ok");
            if (r.getRoleName().contains("Admin")) {roleINT =  1;}
            else if (r.getRoleName().contains("User")){ roleINT =  2;}
        }

        return roleINT;
    };

    public static Integer getRoleByBook(HttpServletRequest request){
        Member member = memberRepository.findByaccount(getTokenBody(request));
        Set<Role> roles = member.getRoles();
        int roleINT = 0;
        for(Role r : roles) {
            //System.out.println(r.getRoleName()+"ok");
            if (r.getRoleName().contains("Admin")) {roleINT =  1;}
            else if (r.getRoleName().contains("User")){ roleINT =  1;}
        }

        return roleINT;
    };

    public static String getTokenBody(HttpServletRequest request){
        String authorHeader =  request.getHeader("Authorization");
        String bearer ="Bearer ";
        String token = authorHeader.substring(bearer.length());
        Claims claims = Jwts.parser().setSigningKey("ray891108")
                .parseClaimsJws(token).getBody();
        System.out.println("JWT payload:"+claims.get("sub", String.class));
        String account = claims.get("sub", String.class);
        return account;
    }
}
