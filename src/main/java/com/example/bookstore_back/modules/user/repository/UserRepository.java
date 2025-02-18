package com.example.bookstore_back.modules.user.repository;

import com.example.bookstore_back.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    User findUserByEmail(String email);
    Optional<User> findByToken(String token);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
