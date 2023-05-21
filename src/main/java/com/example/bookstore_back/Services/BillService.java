package com.example.bookstore_back.Services;

import com.example.bookstore_back.DataAccessObjects.GenerateBillRequest;
import com.example.bookstore_back.Models.Basket;
import com.example.bookstore_back.Models.Bill;
import com.example.bookstore_back.Models.User;
import com.example.bookstore_back.Repositories.BillRepository;
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
