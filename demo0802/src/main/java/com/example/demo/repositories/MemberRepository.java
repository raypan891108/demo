package com.example.demo.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.Member;



@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{

    Member findByaccount(String account);
    Member findByEmail(String email);
    Member getByuserName(String userName);

}


