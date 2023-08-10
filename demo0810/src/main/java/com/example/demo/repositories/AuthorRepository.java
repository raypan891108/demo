package com.example.demo.repositories;

import com.example.demo.entities.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByAuthor(String author);

    @Query("SELECT a FROM Author a INNER JOIN a.books b WHERE b.ID = :bookId")
    Set<Author> findAuthorsBybookId(@Param("bookId")Integer bookId);
}
