package com.example.bookstore_back.modules.organization.dto.request;

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
