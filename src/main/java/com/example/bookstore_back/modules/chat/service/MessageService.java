package com.example.bookstore_back.modules.chat.service;

import com.example.bookstore_back.modules.chat.dto.MessageData;
import com.example.bookstore_back.modules.chat.model.Message;
import com.example.bookstore_back.modules.user.model.User;
import com.example.bookstore_back.modules.chat.repository.MessageRepository;
import com.example.bookstore_back.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;

    @Autowired
    public MessageService(
            MessageRepository messageRepository,
            UserService userService
    ){
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Message save(MessageData messageData){
        return messageRepository.save(new Message(
                        userService.getUserByUsername(messageData.getSender()),
                        messageData.getContent(),
                        userService.getUserByUsername(messageData.getReceiver()),
                        messageData.getTime()
                )
        );
    }

    public List<Message> getMessagesBySenderOrReceiverToUser(String username, String receiver){
        User currentUser = userService.getUserByUsername(username);
        User user = userService.getUserByUsername(receiver);
        return messageRepository.findMessagesBySenderOrReceiver(currentUser, currentUser).stream().filter((m) -> (m.getSender().equals(user) || m.getReceiver().equals(user))).collect(Collectors.toList());
    }

    public List<User> getUsersWithChats(User currentUser){
        List<Message> chatMessages = messageRepository.findMessagesBySenderOrReceiver(currentUser, currentUser);
        Set<User> users = new HashSet<>();
        for (Message chatMessage : chatMessages) {
            if (chatMessage.getSender().getUsername().equals(currentUser.getUsername())) {
                users.add(chatMessage.getReceiver());
            } else {
                users.add(chatMessage.getSender());
            }
        }
        return new ArrayList<>(users);
    }

}
