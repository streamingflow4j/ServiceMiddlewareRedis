package com.redis.endpoint.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.redis.endpoint.model.Entity;
import com.redis.endpoint.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping(value = "/redis")
@RestController
public class RedisWebController {

    private static final Logger LOG = LoggerFactory.getLogger(RedisWebController.class);
    private static final Gson gson = new Gson();
    @Autowired
    RedisService redisSender;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/data/create")
    public ResponseEntity<String> newEntity(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendData(entity);
        return ResponseEntity.ok(gson.toJson("Message sent to Redis JavaInUse Successfully"));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/event/create")
    public ResponseEntity<String> newEvent(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return ResponseEntity.ok(gson.toJson("Message sent to the Redis JavaInUse Successfully"));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/rule/create")
    public ResponseEntity<String> createRule(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return ResponseEntity.ok(gson.toJson("Message sent to the Redis JavaInUse Successfully"));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/rule/update")
    public ResponseEntity<String> updateRule(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return ResponseEntity.ok(gson.toJson("Message sent to the Redis JavaInUse Successfully"));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/rule/delete")
    public ResponseEntity<String> deleteRule(@RequestBody Entity entity) throws JsonProcessingException {
        redisSender.sendRule(entity);
        return ResponseEntity.ok(gson.toJson("Message sent to the Redis JavaInUse Successfully"));
    }

}