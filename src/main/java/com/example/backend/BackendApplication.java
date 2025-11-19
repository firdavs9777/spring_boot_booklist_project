package com.example.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		// Load .env file if it exists (must be done BEFORE SpringApplication.run)
		try {
			Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing()
					.load();
			// Set system properties from .env file (Spring Boot reads these)
			dotenv.entries().forEach(entry -> {
				String key = entry.getKey();
				String value = entry.getValue();
				// Set as system property (Spring Boot reads these)
				System.setProperty(key, value);
			});
			System.out.println("✓ .env file loaded successfully");
			// Debug: verify database password is loaded (don't print the actual password)
			String dbPassword = System.getProperty("DATABASE_PASSWORD");
			if (dbPassword != null && !dbPassword.isEmpty()) {
				System.out.println("✓ DATABASE_PASSWORD is set (length: " + dbPassword.length() + ")");
			} else {
				System.out.println("⚠ WARNING: DATABASE_PASSWORD is not set! Check your .env file.");
			}
		} catch (Exception e) {
			// .env file is optional, continue without it
			System.out.println("⚠ Note: .env file not found or could not be loaded.");
			System.out.println("   Error: " + e.getMessage());
			System.out.println("   Using system environment variables or defaults.");
		}
		
		SpringApplication.run(BackendApplication.class, args);
	}

}
