package com.example.bookstore_back.DataAccessObjects;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class MessageData {
    private String sender;
    private String content;
    private String receiver;
    private LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Almaty"));
}
