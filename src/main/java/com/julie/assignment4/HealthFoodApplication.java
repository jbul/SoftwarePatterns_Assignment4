package com.julie.assignment4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HealthFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthFoodApplication.class, args);
    }


    @Bean
    public CommandLineRunner run() {
        return (args) -> {

        };
    }
}
