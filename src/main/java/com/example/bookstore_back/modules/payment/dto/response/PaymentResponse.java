package com.example.bookstore_back.modules.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String clientSecretKey;
}
