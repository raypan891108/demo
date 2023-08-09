package com.example.demo.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name="role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name="roleName")
    private String roleName;

    @JsonIgnoreProperties(value = "Roles")
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE}
    )
    @JoinTable(
            name = "Member_Role",
            joinColumns = {@JoinColumn(name = "Member_id",
                    referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Role_id",
                    referencedColumnName = "ID")})
    private Set<Member> Users = new HashSet<>();





}
