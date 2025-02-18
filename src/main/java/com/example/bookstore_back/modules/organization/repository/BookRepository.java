package com.example.bookstore_back.modules.organization.repository;

import com.example.bookstore_back.modules.organization.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();
}
