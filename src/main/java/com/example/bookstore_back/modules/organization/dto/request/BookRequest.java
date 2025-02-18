package com.example.bookstore_back.modules.organization.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BookRequest {
    String name;
    String author;
    String description;
    Double cost;
    MultipartFile image;
    MultipartFile file;
}
