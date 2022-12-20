package com.service.middleware.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.service.middleware.cep.handler.MonitorEventHandler;
import com.service.middleware.model.Entity;

@Service
public class RedisMessageListener implements MessageListener {

	@Autowired
	private MonitorEventHandler monitorEventHandler;
	
	private StringRedisTemplate stringRedisTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(RedisMessageListener.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private Environment env;


	public RedisMessageListener(Environment env, StringRedisTemplate stringRedisTemplate) throws Exception {
		this.stringRedisTemplate = stringRedisTemplate;
		this.env = env;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		// TODO Auto-generated method stub

		byte[] topicChannel = message.getChannel();
		byte[] messageBody = message.getBody();
		if (topicChannel == null || messageBody == null) {
			return;
		}
		String msg = new String(messageBody);
		String topic = new String(topicChannel);
		if (topic.equals(env.getProperty("queue.streaming.data"))) {
			try {
				fireEvent(msg);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (topic.equals(env.getProperty("queue.rule.cep"))) {
			try {
				toModel(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			LOG.error("Error! ===>> No event or different queue names.");
		}
	}

	public void fireEvent(String payload) throws NumberFormatException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			if (monitorEventHandler == null) {
				monitorEventHandler = new MonitorEventHandler(stringRedisTemplate);
			}
			Entity event = objectMapper.readValue(payload, Entity.class);
			monitorEventHandler.handleEntity(event);
		} catch (Exception e) {
			// TODO: handle exception
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
			// TODO: handle exception
			LOG.error("Error! ===>> " + e);
		}

		return myEntity;
	}

}