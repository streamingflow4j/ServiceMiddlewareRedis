package com.redis.endpoint.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redis.endpoint.model.Entity;
import com.redis.endpoint.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping(value = "/redis")
@RestController
public class RedisWebController {

    private static final Logger LOG = LoggerFactory.getLogger(RedisWebController.class);

    @Autowired
    RedisService redisSender;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/data/create")
    public String newEntity(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendData(entity);
        return "Message sent to the Kafka JavaInUse Successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/event/create")
    public String newEvent(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return "Message sent to the Kafka JavaInUse Successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/rule/create")
    public String createRule(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return "Message sent to the Kafka JavaInUse Successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/rule/update")
    public String updateRule(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return "Message sent to the Kafka JavaInUse Successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/rule/delete")
    public String deleteRule(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return "Message sent to the Kafka JavaInUse Successfully";
    }

}