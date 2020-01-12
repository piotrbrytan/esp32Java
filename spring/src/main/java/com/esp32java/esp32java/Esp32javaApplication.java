package com.esp32java.esp32java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.esp32java.esp32java", "com.controller"})
public class Esp32javaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Esp32javaApplication.class, args);
	}

}
