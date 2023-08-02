package com.example.demo.repositories;

import org.springframework.stereotype.Repository;


import com.example.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;





@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{


    Role findByRoleName(String roleName);

}
