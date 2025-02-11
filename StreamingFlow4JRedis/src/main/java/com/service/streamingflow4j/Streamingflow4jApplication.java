package com.service.streamingflow4j;

import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {RedisVectorStoreAutoConfiguration.class })
//@ComponentScan(value = {"com.service.streamingflow4j.*"})
public class Streamingflow4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(Streamingflow4jApplication.class, args);
	}

}
