package ua.com.foxminded.yuriy.carrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRestServiceApplication.class, args);
	}

//	@Bean
//	public FlywayMigrationStrategy flywayMigrationStrategy() {
//		return flyway -> {
//			flyway.clean();
//			flyway.migrate();
//		};
//	}

}
