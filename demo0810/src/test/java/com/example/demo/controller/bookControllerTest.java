package com.example.demo.controller;

import com.example.demo.DTO.AddBookFromRequest;
import com.example.demo.DTO.BookVersionFromRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.implement.BookServiceImpl;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class bookControllerTest {

    @InjectMocks
    private BookServiceImpl bookServiceImpl;


    @Mock
    MemberRepository memberRepository;

    @Mock
    bookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    BookVersionRepository bookVersionRepository;

    @Mock
    private RedisService redisService;
    String validToken;

    MockHttpServletRequest httpRequest;

    private Role role;

    private Member member;

    private AddBookFromRequest newbook;

    private book book;

    private AddBookFromRequest book2;

    private Author author;

    private BookVersion bookVersion;

    private BookVersionFromRequest bookVersion2;


    List<BookVersion> versions;
    List<book> books;



    @BeforeEach
    void setUp() {



        MockitoAnnotations.openMocks(this);

        //token body is test
        validToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjkxNjY4NzE3fQ.74ZPzXN9fVPnqE6Us2fHQMjLvc_k_iIHdpcw4dJ9mHrIt_CozQRirQFyQf04nhPt1FEWdlib_Mo9DcOmejz_dw";
        httpRequest = new MockHttpServletRequest();
        httpRequest.addHeader("Authorization", "Bearer " + validToken);

        Role
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


        versions = new ArrayList<>();
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

        books = new ArrayList<>();
        books.add(book);



        when(memberRepository.findByaccount(anyString())).thenReturn(member);


        when(redisService.getDataFromRedis("test")).thenReturn("test");


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void books() {
        boolean bool = true;
        when(redisService.getDataFromRedis("test")).thenReturn("test");
        when(bookRepository.findAll()).thenReturn(books);

        ResponseEntity<List<book>> expectedResponse = ResponseEntity.ok(books);

        // Act
        ResponseEntity<String> actualResponse = bookServiceImpl.books(httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void deleteBookById() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("delete success");


        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Act
        ResponseEntity<String> actualResponse = bookServiceImpl.deleteBook(1, httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void updateEmployee() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("delete success");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Act
        ResponseEntity<String> actualResponse = bookServiceImpl.deleteBook(1, httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse.toString(), actualResponse.toString());

    }

    @Test
    void searchBook() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok(book.toString());

        when(bookRepository.findBybookName(anyString())).thenReturn(book);

        // Act
        ResponseEntity<String> actualResponse = bookServiceImpl.SearchBook("java", httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse.toString(), actualResponse.toString());

    }

    @Test
    void createBook() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("新增成功");

        when(authorRepository.findByAuthor(anyString())).thenReturn(author);

        // Act
        ResponseEntity<String> actualResponse = bookServiceImpl.createBook(book2, httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse.toString(), actualResponse.toString());

    }

    @Test
    void addBookVersion() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("新增成功");

        when(bookRepository.findBybookName(anyString())).thenReturn(book);

        // Act
        ResponseEntity<String> actualResponse = bookServiceImpl.AddBookVersion("java", bookVersion2, httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse.toString(), actualResponse.toString());

    }

    @Test
    void updateBookVersion() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("修改成功");

        when(bookRepository.findBybookName(anyString())).thenReturn(book);

        when(bookVersionRepository.findByVersion(anyString())).thenReturn(bookVersion);


        // Act
        ResponseEntity<String> actualResponse = bookServiceImpl.UpdateBookVersion("java", bookVersion2, httpRequest);
        System.out.println(actualResponse);

        // Assert
        assertEquals(expectedResponse.toString(), actualResponse.toString());

    }
}