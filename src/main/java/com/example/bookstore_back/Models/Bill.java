package com.example.bookstore_back.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;
    @Column
    private String paymentId;
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
    @Column
    private String status;
    @Column
    private LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Almaty"));

    public Bill(
        Basket basket,
        String paymentId,
        User customer,
        String status
    ){
        this.basket = basket;
        this.paymentId = paymentId;
        this.customer = customer;
        this.status = status;
    }

}
