package com.example.bookstore_back.DataAccessObjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {
    private String oldpass;
    private String newpass;
}
