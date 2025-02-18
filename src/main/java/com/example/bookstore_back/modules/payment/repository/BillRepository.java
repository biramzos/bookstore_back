package com.example.bookstore_back.modules.payment.repository;

import com.example.bookstore_back.modules.payment.model.Bill;
import com.example.bookstore_back.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCustomer(User customer);
}
