package ru.verbitskiy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ControllerMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControllerMicroserviceApplication.class, args);
    }
}
