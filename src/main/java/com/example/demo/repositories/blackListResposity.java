package com.example.demo.repositories;
import com.example.demo.entities.blackList;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface blackListResposity extends JpaRepository<blackList, Integer>{

    public blackList findByAccount(String account);

    
}
