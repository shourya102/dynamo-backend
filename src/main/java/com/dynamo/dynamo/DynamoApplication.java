package com.dynamo.dynamo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DynamoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamoApplication.class, args);
    }

}
