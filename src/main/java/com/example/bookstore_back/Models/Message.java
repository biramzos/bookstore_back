package com.example.bookstore_back.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;
    @Column(columnDefinition = "TEXT")
    private String content;
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @Column
    private LocalDateTime time;

    public Message (
            User sender,
            String content,
            User receiver,
            LocalDateTime time
    ){
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
        this.time = time;
    }
}
