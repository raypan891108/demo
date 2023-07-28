package com.example.demo.repositories;
import com.example.demo.entities.book;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface bookRepository extends JpaRepository<book, Integer>{


    book findBybookName(String bookName);

//    List<book> findbooksByAuthorId(Integer author_id);

    
    
}
