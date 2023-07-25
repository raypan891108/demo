package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="book")
public class book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name="bookName")
    private String bookName;

    @Column(name="author")
    private String author;
    
    @Column(name="means")
    private String means;
    
    @Column(name="price")
    private Integer price;
    
    @Column(name="cost")
    private Integer cost;

    public book(){};

}
