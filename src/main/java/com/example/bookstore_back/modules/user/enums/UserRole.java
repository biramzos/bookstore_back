package com.example.bookstore_back.modules.user.enums;

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
