package com.example.bookstore_back.modules.payment.repository;

import com.example.bookstore_back.modules.payment.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findBasketsByStatus(String status);
    Optional<Basket> findBasketById(Long id);
}
