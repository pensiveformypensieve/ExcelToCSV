package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;

@SpringBootApplication
public class ExcelreaderApplication {

	final static Logger log = Logger.getLogger(ExcelreaderApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(ExcelreaderApplication.class, args);
		log.debug("app ran successfully");
	}

}
