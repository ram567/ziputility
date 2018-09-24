package com.extarctor.extactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExtractorAppApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(ExtractorAppApplication.class, args);
		Extraction extraction =new Extraction();
		if(extraction!=null)
		extraction.extract();
	
	}
	
}
