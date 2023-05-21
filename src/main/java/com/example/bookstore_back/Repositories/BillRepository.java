package com.example.bookstore_back.Repositories;

import com.example.bookstore_back.Models.Bill;
import com.example.bookstore_back.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCustomer(User customer);
}
