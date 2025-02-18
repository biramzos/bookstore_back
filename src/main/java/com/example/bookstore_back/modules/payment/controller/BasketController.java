package com.example.bookstore_back.modules.payment.controller;

import com.example.bookstore_back.config.EmailService;
import com.example.bookstore_back.config.PaymentService;
import com.example.bookstore_back.modules.payment.dto.request.GenerateBillRequest;
import com.example.bookstore_back.modules.payment.dto.response.PaymentResponse;
import com.example.bookstore_back.modules.payment.enums.BasketStatus;
import com.example.bookstore_back.modules.payment.model.Basket;
import com.example.bookstore_back.modules.payment.model.Bill;
import com.example.bookstore_back.modules.organization.model.Book;
import com.example.bookstore_back.modules.user.model.User;
import com.example.bookstore_back.modules.payment.service.BasketService;
import com.example.bookstore_back.modules.payment.service.BillService;
import com.example.bookstore_back.modules.organization.service.BookService;
import com.example.bookstore_back.modules.user.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/baskets")
public class BasketController {

    private BasketService basketService;
    private UserService userService;
    private BookService bookService;
    private PaymentService paymentService;
    private BillService billService;
    private EmailService emailService;

    @Autowired
    public BasketController(
            BasketService basketService,
            UserService userService,
            BookService bookService,
            PaymentService paymentService,
            BillService billService,
            EmailService emailService
    ){
        this.basketService = basketService;
        this.userService = userService;
        this.bookService = bookService;
        this.paymentService = paymentService;
        this.billService = billService;
        this.emailService = emailService;
    }

    @ResponseBody
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Basket> getBaskets(){
        if (basketService.baskets().size() == 0) {
            basketService.create();
            return basketService.baskets();
        }
        return basketService.baskets();
    }

    @ResponseBody
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public void addBasket(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        Basket basket = basketService.create();
        userService.addBasketToUser(user, basket);
    }

    @ResponseBody
    @GetMapping("/current/basket")
    @PreAuthorize("isAuthenticated()")
    public Basket getCurrentBasket(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return userService.getCurrentBasket(user);
    }

    @ResponseBody
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public List<Basket> getBasketsOfUser(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return user.getBaskets();
    }

    @ResponseBody
    @GetMapping("/{basketId}/add/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void addBook(@PathVariable("basketId") Basket basket, @NonNull @PathVariable("bookId") Book book) {
        basketService.addBookToBasket(basket, book);
    }

    @ResponseBody
    @GetMapping("/{basketId}/remove/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void removeBook(@PathVariable("basketId") Basket basket, @NonNull @PathVariable("bookId") Book book){
        basketService.removeBookFromBasket(basket, book);
    }

    @ResponseBody
    @GetMapping("/{basketId}/change-status")
    @PreAuthorize("isAuthenticated()")
    public Basket changeStatus(@PathVariable("basketId") Basket basket){
        basketService.changeStatus(basket.getId(), BasketStatus.REMOVED.name());
        return basket;
    }

    @ResponseBody
    @GetMapping("/get-bills")
    @PreAuthorize("isAuthenticated()")
    public List<Bill> getBillsByCustomer(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return billService.getByCustomer(user);
    }

    @ResponseBody
    @GetMapping("/bills/{billId}")
    @PreAuthorize("isAuthenticated()")
    public Bill getBillById(@PathVariable("billId") Bill bill){
        return bill;
    }

    @ResponseBody
    @GetMapping("/bills/{billId}/books")
    @PreAuthorize("isAuthenticated()")
    public List<Book> getBillsBooks(@PathVariable("billId") Bill bill){
        if(bill.getStatus().equals("success")){
            return bill.getBasket().getBooks();
        }
        throw new RuntimeException("Status is canceled!");
    }

    @ResponseBody
    @PostMapping("/{basketId}/payed")
    @PreAuthorize("isAuthenticated()")
    public Bill basketPayed(@PathVariable("basketId") Basket basket, @RequestBody GenerateBillRequest request, Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        Bill bill = billService.generate(request, basket, auth.getName());
        basketService.changeStatus(basket.getId(), BasketStatus.PAYED.name());
        String text = "";
        if(request.getStatus().equals("success")){
            userService.addBooksBought(user, basket.getBooks());
            text = "You have successfully purchased books!\n";
        } else {
            text = "Your order is fail!\n";
        }
        for(Book book:basket.getBooks()){
            text += "[" + book.getName() + "]\n";
        }
        emailService.send(text, user.getEmail());
        return bill;
    }

    @ResponseBody
    @PostMapping("/{basketId}/create-customer-id")
    @PreAuthorize("isAuthenticated()")
    public PaymentResponse createPayment(@PathVariable("basketId") Basket basket) {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(BigDecimal.valueOf(basket.getTotal()), "KZT");
            return new PaymentResponse(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            e.printStackTrace();
            return new PaymentResponse(null);
        }
    }

//    @ResponseBody
//    @PostMapping("/create-customer-id")
//    @PreAuthorize("isAuthenticated() or isAnonymous()")
//    public PaymentResponse createPaymentTsxt() {
//        try {
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(BigDecimal.valueOf(10000), "KZT");
//            return new PaymentResponse(paymentIntent.getClientSecret());
//        } catch (StripeException e) {
//            e.printStackTrace();
//            return new PaymentResponse(null);
//        }
//    }

}
