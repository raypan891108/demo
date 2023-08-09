package com.example.demo.Service.implement;

import com.example.demo.DTO.ForgotPasswordFromRequest;
import com.example.demo.DTO.LoginFromRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.userService;
import com.example.demo.entities.Member;
import com.example.demo.DTO.RegisterFromRequest;
//import com.example.demo.entities.role;
import com.example.demo.entities.Role;
import com.example.demo.repositories.MemberRepository;
//import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.RoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;

@Service
public class UserServiceImpl implements userService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RedisService redisService;



    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordFromRequest member) {
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


        if(!function.islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else{
            redisService.deleteDataFromRedis(function.getTokenBody(request));
            return ResponseEntity.ok("已登出");
        }

    }
    public ResponseEntity<String> userLogin(@RequestBody LoginFromRequest member) {

        Member storedUser = memberRepository.findByaccount(member.getAccount());
        String result="請輸入帳號密碼!";

        if (storedUser != null && storedUser.getPassword().equals(member.getPassword())) {

            result=verifyUser(storedUser.getEmail(), storedUser.getAccount(),storedUser.getPassword());
            redisService.saveDataToRedis(storedUser.getAccount(), result, 36000);
            redisService.getDataFromRedis(storedUser.getAccount());
            System.out.println(result);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("accout or password error!!!");
        }
    }





    public ResponseEntity<String> userRegister(RegisterFromRequest newUser) {

        Member member = new Member();
        Role role = roleRepository.findByRoleName("User");
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
            member.getRoles().add(role);

            memberRepository.save(member);
            return ResponseEntity.ok("User registered successfully");

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

        Member user= memberRepository.findByEmail(email);
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



}
