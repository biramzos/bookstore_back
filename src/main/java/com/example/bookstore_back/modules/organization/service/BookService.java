package com.example.bookstore_back.modules.organization.service;

import com.example.bookstore_back.utils.ByteService;
import com.example.bookstore_back.modules.common.service.FileService;
import com.example.bookstore_back.modules.organization.dto.request.BookRequest;
import com.example.bookstore_back.modules.organization.model.Book;
import com.example.bookstore_back.modules.common.model.File;
import com.example.bookstore_back.modules.organization.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;
    private FileService fileService;

    @Autowired
    public BookService (
            BookRepository bookRepository,
            FileService fileService
    ) {
        this.bookRepository = bookRepository;
        this.fileService = fileService;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book add(BookRequest request) throws IOException {
        File file = fileService.add(request.getFile());
        return bookRepository.save(new Book(request.getName(), request.getAuthor(), request.getDescription(), ByteService.getString(request.getImage().getBytes()), request.getCost(), file));
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }
}
