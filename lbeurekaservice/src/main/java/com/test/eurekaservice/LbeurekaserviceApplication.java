package com.test.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LbeurekaserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LbeurekaserviceApplication.class, args);
	}

}
