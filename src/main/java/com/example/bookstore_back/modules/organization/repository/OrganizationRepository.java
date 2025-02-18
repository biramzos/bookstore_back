package com.example.bookstore_back.modules.organization.repository;

import com.example.bookstore_back.modules.organization.model.Book;
import com.example.bookstore_back.modules.organization.model.Organization;
import com.example.bookstore_back.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByBooksContains(Book book);
    Optional<Organization> findByName(String name);
    Optional<Organization> findByUser(User user);
}
