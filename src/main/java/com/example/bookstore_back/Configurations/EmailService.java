package com.example.bookstore_back.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class EmailService {
    private JavaMailSender sender;

    @Autowired
    public EmailService(
            JavaMailSender sender
    ){
        this.sender = sender;
    }

    public void send(String text, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("QazaqBooks");
        message.setText(text);
        sender.send(message);
    }

}
