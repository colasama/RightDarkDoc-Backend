package com.rightdarkdoc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.rightdarkdoc.dao")
@EnableAsync
public class RightdarkdocApplication {

	public static void main(String[] args) {
		SpringApplication.run(RightdarkdocApplication.class, args);
	}

}
