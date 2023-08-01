package com.example.demo.controller;

import com.example.demo.Service.bookService;
import com.example.demo.entities.book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "test API", description = "test")
public class testController {

    @Autowired
    bookService bks;

    @Transactional
    @Operation(summary = "書籍資料表", description = "Book table")
    @PostMapping("/test/A")
    public ResponseEntity books(HttpServletRequest request){

        return bks.books(request);

    }
}
