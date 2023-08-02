package com.example.demo.DTO;

import com.example.demo.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class AddMemberForRequest {

    private String account;

    private String password;

    private String userName;

    private String phone;

    private String email;

    private Set<String> Roles = new HashSet<>();
}
