package com.valrod.client.modules.settings.mode;

import java.util.Arrays;
import java.util.List;

import com.valrod.client.modules.settings.Setting;

public class ModeSetting extends Setting {

	private List<Mode> modes;
	private int index;
	
	public ModeSetting(String name, String description, Mode... modes) {
		super(name, description);
		this.modes = Arrays.asList(modes);
		this.index = 0;
	}
	
	public Mode getCurrentMode() {
		return this.modes.get(this.index);
	}
	
	public String getCurrentModeName() {
		return this.modes.get(this.index).getName();
	}
	
	public String getCurrentModeDescription() {
		return this.modes.get(this.index).getDescription();
	}
	
	public boolean is(Mode mode) {
		return this.index == this.modes.indexOf(mode);
	}
	
	public void setCurrentMode(Mode mode) {
		if (this.modes.get(this.index) == mode)
			return;
		
		for (int i = 0; i < this.modes.size(); i++)
		{
			if (this.modes.get(i) == mode) {
				this.index = i;
				return;
			}
		}
	}

	public void cycle() {
		this.index = this.index == this.modes.size() - 1 ? 0 : ++this.index;
	}
	
}