package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="BookVersion")
public class BookVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name="version")
    private String version;

    @Column(name="quantity")
    private int quantity;


    // 多對一關係，指向 Book 實體的 book 屬性
    //@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "book")
    @JoinColumn(name = "book_id")
    private book book;


    //忽略特定欄位在序列化（轉為 JSON）時的輸出
    @JsonIgnore
    public Integer getId() {
        return ID;
    }
}


