package com.rightdarkdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class RightdarkdocApplication {

	public static void main(String[] args) {
		SpringApplication.run(RightdarkdocApplication.class, args);
	}

}
