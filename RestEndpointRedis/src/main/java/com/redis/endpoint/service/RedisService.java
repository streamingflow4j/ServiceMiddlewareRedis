package com.redis.endpoint.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.endpoint.model.Entity;
import com.redis.endpoint.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    private static final PropertyUtil propertyUtil = new PropertyUtil();
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String payload;

    @Value("${queue.streaming.data}")
    private String queueStreaming;
    @Value("${queue.rule.cep}")
    private String queueRule;

    public RedisService(RedisTemplate<String, String> redisTemplate, Environment env) {
        this.redisTemplate = redisTemplate;
        payload = "";
    }

    public void sendData(Entity entity) throws JsonProcessingException {
        this.send(entity,queueStreaming);
    }

    public void sendRule(Entity entity) throws JsonProcessingException {
        this.send(entity,queueRule);
    }

    public void send(Entity entity, String queue) throws JsonProcessingException {
        payload = objectMapper.writeValueAsString(entity).trim();
        redisTemplate.convertAndSend(queue, payload);
        log.info("Sent msg: {}" , payload);
    }
}
