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
import com.example.demo.entities.blackList;
import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class userService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final blackListResposity blackListResposity;

    @Autowired
    public userService(MemberRepository memberRepository, RoleRepository roleRepository,blackListResposity blackListResposity){
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.blackListResposity = blackListResposity;
    }

 
    public ResponseEntity<String> forgotPassword(@RequestBody Member member) {
        Member editmember = memberRepository.findByEmail(member.getEmail());
        if(editmember.getAccount().equals(member.getAccount())){
            editmember.setPassword(member.getPassword());
            memberRepository.save(editmember);
            return ResponseEntity.ok("修改成功");
        }else{
            return ResponseEntity.ok("data error!!!!");
        }
    }

    public ResponseEntity logoutUser(HttpServletRequest request) {
        
        blackList logoutUser = blackListResposity.findByAccount(getTokenBody(request));
         
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            if(logoutUser == null){
                blackList logout = new blackList();
                logout.setAccount(getTokenBody(request));
                blackListResposity.save(logout);
            }
            return ResponseEntity.ok("已登出");
        }else{
            return ResponseEntity.ok("no !!!");
        }
        
    }

    public ResponseEntity<String> userLogin(@RequestBody role role) {

        role storedUser = roleRepository.findByaccount(role.getAccount());
        String result="請輸入帳號密碼!";

        if (storedUser != null && storedUser.getPassword().equals(role.getPassword())) {

            result=verifyUser(storedUser.getEmail(), storedUser.getAccount(),storedUser.getPassword());
            
            //從黑名單踢出
            blackList user = blackListResposity.findByAccount(storedUser.getAccount());
            if(user != null){
                blackListResposity.delete(user);
            }

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("accout or password error!!!");
        }
    }



    public ResponseEntity<String> userRegister(@RequestBody Register newUser) {

         Member member = new Member();
        
        if (newUser.getAccount() != null && newUser.getPassword() != null &&
            newUser.getUserName() != null && newUser.getPhone() != null &&
            newUser.getEmail() != null && newUser.getAgainpassword() != null 
            && newUser.getAgainpassword().equals(newUser.getPassword()))
            {

            member.setAccount(newUser.getAccount());
            member.setPassword(newUser.getPassword());
            member.setUserName(newUser.getUserName());
            member.setPhone(newUser.getPhone());
            member.setEmail(newUser.getEmail());
            memberRepository.save(member);
            return ResponseEntity.ok("新增成功");

        } else {
            System.out.println(newUser.getAccount()) ;
            System.out.println(newUser.getPassword()) ;
            System.out.println(newUser.getAgainpassword()) ;
            System.out.println(newUser.getUserName()) ;
            System.out.println(newUser.getPhone()) ;
            System.out.println(newUser.getEmail()) ;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("資料有誤");
        }
        
    }

     public String verifyUser(String email,String userAcct , String userPasswd){
      
        role user=roleRepository.findByEmail(email);
        String result="";

        if(user.getEmail().equals(email)){
            
            if(user.getAccount().equals(userAcct)){

                if(user.getPassword().equals(userPasswd)){
            
                    //設定3min過期
                    Date expireDate = new Date(System.currentTimeMillis()+ 300 * 60 * 1000);

                    String jwtToken = Jwts.builder()
                    .setSubject(userAcct) //我以account當subject

                    .setExpiration(expireDate)// 設定time

                    //Mray891108是自訂的私鑰，HS512是自選的演算法
                    .signWith(SignatureAlgorithm.HS512,"ray891108")

                    .compact();

                    //直接將JWT傳回
                    result=jwtToken;
                }else{
                    result="0003";
                }
            }else{
                result="0002";       
            }
        }else{
            result="0001";
        }
        return result;        
    }    








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
