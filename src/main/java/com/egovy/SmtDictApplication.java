package com.egovy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.egovy.sementic.Connector;

@SpringBootApplication
public class SmtDictApplication {

	public static void main(String[] args) {
		Connector.getInstance().connect(); // connect to sementic database
		SpringApplication.run(SmtDictApplication.class, args);
	}
}
