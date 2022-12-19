package com.service.middleware.model;

public enum CollectType{ 
	NONE ("NONE"),
	ADD_EVENT_TYPE("ADD_EVENT"), //add event type, every before specific rule
	ADD_RULE_TYPE("RULECEP"), //define addiction of a rule
	RULE_ATTR_NAME ("RULE"), //indicate the QUERY to be add(EX.: select temperature from Termometro.win:time(5 sec)) 
	ADD_RULE_ATTR_QUEUE ("QUEUE"),// define destination queue of rule trigger
	EDIT_RULE_TYPE("EDIT_RULECEP"), //define EDITION of a rule	
	RULE_ATTR_ID ("RULE_ID"), //ID of rule to be EDITED/deleted
	DEL_RULE_TYPE ("DEL_RULE");   //command to delete rule
	
	
	private String name;

	CollectType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static CollectType getEnumFromString(String value){
		if(value != null){
			for(CollectType type : CollectType.values()){
				if(value.equalsIgnoreCase(type.name)){
					return type;
				}
			}
		}
		return null;
	}
};