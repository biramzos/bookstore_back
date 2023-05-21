package com.example.bookstore_back.DataAccessObjects;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequest {
    private BigDecimal amount;
}
