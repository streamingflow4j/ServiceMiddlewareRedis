package com.redis.endpoint.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.endpoint.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Environment env;
    private String payload;

    public RedisService(RedisTemplate<String, String> redisTemplate, Environment env) {
        this.redisTemplate = redisTemplate;
        this.env = env;
        payload = "";
    }

    public void sendData(Entity entity) throws JsonProcessingException {
        this.send(entity,getQUEUE_DATA());
    }

    public void sendRule(Entity entity) throws JsonProcessingException {
        this.send(entity,getQUEUE_RULE());
    }

    public String load(String propertyName) {
        return env.getRequiredProperty(propertyName);
    }

    public String getQUEUE_DATA() {
        return load("queue.streaming.data");

    }

    public String getQUEUE_RULE() {
        return load("queue.rule.cep");
    }

    public void send(Entity entity, String queue) throws JsonProcessingException {
        payload = objectMapper.writeValueAsString(entity).trim();
        redisTemplate.convertAndSend(queue, payload);
        log.info("Sent msg: {}" , payload);
    }
}
