package com.example.demo.repositories;

import com.example.demo.entities.BookVersion;
import com.example.demo.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookVersionRepository extends JpaRepository<BookVersion, Integer> {

    BookVersion findByVersion(String version);
}
