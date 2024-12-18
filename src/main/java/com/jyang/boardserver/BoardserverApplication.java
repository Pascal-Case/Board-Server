package com.jyang.boardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BoardserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardserverApplication.class, args);
    }

}
