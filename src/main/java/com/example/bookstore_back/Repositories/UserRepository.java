package com.example.bookstore_back.Repositories;

import com.example.bookstore_back.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    User findUserByEmail(String email);
    Optional<User> findByToken(String token);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
