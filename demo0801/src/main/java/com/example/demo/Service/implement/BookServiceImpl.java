package com.example.demo.Service.implement;

//import com.example.demo.DTO.AuthorFromRequset;
import com.example.demo.DTO.BookFromRequest;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.bookService;
import com.example.demo.entities.Author;
import com.example.demo.entities.Member;
import com.example.demo.entities.book;
import com.example.demo.entities.role;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.bookRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Book;
import java.util.*;

@Service
public class BookServiceImpl implements bookService {
    @Autowired
    private bookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private SessionFactory sessionFactory;


    public List<book> getAllBooks() {
        List<book> books;
        books = bookRepository.findAll();
        return books;
    }

    public ResponseEntity books(HttpServletRequest request){

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            List<book> books = getAllBooks();
            return ResponseEntity.ok(books);

        }else{
            return ResponseEntity.ok("no !!!");
        }

    }
    @Transactional
    public ResponseEntity deleteBook(@PathVariable("id") Integer id, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){

            book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

                book.getAuthorSet().clear();
                bookRepository.deleteById(id);


            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.ok("no success!!!");
        }

    }


    public ResponseEntity updatebook(@PathVariable Integer id, @RequestBody BookFromRequest updateBook, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            book existingBook = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));
            System.out.println(existingBook);
            existingBook.setBookName(updateBook.getBookName());
            existingBook.setMeans(updateBook.getMeans());
            existingBook.setPrice(updateBook.getPrice());
            existingBook.setCost(updateBook.getCost());
            existingBook.setAuthorSet(new HashSet<>());
            isPresentWithAuthor(updateBook,existingBook);
//            Set<String> authors = updateBook.getAuthorSet();
//
//            for (String author : authors) {
//                Author au = authorRepository.findByAuthor(author);
//                if (au == null) {
//                    // 如果作者還未保存到數據庫中，先保存它
//                    au = new Author();
//                    au.setAuthor(author);
//                    authorRepository.save(au);
//                }
//                existingBook.getAuthorSet().add(au);
//            }

            bookRepository.save(existingBook);

            return ResponseEntity.ok("edited success");

        }else{
            return ResponseEntity.ok("no success!!!");
        }

    }


    public ResponseEntity SearchBook(@PathVariable("bookName") String bookName, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){

            book book = bookRepository.findBybookName(bookName);


            if(book != null)
                return ResponseEntity.ok(book);
            else
                return ResponseEntity.ok("NO DATA!!!!");

        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }




    public ResponseEntity createBook(@RequestBody BookFromRequest book, HttpServletRequest request) {


        book newbook = new book();
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            newbook.setBookName(book.getBookName());
            newbook.setMeans(book.getMeans());
            newbook.setPrice(book.getPrice());
            newbook.setCost(book.getCost());
            newbook.setAuthorSet(new HashSet<>());
//            Set<Author> authors = book.getAuthorSet();
            isPresentWithAuthor(book,newbook);
//            Set<String> authors = book.getAuthorSet();
//
//            for (String author : authors) {
//                Author au = authorRepository.findByAuthor(author);
//                if (au == null) {
//                    // 如果作者還未保存到數據庫中，先保存它
//                    au = new Author();
//                    au.setAuthor(author);
//
//
//                    authorRepository.save(au);
//                }
//                newbook.getAuthorSet().add(au);
//            }

            bookRepository.save(newbook);

            return ResponseEntity.ok("新增成功");
        }else{
            return ResponseEntity.ok("no !!!");
        }

    }




    public void isPresentWithAuthor(BookFromRequest book,book newbook){
        Set<String> authors = book.getAuthorSet();

        for (String author : authors) {
            Author au = authorRepository.findByAuthor(author);
            if (au == null) {
                // 如果作者還未保存到數據庫中，先保存它
                au = new Author();
                au.setAuthor(author);


                authorRepository.save(au);
            }
            newbook.getAuthorSet().add(au);
        }
    }



    public String getTokenBody(HttpServletRequest request){
        String authorHeader =  request.getHeader("Authorization");
        String bearer ="Bearer ";
        String token = authorHeader.substring(bearer.length());
        Claims claims = Jwts.parser().setSigningKey("ray891108")
                .parseClaimsJws(token).getBody();
        System.out.println("JWT payload:"+claims.get("sub", String.class));
        String account = claims.get("sub", String.class);
        return account;
    }

    boolean islogin(HttpServletRequest request){

        String user = redisService.getDataFromRedis(getTokenBody(request));

        if(user == null) return false ; else return true;
    }

    Integer getRole(HttpServletRequest request){
        Member member = memberRepository.findByaccount(getTokenBody(request));
        role role = roleRepository.findByaccount(member.getAccount());
        return role.getRole();
    };

}
