package com.foxploit.ignio.devicedataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DeviceDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceDataServiceApplication.class, args);
	}

}
