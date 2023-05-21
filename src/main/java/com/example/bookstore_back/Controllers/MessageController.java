package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.DataAccessObjects.MessageData;
import com.example.bookstore_back.Models.Message;
import com.example.bookstore_back.Models.User;
import com.example.bookstore_back.Services.MessageService;
import com.example.bookstore_back.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private MessageService messageService;
    private UserService userService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageController(
        MessageService messageService,
        UserService userService,
        SimpMessagingTemplate simpMessagingTemplate
    ){
        this.messageService = messageService;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @ResponseBody
    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<Message> getAllMessagesOfCurrentUserToUser(@PathVariable("userId") User user, Authentication auth) {
        List<Message> messageList = messageService.getMessagesBySenderOrReceiverToUser(auth.getName(), user.getUsername());
        return messageList;
    }

    @ResponseBody
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public List<User> getChats(Authentication auth){
        User currentUser = userService.getUserByUsername(auth.getName());
        List<User> users = messageService.getUsersWithChats(currentUser);
        return users;
    }

    @MessageMapping("/chat")
    public void chat(MessageData message) {
        Message m = messageService.save(message);
        System.out.println(m);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/messages", message);
    }



}
