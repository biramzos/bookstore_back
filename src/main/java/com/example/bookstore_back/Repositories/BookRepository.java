package com.example.bookstore_back.Repositories;

import com.example.bookstore_back.Models.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();
}
