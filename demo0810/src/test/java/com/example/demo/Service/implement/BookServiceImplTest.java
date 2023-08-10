package com.example.demo.Service.implement;

import com.example.demo.DTO.AddBookFromRequest;
import com.example.demo.DTO.BookVersionFromRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.entities.Member;
import com.example.demo.entities.Role;
import com.example.demo.entities.book;
import com.example.demo.entities.Author;
import com.example.demo.entities.BookVersion;

import com.example.demo.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class BookServiceImplTest {

    @Inject
    BookServiceImpl bookServiceImpl;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    bookRepository bookRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    RedisService redisService;

    @MockBean
    BookVersionRepository bookVersionRepository;

    private String validToken;
    private Role role;

    private Member member;

    private AddBookFromRequest newbook;

    private book book;

    private AddBookFromRequest book2;

    private Author author;

    private BookVersion bookVersion;

    private BookVersionFromRequest bookVersion2;

    @BeforeEach
    void setUp() {
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

        //Author
        author = new Author();
        author.setAuthor("ray");
        author.setID(1);

        //bookversion
        bookVersion = new BookVersion();
        bookVersion.setID(1);
        bookVersion.setVersion("English");
        bookVersion.setQuantity(56);

        //bookversion2
        bookVersion2 = new BookVersionFromRequest();
        bookVersion2.setVersion("Chinese");
        bookVersion2.setQuantity(66);


        List<BookVersion> versions = new ArrayList<>();
        versions.add(bookVersion);


        //book1
        book = new book();
        book.setID(1);
        book.setBookName("java");
        book.setMeans("Hello world");
        book.setPrice(10);
        book.setCost(8);
        book.getAuthorSet().add(author);
        book.setVersions(versions);

        //book2
        book2 = new AddBookFromRequest();
        book2.setBookName("C++");
        book2.setMeans("Hello");
        book2.setPrice(101);
        book2.setCost(81);
        book2.getAuthorSet().add("pan");

        //newbook
        newbook = new AddBookFromRequest();
        newbook.setBookName("C++");
        newbook.setMeans("Hello");
        newbook.setPrice(101);
        newbook.setCost(81);
        newbook.getAuthorSet().add("pan");

        List<book> books = new ArrayList<>();
        books.add(book);


        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);
        when(bookRepository.findAll()).thenReturn(books);
        //when(bookVersionRepository.findAll()).thenReturn(versions);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("test getBooks")
    void books() throws Exception{

        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/book")
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
    @DisplayName("test deleteBook")
    void deleteBook() throws Exception{

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/book/1")
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
    @DisplayName("test updatebook")
    void updatebook() throws Exception{

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(authorRepository.findByAuthor(anyString())).thenReturn(author);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/book/1")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book2)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("edited success"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test searchBook")
    void searchBook() throws Exception{

        when(bookRepository.findBybookName(anyString())).thenReturn(book);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/book/java")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("{\"bookName\":\"java\"," +
                        "\"means\":\"Hello world\",\"price\":10,\"cost\":8,\"authorSet\":[{\"author\":\"ray\"," +
                        "\"id\":1}],\"versions\":[{\"version\":\"English\",\"quantity\":56}],\"id\":1}"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test createBook")
    void createBook() throws Exception{
        when(redisService.getDataFromRedis(anyString())).thenReturn("test");
        when(memberRepository.findByaccount(anyString())).thenReturn(member);
        when(roleRepository.findByRoleName(anyString())).thenReturn(role);
        when(authorRepository.findByAuthor(anyString())).thenReturn(author);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newbook)))

                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("新增成功"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test addBookVersion")
    void addBookVersion() throws Exception{

        when(bookRepository.findBybookName(anyString())).thenReturn(book);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/AddBookVersion/java")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookVersion2)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("新增成功"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("test updateBookVersion")
    void updateBookVersion() throws Exception{

        when(bookRepository.findBybookName(anyString())).thenReturn(book);
        when(bookVersionRepository.findByVersion(anyString())).thenReturn(bookVersion);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/UpdateBookVersion/java")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookVersion2)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 預期狀態碼是 OK
                .andExpect(MockMvcResultMatchers.content().string("修改成功"))
                .andReturn();
        int expectedStatusCode = HttpStatus.OK.value();
        int actualStatusCode = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }
}