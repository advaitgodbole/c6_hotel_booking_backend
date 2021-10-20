package com.sweethome.bookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


import java.util.ArrayList;
import java.util.Random;

@SpringBootApplication
// @EnableEurekaClient
public class BookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingServiceApplication.class, args);
	}

	public static ArrayList<String> getRandomNumbers(int count){
		Random rand = new Random();
		int upperBound = 100;
		ArrayList<String>numberList = new ArrayList<String>();                 
 
        for (int i=0; i<count; i++){
			numberList.add(
				String.valueOf(
					rand.nextInt(upperBound)
				)
			);                 
		}                 
 
		return numberList;                 
	} 
}
