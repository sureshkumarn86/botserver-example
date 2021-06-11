package com.example.botserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(BotServerApplicationConfiguration.class)
@SpringBootApplication
public class BotServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotServerApplication.class, args);
    }

}
