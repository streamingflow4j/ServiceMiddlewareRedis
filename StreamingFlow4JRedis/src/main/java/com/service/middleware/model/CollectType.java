package com.service.middleware.model;

public enum CollectType{ 
	NONE ("NONE"),
	EVENT_CREATE_TYPE("EVENT_CREATE"), //add event type, every before specific rule
	RULE_CREATE_TYPE("RULE_CREATE"), //define addiction of a rule
	RULE_ATTR_NAME ("RULE_QUERY"), //indicate the QUERY to be added(EX.: select temperature from Termometro.win:time(5 sec))
	ADD_RULE_ATTR_QUEUE ("RULE_QUEUE"),// define destination queue of rule trigger
	EDIT_RULE_TYPE("RULE_UPDATE"), //define EDITION of a rule
	RULE_ATTR_ID ("RULE_ID"), //ID of rule to be EDITED/deleted
	DEL_RULE_TYPE ("RULE_DELETE");   //command to delete rule
	
	
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