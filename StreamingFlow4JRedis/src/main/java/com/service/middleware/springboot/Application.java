package com.service.middleware.springboot;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@ComponentScan(value = {"com.service.middleware.*"})
public class Application {

	public static void main(final String[] aArgs)
	{
		new SpringApplicationBuilder(Application.class).run(aArgs);
	}
}
