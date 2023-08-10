package com.example.demo.controller;

import com.example.demo.DTO.RegisterFromRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.implement.UserServiceImpl;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@AutoConfigureMockMvc
class registerContrallerTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RedisService redisService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("test UserRegister")
    void userRegister() {
        MockitoAnnotations.initMocks(this);
        // Arrange
        RegisterFromRequest requestBody = new RegisterFromRequest();
        requestBody.setAccount("string");
        requestBody.setUserName("string");
        requestBody.setEmail("string@example.com");
        requestBody.setPassword("string");
        requestBody.setPhone("0988776655");
        requestBody.setAgainpassword("string");

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("User registered successfully");

        // Act
        ResponseEntity<String> actualResponse = userServiceImpl.userRegister(requestBody);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

}