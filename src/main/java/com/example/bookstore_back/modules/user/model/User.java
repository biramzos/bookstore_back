package com.example.bookstore_back.modules.user.model;

import com.example.bookstore_back.modules.payment.model.Basket;
import com.example.bookstore_back.modules.organization.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstname;
    @Column
    private String secondname;
    @Column
    private String username;
    @JsonIgnore
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String token;
    @Column
    private String role;
    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String image;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> favourites = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_baskets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_id")
    )
    private List<Basket> baskets = new ArrayList<>();
    @Column
    private boolean activate = false;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_buyed",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> bought = new ArrayList<>();

//    public User(
//            String firstname,
//            String secondname,
//            String username,
//            String password,
//            String email,
//            String token,
//            String role,
//            byte[] image
//    ){
//        this.firstname = firstname;
//        this.secondname = secondname;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.token = token;
//        this.role = role;
//        this.image = image;
//    }
public User(
        String firstname,
        String secondname,
        String username,
        String password,
        String email,
        String token,
        String role,
        String image
){
    this.firstname = firstname;
    this.secondname = secondname;
    this.username = username;
    this.password = password;
    this.email = email;
    this.token = token;
    this.role = role;
    this.image = image;
}
}
