package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.java.entities")
public class CassaAutomaticaApplication {
	public static void main(String[] args) {
		SpringApplication.run(CassaAutomaticaApplication.class, args);
	}
}

