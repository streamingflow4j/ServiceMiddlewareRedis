package com.service.streamingflow4j.config;

import com.service.streamingflow4j.util.RedisMessageListener;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

//@Configuration
public class RedisConfig {
/*
    @Autowired
    public Environment env;
    @Value("${queue.streaming.data}")
    public String queueStreaming;
    @Value("${queue.rule.cep}")
    public String queueRule;
    @Value("${spring.redis.host}")
    public String redisHost;
    @Value("${spring.redis.port}")
    public Integer redisPort;

    @Bean
    public String getQueueStreaming(){
        return queueStreaming;
    }
    @Bean
    public String getQueueRule(){
        return queueRule;
    }

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
        return new MessageListenerAdapter(new RedisMessageListener(env, stringRedisTemplate(jedisConnectionFactory())));
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
*/
}
