package com.redis.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class EndpointApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EndpointApplication.class);
		//app.setAdditionalProfiles("prod");
		app.run(args);
	}

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private Integer redisPort;

/*
	@Bean
	public String getQueueStreaming(){
		return queueStreaming;
	}

	@Bean
	public String getQueueRule(){
		return queueRule;
	}*/

	@Bean
	@Primary
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().usePooling().build();
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
		JedisConnectionFactory factory = new JedisConnectionFactory(config,clientConfig);
		return factory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}
}


