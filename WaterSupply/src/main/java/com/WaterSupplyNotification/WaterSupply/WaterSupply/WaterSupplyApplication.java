package com.WaterSupplyNotification.WaterSupply.WaterSupply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WaterSupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterSupplyApplication.class, args);
	}

}
