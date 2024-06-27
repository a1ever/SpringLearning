package ru.verbitskiy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KittyMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KittyMicroserviceApplication.class, args);
	}

}
