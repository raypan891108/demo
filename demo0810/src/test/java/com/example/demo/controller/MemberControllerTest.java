package com.example.demo.controller;

import com.example.demo.DTO.AddMemberForRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.implement.MemberServiceImpl;
import com.example.demo.Service.implement.UserServiceImpl;
import com.example.demo.entities.Member;
import com.example.demo.entities.Role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @Mock
    MemberRepository memberRepository;

    @Mock
    RoleRepository roleRepository;

    String validToken;

    MockHttpServletRequest httpRequest;
    @Mock
    RedisService redisService;

    Member member;

    Role role;

    AddMemberForRequest updateMember;

    AddMemberForRequest createMember;

    @BeforeEach
    void setUp() {

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

        //註冊的會員資料
        createMember = new AddMemberForRequest();
        createMember.setAccount("test");
        createMember.setPassword("hello");
        createMember.setUserName("test");
        createMember.setPhone("test");
        createMember.setEmail("test@gmail.com");
        createMember.getRoles().add("Admin");

        //修改的會員資料
        updateMember = new AddMemberForRequest();
        updateMember.setAccount("test");
        updateMember.setPassword("hello");
        updateMember.setUserName("test");
        updateMember.setPhone("test");
        updateMember.setEmail("test@gmail.com");
        updateMember.getRoles().add("Admin");


        //token body is test
        validToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjkxNjY4NzE3fQ.74ZPzXN9fVPnqE6Us2fHQMjLvc_k_iIHdpcw4dJ9mHrIt_CozQRirQFyQf04nhPt1FEWdlib_Mo9DcOmejz_dw";
        httpRequest = new MockHttpServletRequest();
        httpRequest.addHeader("Authorization", "Bearer " + validToken);

        when(redisService.getDataFromRedis("test")).thenReturn("test");
        when(memberRepository.findByaccount(member.getAccount())).thenReturn(member);

    }


    @Test
    void members() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("[]");

        // Act
        ResponseEntity<String> actualResponse = memberServiceImpl.getMemberList(httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void deleteMemberById() {

        when(memberRepository.findById(1)).thenReturn(Optional.of(member));

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("[]");

        // Act
        ResponseEntity<String> actualResponse = memberServiceImpl.deleteMember(1,httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void updateMember() {

        when( memberRepository.findById(1)).thenReturn(Optional.of(member));

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("edit success");

        // Act
        ResponseEntity<String> actualResponse = memberServiceImpl.updateMember(1, updateMember, httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void searchMember() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok(member.toString());

        // Act
        ResponseEntity<String> actualResponse = memberServiceImpl.SearchMember("test", httpRequest);

        System.out.println(expectedResponse.toString());
        // Assert
        assertEquals(expectedResponse.toString(), actualResponse.toString());
    }

    @Test
    void createMember(){
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("新增成功");

        // Act
        ResponseEntity<String> actualResponse = memberServiceImpl.createMember(createMember, httpRequest);

        System.out.println(expectedResponse.toString());
        // Assert
        assertEquals(expectedResponse.toString(), actualResponse.toString());
    }


}