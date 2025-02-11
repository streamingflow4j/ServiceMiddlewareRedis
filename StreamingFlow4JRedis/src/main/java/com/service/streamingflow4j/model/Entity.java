package com.service.streamingflow4j.model;

import java.util.List;

public class Entity {
	private String id;
	private String type;
	private List<Attributes> attributes;

	public Entity() {
		super();
	}
    
	
	
	public Entity(String id, String type, List<Attributes> attributes) {
		super();
		this.id = id;
		this.type = type;
		this.attributes = attributes;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Attributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attributes> attributes) {
		this.attributes = attributes;
	}

}