package com.example.demo.Service.implement;

import com.example.demo.DTO.AddMemberForRequest;
import com.example.demo.Service.MemberService;
import com.example.demo.Service.RedisService;
import com.example.demo.entities.Member;
import com.example.demo.entities.Role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class MemberServiceImplTest {

    @Inject
    MemberServiceImpl memberServiceImpl;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    RedisService redisService;
    private MockHttpServletRequest req; // Mock HttpServletRequest

    String validToken;

    Member member;

    Role role;

    AddMemberForRequest request;

    AddMemberForRequest updateMember;

    @BeforeEach
    void setUp() {
        //註冊的會員資料
        request = new AddMemberForRequest();
        request.setAccount("mam");
        request.setPassword("mam");
        request.setUserName("mam");
        request.setPhone("0999999999");
        request.setEmail("mam@gmail.com");
        request.getRoles().add("Admin");

        //修改的會員資料
        updateMember = new AddMemberForRequest();
        updateMember.setAccount("hello");
        updateMember.setPassword("mam");
        updateMember.setUserName("mam");
        updateMember.setPhone("0999999999");
        updateMember.setEmail("mam@gmail.com");
        updateMember.getRoles().add("Admin");
        //token body is test
        validToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjkxNjY4NzE3fQ.74ZPzXN9fVPnqE6Us2fHQMjLvc_k_iIHdpcw4dJ9mHrIt_CozQRirQFyQf04nhPt1FEWdlib_Mo9DcOmejz_dw";
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
        memberRepository.save(member);

        List<Member> members = new ArrayList<>();
        members.add(member);


        // 調用實際的memberRepository.findAll方法
        when(memberRepository.findAll()).thenReturn(members);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("test getMemberList")
    void getMemberList() throws Exception {

        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/Member")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                //.andExpect(MockMvcResultMatchers.content().string("新增成功"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test deleteMember")
    void deleteMember() throws Exception{
        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/Member/1")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("delete success"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test updateMember")
    void updateMember() throws Exception{

        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/Member/1")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMember)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("edit success"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test searchMember")
    void searchMember() throws Exception{

        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/Member/test")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                        .andExpect(MockMvcResultMatchers.content().string("{\"account\":\"test\"," +
                                "\"password\":\"test\",\"userName\":\"test\",\"phone\":\"0999999999\"," +
                                "\"email\":\"test@gmail.com\",\"id\":1,\"roles\":[{\"roleName\":\"Admin\"," +
                                "\"id\":null,\"users\":[]}]}"))
                        .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test createMember")
    void createMember() throws Exception{


        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/Member")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("新增成功"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }
}