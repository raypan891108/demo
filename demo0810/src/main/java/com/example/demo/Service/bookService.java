package com.example.demo.Service;


import com.example.demo.DTO.AddBookFromRequest;
import com.example.demo.DTO.BookVersionFromRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;



import jakarta.servlet.http.HttpServletRequest;

@Service
public interface bookService {

    public ResponseEntity books(HttpServletRequest request);

    public ResponseEntity deleteBook(@PathVariable("id") Integer id,
                                     HttpServletRequest request) ;

    public ResponseEntity updatebook(@PathVariable Integer id,
                                     @RequestBody AddBookFromRequest updateBook,
                                     HttpServletRequest request) ;

    public ResponseEntity SearchBook(@PathVariable("bookName") String bookName,
                                     HttpServletRequest request) ;


    public ResponseEntity createBook(@RequestBody AddBookFromRequest book,
                                     HttpServletRequest request) ;


    public ResponseEntity AddBookVersion(@PathVariable String bookName,
                                            @RequestBody BookVersionFromRequest bookVersionFromRequest,
                                            HttpServletRequest request) ;

    public ResponseEntity UpdateBookVersion(@PathVariable("bookName") String bookName,
                                            @RequestBody BookVersionFromRequest bookVersionFromRequest,
                                            HttpServletRequest request);



}
