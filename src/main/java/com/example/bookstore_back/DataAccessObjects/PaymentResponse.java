package com.example.bookstore_back.DataAccessObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String clientSecretKey;
}
