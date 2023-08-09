package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name="author")
    private String author;


//    @JsonIgnoreProperties("authorSet")
    //@ManyToMany(mappedBy = "authorSet", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "authorSet")
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL, CascadeType.REMOVE}
    ) //CascadeType.PERSIST,
    @JoinTable(
            name = "Book_Author",
            joinColumns = {@JoinColumn(name = "Book_id",
                    referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Author_id",
                    referencedColumnName = "ID")})
    private Set<book> books = new HashSet<>();



}
