package com.example.bookstore_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@EnableTransactionManagement
public class BookstoreBackApplication {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BookstoreBackApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BookstoreBackApplication.class, args);
    }

}
