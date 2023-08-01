package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity
@Data
@Table(name="book")
public class book {

    public book(){
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name="bookName")
    private String bookName;

    
    @Column(name="means")
    private String means;
    
    @Column(name="price")
    private Integer price;
    
    @Column(name="cost")
    private Integer cost;
    @JsonIgnoreProperties(value = "books")
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL, CascadeType.REMOVE}
    ) //CascadeType.PERSIST,
    @JoinTable(
            name = "Book_Author",
            joinColumns = {@JoinColumn(name = "Book_id",
                    referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Author_id",
                    referencedColumnName = "ID")})
    private Set<Author> authorSet = new HashSet<>();







}
