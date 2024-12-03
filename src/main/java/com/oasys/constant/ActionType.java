package com.oasys.constant;

public enum ActionType {
	ADD("Add",0), MODIFY("Modify",1), DELETE("Delete",2);

	private ActionType(String type, int id) {
		this.type = type;
		this.id = id;

	}

	String type;
	int id;

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}
}
