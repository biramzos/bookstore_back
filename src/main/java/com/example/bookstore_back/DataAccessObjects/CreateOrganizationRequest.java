package com.example.bookstore_back.DataAccessObjects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrganizationRequest {
    private String name;
    private String username;
}
