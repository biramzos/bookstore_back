package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.File;
import com.example.bookstore_back.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/files")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(
            FileService fileService
    ){
        this.fileService = fileService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> downloadFile(@PathVariable("bookId") Book book) {
        File file = book.getFile();
        return ResponseEntity.ok()
                .body(file.getData());
    }

}
