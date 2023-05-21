package com.example.bookstore_back.Enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN,
    SELLER,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
