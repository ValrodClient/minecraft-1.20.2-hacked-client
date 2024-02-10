package com.valrod.client.modules.settings;

public class Setting {
	
	private String name, description;
	
	public Setting(String name, String description) {
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
