package com.happystudy.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages= {"com.happystudy"})
@MapperScan(basePackages="com.happystudy.dao")
public class HappyStudyApplication extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(HappyStudyApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(HappyStudyApplication.class, args);
	}

}
