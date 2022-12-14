package com.w2m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class W2mApplication {

	public static void main(String[] args) {
		SpringApplication.run(W2mApplication.class, args);
	}

}
