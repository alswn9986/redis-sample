package com.example.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisSampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisSampleApplication.class, args);
	}
}
