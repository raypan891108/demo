package com.example.demo.Service.implement;

import com.example.demo.DTO.AuthorFromRequset;
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

    @Transactional
    public List<book> getAllBooks() {
        List<book> books = bookRepository.findAll();
        return books;
    }

    public ResponseEntity books(HttpServletRequest request){

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            List<book> books = getAllBooks();
            System.out.println(books);
            return ResponseEntity.ok(books);
//            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no !!!");
        }

    }

    public ResponseEntity deleteBook(@PathVariable("id") Integer id, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
//            book book = bookRepository.findById(id);
//            for(book b : book){}

            bookRepository.deleteById(id);
            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no success!!!");
        }

    }


    public ResponseEntity updatebook(@PathVariable Integer id, @RequestBody BookFromRequest updateBook, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
//            book existingBook = bookRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));
//
//            existingBook.setBookName(updateBook.getBookName());
//            existingBook.setAuthor(updateBook.getAuthor());
//            existingBook.setMeans(updateBook.getMeans());
//            existingBook.setPrice(updateBook.getPrice());
//            existingBook.setCost(updateBook.getCost());
//
//            bookRepository.save(existingBook);

                return ResponseEntity.ok("l");
//            return ResponseEntity.ok(existingBook);
        }else{
            return ResponseEntity.ok("no success!!!");
        }

    }


    public ResponseEntity SearchBook(@PathVariable("bookName") String bookName, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){

            book book = bookRepository.findBybookName(bookName);
            System.out.println(book.getAuthorSet());

            if(book != null)
                return ResponseEntity.ok(book);
            else
                return ResponseEntity.ok("NO DATA!!!!");

        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }



    @Transactional
    public ResponseEntity createBook(@RequestBody BookFromRequest book, HttpServletRequest request) {

        book newbook = new book();
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){

            newbook.setBookName(book.getBookName());
            newbook.setMeans(book.getMeans());
            newbook.setPrice(book.getPrice());
            newbook.setCost(book.getCost());

//            Set<AuthorFromRequset> authors = book.getAuthorSet();
            Set<Author> authors = book.getAuthorSet();


            Set<Author> savedAuthors = new HashSet<>();


            if(authors != null){
                for (Author author : authors) {
                    Author savedAuthor = new Author();
                    if(authorRepository.findByAuthor(author.getAuthor()) == null
                            && !author.getAuthor().isEmpty()) {
                        savedAuthor.setAuthor(author.getAuthor());
                        savedAuthors.add(savedAuthor);
                        authorRepository.save(savedAuthor);
                    }
                }
            }
            for (Author author : authors){
                Author au = authorRepository.findByAuthor(author.getAuthor());
                newbook.getAuthorSet().add(au);
                //System.out.println(newbook);
            }
            //newbook.setAuthorSet(savedAuthors);
            //System.out.println("1");
            bookRepository.save(newbook);
           //System.out.println("2");
            return ResponseEntity.ok("新增成功");
        }else{
            return ResponseEntity.ok("no !!!");
        }

    }


//    public ResponseEntity getAllAuthorByBookId(@PathVariable("bookId")  Integer bookid,
//                                                HttpServletRequest request) {
//
//        List<String> authors = authorRepository.findAuthorsBybookId(bookid);
//        return new ResponseEntity<>(authors, HttpStatus.OK);
//
//    }



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
