package com.service.streamingflow4j;

import com.service.streamingflow4j.util.RedisMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@SpringBootApplication(
		scanBasePackages = "com.service",
		exclude = {RedisVectorStoreAutoConfiguration.class, RedisAutoConfiguration.class })
public class Streamingflow4jApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Streamingflow4jApplication.class);
		//app.setAdditionalProfiles("dev");
		app.run(args);
	}
	private static final Logger LOG = LoggerFactory.getLogger(Streamingflow4jApplication.class);

	@Autowired
	private Environment env;
	@Value("${queue.streaming.data}")
	private String queueStreaming;
	@Value("${queue.rule.cep}")
	private String queueRule;
	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private Integer redisPort;


	@Bean
	@Primary
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().usePooling().build();
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
		JedisConnectionFactory factory = new JedisConnectionFactory(config,clientConfig);
		return factory;
	}

	@Bean
	@Primary
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
		return stringRedisTemplate;
	}

	@Bean
	RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@Bean
	public MessageListenerAdapter messageListener() throws Exception {
		RedisMessageListener redisMessageListener = new RedisMessageListener(env, stringRedisTemplate(jedisConnectionFactory()));
		redisMessageListener.setQueueStreaming(queueStreaming);
		redisMessageListener.setQueueRule(queueRule);
		return new MessageListenerAdapter(redisMessageListener);
	}

	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
														MessageListenerAdapter messageListener) throws Exception {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(messageListener(), new ChannelTopic(queueRule));
		container.addMessageListener(messageListener(), new ChannelTopic(queueStreaming));
		return container;
	}

}

