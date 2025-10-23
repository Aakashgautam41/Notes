package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceBusDemoApplication implements CommandLineRunner {

	@Autowired
	private ServiceBusSender sender;

	public static void main(String[] args) {
		SpringApplication.run(ServiceBusDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		sender.send("Hello, Service Bus!");
	}

}
