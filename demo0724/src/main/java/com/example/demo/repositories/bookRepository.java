package com.example.demo.repositories;
import com.example.demo.entities.book;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface bookRepository extends JpaRepository<book, Integer>{


    book findBybookName(String bookName);

   

    
    
}
