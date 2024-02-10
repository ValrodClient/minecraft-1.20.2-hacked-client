package com.valrod.client.modules.settings.mode;

public class Mode {

	private String name, description;

	public Mode(String name) {
		super();
		this.name = name;
		this.description = "";
	}
	
	public Mode(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
}
