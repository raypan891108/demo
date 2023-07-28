package com.example.demo.DTO;

import com.example.demo.entities.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BookFromRequest {

    private String bookName;
    //@JsonIgnoreProperties("books")

    private Set<Author> authorSet = new HashSet<>();


    private String means;


    private Integer price;


    private Integer cost;
}
