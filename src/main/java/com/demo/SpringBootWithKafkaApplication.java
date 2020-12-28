package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@SpringBootApplication
@EnableCaching
public class SpringBootWithKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithKafkaApplication.class, args);
	}
}
