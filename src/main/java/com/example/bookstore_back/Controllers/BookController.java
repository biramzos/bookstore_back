package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.DataAccessObjects.BookRequest;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.User;
import com.example.bookstore_back.Services.BookService;
import com.example.bookstore_back.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;
    private UserService userService;

    @Autowired
    public BookController(
            BookService bookService,
            UserService userService
    ){
        this.bookService = bookService;
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public List<Book> getBooks(){
        return bookService.getAllBooks();
    }

    @ResponseBody
    @GetMapping("/{bookId}")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public Book getBook(@PathVariable("bookId") Book book){
        return book;
    }

    @ResponseBody
    @GetMapping(value = "/{bookId}/preview", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public ResponseEntity<byte[]> getBookPreview(@PathVariable("bookId") Book book){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(book.getPreview().length);
        return new ResponseEntity<>(book.getPreview(), headers, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/users/bought")
    @PreAuthorize("isAuthenticated()")
    public List<Book> getBought(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return user.getBought();
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('ADMIN')")
    public Book createBook(@ModelAttribute BookRequest request) throws IOException {
        return bookService.add(request);
    }

}