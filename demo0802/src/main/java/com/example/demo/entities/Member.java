package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Schema(description = "會員")
@Table(name="Member")
public class Member {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;


    @Column(name="userName")
    private String userName;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @JsonIgnoreProperties(value = "Users")
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL, CascadeType.REMOVE}
    )
    @JoinTable(
            name = "Member_Role",
            joinColumns = {@JoinColumn(name = "Member_id",
                    referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Role_id",
                    referencedColumnName = "ID")})
    private Set<Role> Roles = new HashSet<>();

}
