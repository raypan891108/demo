package com.example.demo.controller;

import com.example.demo.DTO.LoginFromRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.implement.UserServiceImpl;
import com.example.demo.entities.Member;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//@SpringBootTest
class loginContorllerTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RedisService redisService;

    private Member member;

    @BeforeEach

    void setUp() {
        MockitoAnnotations.initMocks(this);
        member = new Member();
        member.setAccount("test");
        member.setPassword("test");
        member.setUserName("test");
        member.setPhone("09999999999");
        member.setEmail("test@gmail.com");


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("test userLogin")
    void userLogin() {
        LoginFromRequest request = new LoginFromRequest();
        request.setAccount("test");
        request.setPassword("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(memberRepository.findByEmail(anyString())).thenReturn(member);

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("200");


        ResponseEntity<String> actualResponse = userServiceImpl.userLogin(request);
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }
}