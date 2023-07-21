package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.entities.blackList;
import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
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

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Service.MemberService;



@RestController
@Tag(name = "Member API", description = "會員管理")
public class MemberController {

    @Autowired
    public MemberController(){ }


    @Autowired
    MemberService mbs;

   
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Found the Members", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = Member.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
        content = @Content), 
    @ApiResponse(responseCode = "401", description = "No authentication", 
        content = @Content) })
    @Operation(summary = "會員資料表", description = "Find the Member table")
    @GetMapping("/Member")
    public ResponseEntity members(HttpServletRequest request){
       
        return mbs.getMemberList(request);
    }
    

    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Delete the Member", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = Member.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
        content = @Content), 
    @ApiResponse(responseCode = "401", description = "No authentication", 
        content = @Content) })
    @Operation(summary = "刪除會員", description = "Delete the Member")
    @DeleteMapping("/Member/{id}")
    public ResponseEntity deleteMemberById(@PathVariable("id") Integer id, HttpServletRequest request) {
      
        return mbs.deleteMember(id, request);
    
    }

    
    @Transactional //保证延迟加载的属性能够被正确初始化
    @PutMapping("/Member/{id}")
    @Operation(summary = "修改會員資料", description = "Edit the Member")
    public ResponseEntity updateMember(@PathVariable("id") Integer id, @RequestBody Member updateMember, HttpServletRequest request) {
       
        return mbs.updateMember(id, updateMember, request);
     
    }

     
    @Transactional //保证延迟加载的属性能够被正确初始化
    @GetMapping("/Member/{account}")
    @Operation(summary = "查詢會員資料", description = "Search the Member")
    public ResponseEntity SearchMember(@PathVariable("account") String account, HttpServletRequest request) {
       
        return mbs.SearchMember(account, request);
   
    }



    @PostMapping("/Member")
    @Operation(summary = "新增會員資料", description = "Add the Member")
    public ResponseEntity createMember(@RequestBody Member member, HttpServletRequest request) {
       
        return mbs.createMember(member, request);
    
        
    }



   
}
