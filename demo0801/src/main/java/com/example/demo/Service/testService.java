package com.example.demo.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class testService {
    @PostMapping
    public ResponseEntity test(HttpServletRequest request){


        return ResponseEntity.ok("s");
    }
}
