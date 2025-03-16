package com.clientwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main entry point for the Client Wallet application.
 * <p>
 * This is a Spring Boot application that serves as the main class to start the Client Wallet application.
 * It uses the {@link SpringBootApplication} annotation, which is a convenience annotation that includes:
 * - {@link EnableAutoConfiguration}
 * - {@link ComponentScan}
 * - {@link Configuration}
 * </p>
 * <p>
 * The {@link SpringApplication} class is used to launch the application.
 * </p>
 */
@SpringBootApplication
public class ClientWalletApplication {

	/**
	 * The main method that serves as the entry point to the application.
	 * <p>
	 * This method uses {@link SpringApplication#run(Class, String...)} to launch the Spring Boot application,
	 * which will automatically configure and start the embedded web server (usually Tomcat) and handle the setup
	 * of application components such as controllers, services, and repositories.
	 * </p>
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClientWalletApplication.class, args);
	}
}
