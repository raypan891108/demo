package com.example.demo.controller;




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

import com.example.demo.Service.bookService;

import com.example.demo.entities.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "Book API", description = "書籍管理")
public class bookController {

    @Autowired
    public bookController(){}
    
  
    @Autowired
    bookService bks;


    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Found the book", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = book.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
        content = @Content), 
    @ApiResponse(responseCode = "404", description = "Book not found", 
        content = @Content) })

    @Operation(summary = "書籍資料表", description = "Book table")
    @GetMapping("/book")
    public ResponseEntity books(HttpServletRequest request){
    
        return bks.books(request);
  
    }

    @DeleteMapping("/book/{id}")
    @Operation(summary = "刪除書籍", description = " Delete the Book")
    public ResponseEntity deleteBookById(@PathVariable("id") Integer id, HttpServletRequest request) {
        
        return bks.deleteBook(id, request);
        
    }

    @Transactional //保证延迟加载的属性能够被正确初始化
    @PutMapping("/book/{id}")
    @Operation(summary = "修改書籍資料", description = " Edit the Book data")
    public ResponseEntity updateEmployee(@PathVariable Integer id,@RequestBody book updateBook, HttpServletRequest request) {
       
        return bks.updatebook(id, updateBook, request);

    }

    @GetMapping("/book/{bookName}")
    @Operation(summary = "查詢書籍資料", description = " Search the Book data")
    public ResponseEntity SearchBook(@PathVariable("bookName") String bookName, HttpServletRequest request) {
        
        return bks.SearchBook(bookName, request);
       
    }



    @PostMapping("/book")
    @Operation(summary = "新增書籍", description = " Add the Book")
    public ResponseEntity createBook(@RequestBody book book, HttpServletRequest request) {

        return bks.createBook(book, request);
      
    }
    

}
