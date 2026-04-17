package com.logyourmeals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LogYourMealsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogYourMealsApplication.class, args);
    }
}
