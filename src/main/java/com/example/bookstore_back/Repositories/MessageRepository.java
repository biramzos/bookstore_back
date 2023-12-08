package com.example.bookstore_back.Repositories;

import com.example.bookstore_back.Models.Message;
import com.example.bookstore_back.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesBySenderOrReceiver(User sender, User receiver);
}
