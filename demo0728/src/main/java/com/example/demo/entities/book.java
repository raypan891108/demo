package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonIgnoreProperties("books")
    @ManyToMany
    @JoinTable(name = "Book_Author",
            joinColumns = {@JoinColumn(name = "Book_id",
                    referencedColumnName = "Id")},
            inverseJoinColumns = {@JoinColumn(name = "Author_id",
                    referencedColumnName = "Id")})
    private Set<Author> authorSet;





}
