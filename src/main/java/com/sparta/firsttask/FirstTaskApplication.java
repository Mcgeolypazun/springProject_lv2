package com.sparta.firsttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FirstTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstTaskApplication.class, args);
    }

}
