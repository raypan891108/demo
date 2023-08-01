package com.example.demo.Service;


import com.example.demo.DTO.BookFromRequest;
import com.example.demo.entities.Author;
import com.example.demo.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;

import com.example.demo.entities.book;
import com.example.demo.entities.role;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.bookRepository;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Service
public interface bookService {

    public ResponseEntity books(HttpServletRequest request);

    public ResponseEntity deleteBook(@PathVariable("id") Integer id,
                                     HttpServletRequest request) ;

    public ResponseEntity updatebook(@PathVariable Integer id,
                                     @RequestBody BookFromRequest updateBook,
                                     HttpServletRequest request) ;

    public ResponseEntity SearchBook(@PathVariable("bookName") String bookName,
                                     HttpServletRequest request) ;


    public ResponseEntity createBook(@RequestBody BookFromRequest book,
                                     HttpServletRequest request) ;



}
