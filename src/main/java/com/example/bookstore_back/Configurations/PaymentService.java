package com.example.bookstore_back.Configurations;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PaymentService {
    @Value("${stripe.api.key}")
    private String apiKey;

    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency) throws StripeException {
        Stripe.apiKey = apiKey;
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(toStripeAmount(amount))
                .setCurrency(currency)
                .addPaymentMethodType("card")
                .build();
        return PaymentIntent.create(params);
    }

    private Long toStripeAmount(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100)).longValueExact();
    }
}
