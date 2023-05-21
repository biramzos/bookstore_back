package com.example.bookstore_back.Repositories;

import com.example.bookstore_back.Models.Basket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findBasketsByStatus(String status);
    Optional<Basket> findBasketById(Long id);
}
