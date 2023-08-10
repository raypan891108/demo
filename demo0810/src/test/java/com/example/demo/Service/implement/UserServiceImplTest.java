package com.example.demo.Service.implement;

import com.example.demo.DTO.ForgotPasswordFromRequest;
import com.example.demo.DTO.LoginFromRequest;
import com.example.demo.DTO.RegisterFromRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.userService;
import com.example.demo.controller.registerContorller;
import com.example.demo.entities.Member;
import com.example.demo.entities.Role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    RegisterFromRequest requestBody;

    Role role;

    Member member;

    Member member2;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    RoleRepository roleRepository;

    @Mock
    RedisService redisService;

    @Mock
    HttpServletRequest req;

    private String validToken;

    @BeforeEach
    void setUp() {
        requestBody = new RegisterFromRequest();
        requestBody.setAccount("string");
        requestBody.setUserName("string");
        requestBody.setEmail("string@example.com");
        requestBody.setPassword("string");
        requestBody.setPhone("0988776655");
        requestBody.setAgainpassword("string");

        role = new Role();
        role.setRoleName("User");
        roleRepository.save(role);


        member = new Member();
        member.setAccount("test");
        member.setPassword("test");
        member.setUserName("test");
        member.setPhone("09999999999");
        member.setEmail("test@gmail.com");

        member2 = new Member();
        member2.setID(1);
        member2.setAccount("test");
        member2.setPassword("test");
        member2.setUserName("test");
        member2.setPhone("09999999999");
        member2.setEmail("test@gmail.com");

        //token body is test
        validToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjkxNjY4NzE3fQ.74ZPzXN9fVPnqE6Us2fHQMjLvc_k_iIHdpcw4dJ9mHrIt_CozQRirQFyQf04nhPt1FEWdlib_Mo9DcOmejz_dw";

        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("test UserRegister")
    void userRegister() throws Exception{


        // 創建模擬的 UserServiceImpl 物件
        UserServiceImpl userServiceMock = Mockito.mock(UserServiceImpl.class);


        // 假設預期的回應是 ResponseEntity.ok("User registered successfully");
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("User registered successfully");


        // 使用 doReturn 來模擬方法的行為
        doReturn(expectedResponse).when(userServiceMock).userRegister(any(RegisterFromRequest.class));


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .servletPath("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }


    @Test
    @DisplayName("test forgotPassword")
    void forgotPassword() throws Exception{
        ForgotPasswordFromRequest request = new ForgotPasswordFromRequest();
        request.setEmail("test@gmail.com");
        request.setAccount("test");
        request.setPassword("test");

        when(memberRepository.findByEmail(anyString())).thenReturn(member);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/forgotPassword")
                        .servletPath("/forgotPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("修改成功"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);

    }

    @Test
    @DisplayName("test logoutUser")
    void logoutUser() throws Exception{

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/userlogout")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("已登出"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test userLogin")
    void userLogin() throws Exception{
        LoginFromRequest loginFromRequest = new LoginFromRequest();
        loginFromRequest.setAccount("test");
        loginFromRequest.setPassword("test");

        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(memberRepository.findByEmail(anyString())).thenReturn(member);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .servletPath("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginFromRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                //.andExpect(MockMvcResultMatchers.content().string(""))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);

    }


}