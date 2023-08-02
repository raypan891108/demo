package com.example.demo.DTO;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AddBookFromRequest {
    private String bookName;
    private Set<String> authorSet = new HashSet<>();

    private String means;
    private Integer price;
    private Integer cost;
}
