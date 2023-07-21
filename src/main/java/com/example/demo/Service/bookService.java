package com.example.demo.Service;


import org.springframework.stereotype.Service;
import java.util.List;

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
import com.example.demo.entities.book;
import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.bookRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class bookService {

    private final bookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final blackListResposity blackListResposity;

    @Autowired
    public bookService(bookRepository bookRepository, MemberRepository memberRepository,
                            RoleRepository roleRepository, blackListResposity blackListResposity){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.blackListResposity = blackListResposity;
    }


    public ResponseEntity books(HttpServletRequest request){

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no !!!");
        }
        
    }

    public ResponseEntity deleteBook(@PathVariable("id") Integer id, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            bookRepository.deleteById(id);
            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no success!!!");
        }
        
    }


    public ResponseEntity updatebook(@PathVariable Integer id,@RequestBody book updateBook, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));

            existingBook.setBookName(updateBook.getBookName());
            existingBook.setAuthor(updateBook.getAuthor());
            existingBook.setMeans(updateBook.getMeans());
            existingBook.setPrice(updateBook.getPrice());
            existingBook.setCost(updateBook.getCost());

            bookRepository.save(existingBook);


            return ResponseEntity.ok(existingBook);
        }else{
            return ResponseEntity.ok("no success!!!");
        }

    }


    public ResponseEntity SearchBook(@PathVariable("bookName") String bookName, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
              book book = bookRepository.findBybookName(bookName);
              if(book != null)
                return ResponseEntity.ok(book);
            else
                return ResponseEntity.ok("NO DATA!!!!");
            
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }




    public ResponseEntity createBook(@RequestBody book book, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            bookRepository.save(book);
            return ResponseEntity.ok("新增成功");
        }else{
            return ResponseEntity.ok("no !!!");
        }
        
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
