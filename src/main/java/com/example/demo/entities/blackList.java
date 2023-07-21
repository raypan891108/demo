package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="blackList")
public class blackList {
    
    @Id
    @Column(name="account")
    private String account;

    public blackList(){}

}
