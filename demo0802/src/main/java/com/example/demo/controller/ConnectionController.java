package com.example.demo.controller;




import com.example.demo.connection.NetworkTest;
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
@Tag(name = "Connection API", description = "連線測試")
public class ConnectionController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conneciton Redis success")})
    @Operation(summary = "Redis連線", description = "connection Redis")
    @GetMapping("/redis")
    public void redisConnection(String host, Integer port){
        System.out.println("#### Redis connect start ");
        NetworkTest.testConnection(host, port);
        System.out.println("#### Redis connect end ");

    }


}
