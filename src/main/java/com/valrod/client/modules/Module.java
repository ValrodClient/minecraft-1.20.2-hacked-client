package com.valrod.client.modules;

import java.util.ArrayList;
import java.util.Arrays;

import com.valrod.client.events.Event;
import com.valrod.client.modules.settings.Setting;

import net.minecraft.client.Minecraft;

public class Module implements Comparable<Module>{

	private String name, description;
	private int keyCode;
	private Category category;
	private boolean enabled;
	public Minecraft mc;
	private ArrayList<Setting> settings;

	public Module(String name, String description, int keyCode, Category category) {
		this.name = name;
		this.description = description;
		this.keyCode = keyCode;
		this.category = category;
		this.mc = Minecraft.getInstance();
		this.settings = new ArrayList<Setting>();
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void toggle() {
		this.enabled = !this.enabled;
		if(this.enabled) {
			this.onEnable();
		}else {
			this.onDisable();
		}
	}

	public void registerSettings(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
	}
	
	public ArrayList<Setting> getSettings() {
		return this.settings;
	}

	public void onDisable() {

	}

	public void onEnable() {

	}

	public void onUpdate(Event event) {

	}

	@Override
	public String toString() {
		return "Module [name=" + name + ", keyCode=" + keyCode + ", category=" + category + "]";
	}

	@Override
	public int compareTo(Module o) {
		//		FontRenderer fr = this.mc.fontRendererObj;
		//		int len1 = fr.getStringWidth(getName());
		//		int len2 = fr.getStringWidth(o.getName());
		//
		//		return MathHelper.clamp(len1 - len2, -1, 1);
		return -1;
	}

}
