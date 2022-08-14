package com.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableScheduling
public class BootSwagger3Application {

	public static void main(String[] args) {
		SpringApplication.run(BootSwagger3Application.class, args);
	}

}
