package com.example.bookstore_back.modules.chat.repository;

import com.example.bookstore_back.modules.chat.model.Message;
import com.example.bookstore_back.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesBySenderOrReceiver(User sender, User receiver);
}
