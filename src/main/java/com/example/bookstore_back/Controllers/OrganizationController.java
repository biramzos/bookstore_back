package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.DataAccessObjects.BookRequest;
import com.example.bookstore_back.DataAccessObjects.CreateOrganizationRequest;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.Organization;
import com.example.bookstore_back.Models.User;
import com.example.bookstore_back.Services.BookService;
import com.example.bookstore_back.Services.OrganizationService;
import com.example.bookstore_back.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    private OrganizationService organizationService;
    private UserService userService;
    private BookService bookService;

    @Autowired
    public OrganizationController (
            OrganizationService organizationService,
            UserService userService,
            BookService bookService
    ) {
        this.organizationService = organizationService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @ResponseBody
    @GetMapping("/{organizationId}")
    @PreAuthorize("isAnonymous()")
    public Organization getById(@PathVariable("organizationId")Organization organization){
        return organization;
    }

    @ResponseBody
    @GetMapping("/{organizationId}/books")
    @PreAuthorize("isAnonymous()")
    public List<Book> getBooksById(@PathVariable("organizationId") Organization organization){
        return organization.getBooks();
    }

    @ResponseBody
    @GetMapping("/books/{bookId}")
    @PreAuthorize("isAnonymous()")
    public Organization getByBooks(@PathVariable("bookId") Book book){
        return organizationService.getOrganizationByBook(book);
    }

    @ResponseBody
    @GetMapping("/users/{userId}")
    @PreAuthorize("isAnonymous()")
    public Organization getByUser(@PathVariable("userId") User user){
        return organizationService.getOrganizationByUser(user);
    }

    @ResponseBody
    @PostMapping("/{organizationId}/add/{bookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public void addAlrBook(@PathVariable("organizationId") Organization organization, @PathVariable("bookId") Book book){
        organizationService.addAlBook(organization, book);
    }

    @ResponseBody
    @PostMapping("/{organizationId}/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public void addBook(@PathVariable("organizationId") Organization organization, @ModelAttribute BookRequest request) throws IOException {
        organizationService.addBook(organization, request);
    }

    @ResponseBody
    @GetMapping("/{organizationId}/remove/{bookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public void removeBook(@PathVariable("organizationId") Organization organization, @PathVariable("bookId") Book book){
        organizationService.removeBook(organization, book);
    }

    @ResponseBody
    @PostMapping("/create/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Organization create(@PathVariable("userId")User user, @RequestParam("name") String name){
        CreateOrganizationRequest request = new CreateOrganizationRequest(name, user.getUsername());
        return organizationService.create(request);
    }

}
