package com.service.streamingflow4j.cep.subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import com.service.streamingflow4j.model.Attributes;
import com.service.streamingflow4j.model.CollectType;
import com.service.streamingflow4j.model.Entity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;



@Component
public class MonitorEventSubscriber implements StatementSubscriber {

	String rule = "";
	Map<String, String> eventUpdate = new HashMap<String, String>();
	Entity myEntity;

	private StringRedisTemplate stringRedisTemplate;

	
	public MonitorEventSubscriber(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

	/**
	 * {@inheritDoc}
	 */
	public String getStatement() {
		return rule;
	}

	/**
	 * Listener method called when Esper has detected a pattern match.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(Map<String, Double> eventMap) throws Exception {
		StringBuilder sb = new StringBuilder();
		int count = 0;

		for (Entry<String, Double> entry : eventMap.entrySet()) {
			if (count != 0) {
				sb.append(",");
			}
			sb.append(getPayloadProperty(entry.getKey(), String.valueOf(entry.getValue())));
			count++;
		}
		@SuppressWarnings("resource")
		String payload = getPayload(sb.toString());

		for (Entry<String, String> entry : eventUpdate.entrySet()) {

			if (entry.getKey().equals(CollectType.ADD_RULE_ATTR_QUEUE.getName())) {
				stringRedisTemplate.convertAndSend(entry.getValue(), payload);
			}

		}

	}

	public String getPayloadProperty(String key, String value) {
		return "{\"name\":\"" + key + "\",\"type\" :\"String\",\"value\":\"" + value + "\"}";
	}

	public String getPayload(String value) {
		return "{\"type\" : \"EventCEP\",\"id\" : \"" + String.valueOf(System.currentTimeMillis())
				+ "\",\"attributes\" : [" + value + "]}";
	}

	public String getRule() {
		return rule;
	}

	private void setRule(String rule) {
		this.rule = rule;
	}

	public Map<String, String> getEventUpdate() {
		return eventUpdate;
	}

	private void setEventUpdate(Map<String, String> eventUpdate) {
		this.eventUpdate = eventUpdate;
	}

	public Entity getMyEntity() {
		return myEntity;
	}

	public String setMyEntity(Entity myEntity) {
		this.myEntity = myEntity;
		Map<String, String> update = new HashMap<String, String>();
		for (Attributes rule : myEntity.getAttributes()) {
			if (verifyDelRule(myEntity)) {
				if (rule.getName().equals(CollectType.RULE_ATTR_ID.getName())) {
					return rule.getValue();
				}
			} else {
				if (rule.getName().equals(CollectType.RULE_ATTR_NAME.getName())) {
					this.setRule(rule.getValue());
				} else {
					update.put(rule.getType(), rule.getValue());
				}
			}
		}
		this.setEventUpdate(update);
		return CollectType.NONE.getName();
	}

	public boolean verifyDelRule(Entity myEntity) {
		boolean result = false;

		if (myEntity.getType().equals(CollectType.DEL_RULE_TYPE.getName())) {
			result = true;
		}

		return result;
	}

}