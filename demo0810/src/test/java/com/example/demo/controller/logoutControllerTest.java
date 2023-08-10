package com.example.demo.controller;

import com.example.demo.Service.RedisService;
import com.example.demo.Service.implement.UserServiceImpl;
import com.example.demo.entities.Member;
import com.example.demo.entities.Role;
import com.example.demo.repositories.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class logoutControllerTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    String validToken;

    MockHttpServletRequest httpRequest;

    Member member;

    Role role;
    @Mock
    RedisService redisService;

    @BeforeEach
    void setUp() {

        //token body is test
        validToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjkxNjY4NzE3fQ.74ZPzXN9fVPnqE6Us2fHQMjLvc_k_iIHdpcw4dJ9mHrIt_CozQRirQFyQf04nhPt1FEWdlib_Mo9DcOmejz_dw";
        httpRequest = new MockHttpServletRequest();
        httpRequest.addHeader("Authorization", "Bearer " + validToken);

        //Role
        role = new Role();
        role.setRoleName("Admin");

        //member
        member = new Member();
        member.setID(1);
        member.setAccount("test");
        member.setPassword("test");
        member.setUserName("test");
        member.setEmail("test@gmail.com");
        member.setPhone("0999999999");
        member.getRoles().add(role);


        when(redisService.getDataFromRedis("test")).thenReturn("test");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void logoutUser() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("已登出");

        // Act
        ResponseEntity actualResponse = userServiceImpl.logoutUser(httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }
}