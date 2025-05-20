package com.jo.IDDSI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Joseph Kianda Tshava
 *
 * Entry point for the IDDSI Application.
 *
 * This is a Spring Boot application class that initializes and launches
 * the application when the main method is executed. It uses the
 * {@code @SpringBootApplication} annotation to enable Spring Boot's
 * auto-configuration and component scanning features.
 */
@SpringBootApplication
public class IddsiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IddsiApplication.class, args);
	}

}
