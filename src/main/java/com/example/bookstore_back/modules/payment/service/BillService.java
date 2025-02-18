package com.example.bookstore_back.modules.payment.service;

import com.example.bookstore_back.modules.payment.dto.request.GenerateBillRequest;
import com.example.bookstore_back.modules.payment.model.Basket;
import com.example.bookstore_back.modules.payment.model.Bill;
import com.example.bookstore_back.modules.user.model.User;
import com.example.bookstore_back.modules.payment.repository.BillRepository;
import com.example.bookstore_back.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private BillRepository billRepository;
    private BasketService basketService;
    private UserService userService;

    @Autowired
    public BillService(
        BillRepository billRepository,
        BasketService basketService,
        UserService userService
    ){
        this.billRepository = billRepository;
        this.basketService = basketService;
        this.userService = userService;
    }

    public Bill generate(GenerateBillRequest request, Basket basket, String currentUsername){
        return billRepository.save(new Bill(
                basketService.getById(basket.getId()),
                request.getPaymentId(),
                userService.getUserByUsername(currentUsername),
                request.getStatus())
        );
    }

    public List<Bill> getByCustomer(User customer){
        return billRepository.findByCustomer(customer);
    }
}
