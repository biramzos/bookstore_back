package com.example.bookstore_back.DataAccessObjects;

import com.example.bookstore_back.Models.Basket;
import com.example.bookstore_back.Models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String token;
    private String role;
    private List<Basket> baskets;

    public static LoginResponse fromUser(User user){
        return new LoginResponse(
                user.getId(),
                user.getFirstname() + " " + user.getSecondname(),
                user.getUsername(),
                user.getEmail(),
                user.getToken(),
                user.getRole(),
                user.getBaskets()
        );
    }

}
