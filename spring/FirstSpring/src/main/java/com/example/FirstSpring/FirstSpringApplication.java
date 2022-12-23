package com.example.FirstSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication

@SpringBootApplication
public class FirstSpringApplication {

	public static void main(String[] args) {
		//System.out.println("version: " + SpringVersion.getVersion());
		SpringApplication.run(FirstSpringApplication.class, args);
	}

}
