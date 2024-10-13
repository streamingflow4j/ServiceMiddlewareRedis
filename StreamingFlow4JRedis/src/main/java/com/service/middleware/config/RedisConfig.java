package com.service.middleware.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.service.middleware.util.RedisMessageListener;


@Configuration
public class RedisConfig {
    	
	@Value("${queue.streaming.data}")
	private String queueStreaming;

    @Value("${queue.rule.cep}")
	private String queueRule;

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private Integer redisPort;

	@Value("${spring.redis.channel.events}")
	private static String channelTopic;
	
	@Autowired
	private Environment env;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);
	
    
	public RedisConfig() {
	//	super();
		// TODO Auto-generated constructor stub
		 
//	     LOGGER.info("{}={}", "spring.redis.port", env.getProperty("spring.redis.port"));
//	     LOGGER.info("{}={}", "spring.redis.channel.events", env.getProperty("spring.redis.channel.events"));
	}

	@Bean
	@Primary
	public JedisConnectionFactory jedisConnectionFactory() {
		LOGGER.info("{}={}", "spring.redis.host", redisHost);
		LOGGER.info("{}={}", "spring.redis.port", redisPort);
		JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().usePooling().build();
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
		JedisConnectionFactory factory = new JedisConnectionFactory(config,clientConfig);
		return factory;
	}
	
	@Bean
	@Primary
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(connectionFactory);
		return stringRedisTemplate;
	}

	@Bean
	public MessageListenerAdapter messageListener() throws Exception {
		return new MessageListenerAdapter(new RedisMessageListener(env, stringRedisTemplate(jedisConnectionFactory())));
	}

	@Bean
	public ChannelTopic topic() {
		LOGGER.debug(queueRule);
		return new ChannelTopic(queueRule);
	}

	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter messageListener) throws Exception {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		
		LOGGER.debug(queueStreaming);
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(messageListener(), topic());
		container.addMessageListener(messageListener(), new ChannelTopic(queueStreaming));
		return container;
	}


}
