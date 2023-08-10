package com.example.demo.controller;

import com.example.demo.DTO.ForgotPasswordFromRequest;
import com.example.demo.Service.implement.UserServiceImpl;
import com.example.demo.entities.Member;
import com.example.demo.repositories.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//@SpringBootTest
class userControllerTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private MemberRepository memberRepository;

    private ForgotPasswordFromRequest request;

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

        request = new ForgotPasswordFromRequest();
        request.setEmail("test@gmail.com");
        request.setAccount("test");
        request.setPassword("12345668");
        when(memberRepository.findByEmail(anyString())).thenReturn(member);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("test forgotPassword")
    void forgotPassword() {

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("修改成功");

        // Act
        ResponseEntity<String> actualResponse = userServiceImpl.forgotPassword(request);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse, actualResponse);


    }
}