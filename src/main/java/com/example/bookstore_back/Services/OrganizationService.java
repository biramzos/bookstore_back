package com.example.bookstore_back.Services;

import com.example.bookstore_back.DataAccessObjects.BookRequest;
import com.example.bookstore_back.DataAccessObjects.CreateOrganizationRequest;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.Organization;
import com.example.bookstore_back.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bookstore_back.Models.User;
import java.io.IOException;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private UserService userService;
    private BookService bookService;

    @Autowired
    public OrganizationService (
            OrganizationRepository organizationRepository,
            UserService userService,
            BookService bookService
    ) {
        this.organizationRepository = organizationRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public Organization create(CreateOrganizationRequest request) {
        Organization organization = organizationRepository.findByName(request.getName()).orElse(null);
        User user = userService.getUserByUsername(request.getUsername());
        if (organization != null) {
            throw new RuntimeException("Seller is exist!");
        }
        if (user.getRole().equals("SELLER")) {
            organization = organizationRepository.save(new Organization(request.getName(), user));
            return organization;
        }
        throw new RuntimeException("User is not seller!");
    }

    public Organization getOrganizationByBook(Book book){
        return organizationRepository.findByBooksContains(book).orElseThrow(() -> new RuntimeException("Seller is not found!"));
    }

    public Organization getOrganizationByUser(User user){
        return organizationRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Organization is not exist!"));
    }

    public void addAlBook(Organization organization, Book book) {
        organization.getBooks().add(book);
        organizationRepository.save(organization);
    }

    public void addBook(Organization organization, BookRequest request) throws IOException {
        Book book = bookService.add(request);
        organization.getBooks().add(book);
        organizationRepository.save(organization);
    }

    public void removeBook(Organization organization, Book book) {
        organization.getBooks().remove(book);
        organizationRepository.save(organization);
        bookService.delete(book);
    }

    public void delete(Organization organization){
        organizationRepository.delete(organization);
    }

}
