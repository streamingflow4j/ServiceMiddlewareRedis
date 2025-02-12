package com.service.streamingflow4j.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonProcessingException;


import com.service.streamingflow4j.cep.handler.MonitorEventHandler;
import com.service.streamingflow4j.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class RedisMessageListener implements MessageListener {

	@Autowired
	private MonitorEventHandler monitorEventHandler;
	
	private StringRedisTemplate stringRedisTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(RedisMessageListener.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private Environment env;

	private String queueStreaming;

	private String queueRule;

	public void setQueueStreaming(String queueStreaming){
		this.queueStreaming = queueStreaming;
	}
	public void  setQueueRule(String queueRule){
		this.queueRule = queueRule;
	}

	public RedisMessageListener(Environment env, StringRedisTemplate stringRedisTemplate) throws Exception {
		this.stringRedisTemplate = stringRedisTemplate;
		this.env = env;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {

		byte[] topicChannel = message.getChannel();
		byte[] messageBody = message.getBody();
		if (topicChannel == null || messageBody == null) {
			return;
		}
		String msg = new String(messageBody);
		String topic = new String(topicChannel);
		if (topic.equals(queueStreaming)) {
			try {
				fireEvent(msg);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                     InvocationTargetException | JsonProcessingException e) {
				e.printStackTrace();
			}
		} else if (topic.equals(queueRule)) {
			try {
				toModel(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			LOG.error("Error! ===>> No event or different queue names.");
		}
	}

	public void fireEvent(String payload) throws NumberFormatException, NoSuchMethodException, SecurityException,
			JsonProcessingException, JsonMappingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			if (monitorEventHandler == null) {
				monitorEventHandler = new MonitorEventHandler(stringRedisTemplate);
			}
			Entity event = objectMapper.readValue(payload, Entity.class);
			monitorEventHandler.handleEntity(event);
		} catch (Exception e) {
			LOG.error("Error! ===>> " + e);
		}

	}

	public Entity toModel(String payload) throws JsonParseException, JsonMappingException, IOException {
		Entity myEntity = objectMapper.readValue(payload, Entity.class);
		try {
			if (monitorEventHandler == null) {
				monitorEventHandler = new MonitorEventHandler(stringRedisTemplate);
			}
			monitorEventHandler.createRequestMonitorExpression(myEntity);
		} catch (Exception e) {
			LOG.error("Error! ===>> " + e);
		}

		return myEntity;
	}

}