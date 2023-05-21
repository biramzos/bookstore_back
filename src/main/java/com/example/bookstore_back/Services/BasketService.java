package com.example.bookstore_back.Services;

import com.example.bookstore_back.Models.Basket;
import com.example.bookstore_back.Enums.BasketStatus;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BasketService {

    private BasketRepository basketRepository;

    @Autowired
    public BasketService(
            BasketRepository basketRepository
    ){
        this.basketRepository = basketRepository;
    }

    public List<Basket> baskets(){
        return basketRepository.findAll();
    }

    public Basket create(){
        return basketRepository.save(new Basket(BasketStatus.CREATED.name()));
    }

    public void addBookToBasket(Basket basket, Book book){
        basket.getBooks().add(book);
        double total = 0;
        for(Book b: basket.getBooks()){
            total += b.getCost();
        }
        basket.setTotal(total);
        basketRepository.save(basket);
    }


    public void removeBookFromBasket(Basket basket, Book book){
        basket.getBooks().remove(book);
        double total = 0;
        for(Book b: basket.getBooks()){
            total += b.getCost();
        }
        basket.setTotal(total);
        basketRepository.save(basket);
    }

    public Basket changeStatus(Long id, String status){
        Basket basket = basketRepository.findBasketById(id).orElse(null);
        if (basket != null) {
            basket.setStatus(status);
            basket.setTime(LocalDateTime.now(ZoneId.of("Asia/Almaty")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")));
            return basketRepository.save(basket);
        }
        return null;
    }

    public Basket getById(Long basketId){
        return basketRepository.findBasketById(basketId).orElseThrow(() -> new RuntimeException("Basket is not exist!"));
    }
}
