package com.technicalTest.technicalTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.technicalTest.technicalTest.service.FileProcessingService;

@SpringBootApplication
@EnableScheduling
public class TechnicalTestApplication {

	@Autowired
	private FileProcessingService fileProcessingService;

	public static void main(String[] args) {
		SpringApplication.run(TechnicalTestApplication.class, args);
	}

	// Run every 60 seconds (1 minute)
	@Scheduled(fixedRate = 60000)
	public void scheduleFileProcessing() {
		fileProcessingService.processFiles(); // Call the service directly
	}
}
