package com.example.bookstore_back.Repositories;

import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.Organization;
import com.example.bookstore_back.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
@Transactional
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByBooksContains(Book book);
    Optional<Organization> findByName(String name);
    Optional<Organization> findByUser(User user);
}
