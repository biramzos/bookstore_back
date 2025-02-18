package com.example.bookstore_back.modules.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @JsonIgnore
//    @Lob
//    @Column(columnDefinition = "BYTEA")
//    private byte[] data;
    @Column(columnDefinition = "TEXT")
    private String data;

    public File(
            String name,
            String data
    ){
        this.name = name;
        this.data = data;
    }
}
