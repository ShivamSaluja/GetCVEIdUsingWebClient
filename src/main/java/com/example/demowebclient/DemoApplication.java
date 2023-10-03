package com.example.demowebclient;

import com.example.demowebclient.service.CVEDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	private final CVEDataService cveDataService;

	public DemoApplication(CVEDataService cveDataService) {
		this.cveDataService = cveDataService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws RuntimeException {
		if (args.length > 0) {
			log.info("Received {} command-line arguments ",   args.length );
			Arrays.stream(args).forEach(cveDataService::fetchCVEDataResults);
		} else {
			log.error("No command-line arguments provided.");
		}
	}

}

