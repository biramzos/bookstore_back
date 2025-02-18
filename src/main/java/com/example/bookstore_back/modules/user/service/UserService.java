package com.example.bookstore_back.modules.user.service;


import com.example.bookstore_back.utils.ByteService;
import com.example.bookstore_back.config.TokenService;
import com.example.bookstore_back.modules.user.dto.response.LoginResponse;
import com.example.bookstore_back.modules.user.dto.request.RegisterRequest;
import com.example.bookstore_back.modules.payment.enums.BasketStatus;
import com.example.bookstore_back.modules.user.enums.UserRole;
import com.example.bookstore_back.modules.payment.model.Basket;
import com.example.bookstore_back.modules.organization.model.Book;
import com.example.bookstore_back.modules.user.model.User;
import com.example.bookstore_back.modules.payment.repository.BasketRepository;
import com.example.bookstore_back.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private UserRepository userRepository;
    private BasketRepository basketRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            BasketRepository basketRepository,
            BCryptPasswordEncoder passwordEncoder,
            TokenService tokenService
    ){
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponse registration(RegisterRequest request) throws IOException {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent() ||
                userRepository.findUserByEmail(request.getEmail()) != null){
            return null;
        }
        User user = new User(
                request.getFirstname(),
                request.getSecondname(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                tokenService.generateToken(request.getUsername()),
                UserRole.USER.getAuthority(),
                ByteService.getString(request.getImage().getBytes())
        );
        userRepository.save(
                user
        );
        return LoginResponse.fromUser(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username).get();
    }

    public List<Book> getFavourites(User user){
        return user.getFavourites().stream().toList();
    }

    public void addFavourites(User user, Book book){
        user.getFavourites().add(book);
        userRepository.save(user);
    }

    public void addBookBought(User user, Book book){
        user.getBought().add(book);
        userRepository.save(user);
    }

    public void addBooksBought(User user, List<Book> books){
        user.getBought().addAll(books);
        userRepository.save(user);
    }

    public void changeRole(User user, String role){
        if(role.equals(UserRole.SELLER.getAuthority())){
            user.setRole(UserRole.SELLER.getAuthority());
        } else if(role.equals(UserRole.USER.getAuthority())){
            user.setRole(UserRole.USER.getAuthority());
        } else if(role.equals(UserRole.ADMIN.getAuthority())){
            user.setRole(UserRole.ADMIN.getAuthority());
        } else {
            throw new RuntimeException("There is no role like " + role);
        }
        userRepository.save(user);
    }

    public void changeRoleToUser(User user){
        user.setRole(UserRole.USER.getAuthority());
        userRepository.save(user);
    }

    public void changeRoleToAdmin(User user){
        user.setRole(UserRole.ADMIN.getAuthority());
        userRepository.save(user);
    }

    public boolean checkBookFavourites(User user, Book book){
        if (user.getFavourites().contains(book)) {
            return true;
        }
        return false;
    }

    public void updatePassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public String randomPassword(){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            password.append(randomChar);
        }
        return password.toString();
    }

    public User getByUsernameOrEmail(String usernameOrEmail){
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new RuntimeException("User is not exist!"));
    }

    public void removeFavourites(User user, Book book){
        user.getFavourites().remove(book);
        userRepository.save(user);
    }

    public List<Basket> getUserBaskets(User user){
        return user.getBaskets();
    }

    public void addBasketToUser(User user, Basket basket){
        user.getBaskets().add(basket);
        userRepository.save(user);
    }

    public Basket getCurrentBasket(User user){
        List<Basket> baskets = user.getBaskets()
                .stream().filter(
                        (b) -> (b.getStatus().equals(BasketStatus.CREATED.name())))
                .toList();
        Basket basket;
        if(baskets.size() == 0){
            basket = basketRepository.save(new Basket(BasketStatus.CREATED.name()));
            user.getBaskets().add(basket);
            userRepository.save(user);
            return basket;
        }
        basket = baskets.get(0);
        return basket;
    }

    public User getByToken(String token){
        return userRepository.findByToken(token).orElseThrow(() -> new RuntimeException("User is not exist!"));
    }

    public void activate(User user){
        user.setActivate(true);
        userRepository.save(user);
    }

}
