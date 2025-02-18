package com.example.bookstore_back.modules.user.controller;

import com.example.bookstore_back.config.EmailService;
import com.example.bookstore_back.config.TokenService;
import com.example.bookstore_back.modules.user.dto.request.LoginRequest;
import com.example.bookstore_back.modules.user.dto.response.LoginResponse;
import com.example.bookstore_back.modules.user.dto.request.RegisterRequest;
import com.example.bookstore_back.modules.user.dto.request.UpdatePasswordRequest;
import com.example.bookstore_back.modules.organization.model.Book;
import com.example.bookstore_back.modules.user.model.User;
import com.example.bookstore_back.utils.ByteService;
import com.example.bookstore_back.modules.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private EmailService emailService;

    @Autowired
    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            EmailService emailService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @ResponseBody
    @PostMapping(value = "/login", produces = "application/json")
    @PreAuthorize("isAnonymous()")
    public LoginResponse loginPost(@RequestBody LoginRequest request, HttpServletRequest req, HttpServletResponse res){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        String token = tokenService.generateToken(auth.getName());
        User user = userService.getUserByUsername(auth.getName());
        return LoginResponse.fromUser(user);
    }

    @ResponseBody
    @PostMapping("/token/login")
    @PreAuthorize("isAuthenticated()")
    public LoginResponse tokenLoginPost(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return LoginResponse.fromUser(user);
    }

    @ResponseBody
    @PostMapping("/{userId}/change/{role}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void changeToSeller(@PathVariable("userId") User user, @PathVariable("role") String role){
        userService.changeRole(user, role);
    }

    @ResponseBody
    @PostMapping("/update/password")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(Authentication auth, @RequestBody UpdatePasswordRequest request){
        User user = userService.getUserByUsername(auth.getName());
        Authentication auth2 = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getOldpass()));
        if(auth.equals(auth2)){
            userService.updatePassword(user, request.getNewpass());
            return "Success";
        }
        return "Password is not exist";
    }

    @ResponseBody
    @GetMapping("/get-all-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAll(){
        return userService.getAll();
    }

    @ResponseBody
    @GetMapping("/forget/password")
    @PreAuthorize("isAnonymous()")
    public String forgetPassword(@RequestParam("username") String username){
        User user = userService.getByUsernameOrEmail(username);
        String randomPassword = userService.randomPassword();
        userService.updatePassword(user, randomPassword);
        String text = "You forgot your password.\nYour new credentials\nUsername:" + user.getUsername() + "\nPassword: " + randomPassword;
        emailService.send(text, user.getEmail());
        return "Go to your email to see your new password!";
    }

    @ResponseBody
    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("isAnonymous()")
    public LoginResponse registerPost(@ModelAttribute RegisterRequest request) throws IOException {
        String text = "To activate account click to the link:\n" + "http://localhost:8000/api/v1/auth/activate?t=" + tokenService.generateToken(request.getUsername());
        emailService.send(text, request.getEmail());
        return userService.registration(request);
    }

    @ResponseBody
    @GetMapping("/activate")
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public String activated(@RequestParam("t") String token){
        User user = userService.getByToken(token);
        userService.activate(user);
        return "Success";
    }

    @ResponseBody
    @GetMapping(value = "/user/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public byte[] userImageByUserId(@PathVariable("userId") User user){
        return ByteService.getBytes(user.getImage());
    }

    @ResponseBody
    @GetMapping("/favourites")
    @PreAuthorize("isAuthenticated()")
    public List<Book> getFavourites(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return userService.getFavourites(user);
    }

    @ResponseBody
    @GetMapping("/favourites/add/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void addFavourites(Authentication auth, @PathVariable("bookId") Book book){
        User user = userService.getUserByUsername(auth.getName());
        userService.addFavourites(user, book);
    }

    @ResponseBody
    @GetMapping("/favourites/check/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public Boolean checkFavourites(Authentication auth, @PathVariable("bookId") Book book){
        User user = userService.getUserByUsername(auth.getName());
        return userService.checkBookFavourites(user, book);
    }

    @ResponseBody
    @GetMapping("/favourites/remove/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void removeFavourites(Authentication auth, @PathVariable("bookId") Book book){
        User user = userService.getUserByUsername(auth.getName());
        userService.removeFavourites(user, book);
    }

}
