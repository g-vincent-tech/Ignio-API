package com.foxploit.ignio.deviceinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DeviceInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceInfoServiceApplication.class, args);
	}

}
