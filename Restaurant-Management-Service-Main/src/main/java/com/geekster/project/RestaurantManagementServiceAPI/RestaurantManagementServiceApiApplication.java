package com.geekster.project.RestaurantManagementServiceAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.geekster.project.RestaurantManagementServiceAPI.Repository")
@EntityScan(basePackages = "com.geekster.project.RestaurantManagementServiceAPI.Model")
public class RestaurantManagementServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagementServiceApiApplication.class, args);
	}

}
