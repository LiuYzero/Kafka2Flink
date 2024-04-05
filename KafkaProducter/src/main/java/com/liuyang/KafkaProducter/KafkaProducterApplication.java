package com.liuyang.KafkaProducter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KafkaProducterApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducterApplication.class, args);
	}

}
