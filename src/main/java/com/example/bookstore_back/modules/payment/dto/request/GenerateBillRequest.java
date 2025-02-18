package com.example.bookstore_back.modules.payment.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenerateBillRequest {

    private String paymentId;
    private String status;

}
