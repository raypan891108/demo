package com.example.demo.controller;

import com.example.demo.DTO.RegisterFromRequest;
import com.example.demo.Service.implement.UserServiceImpl;
import com.example.demo.Service.userService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class registerContorllerTest {

    @Autowired
    registerContorller contorller;

    @Autowired
    userService urs;


    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    RegisterFromRequest requestBody;
    @BeforeEach
    void setUp() {

        requestBody = new RegisterFromRequest();
        requestBody.setAccount("test");
        requestBody.setPassword("test");
        requestBody.setUserName("test");
        requestBody.setAgainpassword("test");
        requestBody.setPhone("0999999999");
        requestBody.setEmail("test@gmail.com");

    }



    @Test
    void userRegister() throws Exception{



    }
}