package com.example.demo.repositories;

import org.springframework.stereotype.Repository;


import com.example.demo.entities.role;
import org.springframework.data.jpa.repository.JpaRepository;





@Repository
public interface RoleRepository extends JpaRepository<role, Integer>{

    public role findByaccount(String account);

    public role findByEmail(String email);

    
}
