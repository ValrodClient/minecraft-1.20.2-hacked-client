package com.valrod.client.modules.settings;

import com.valrod.client.modules.settings.mode.Mode;
import com.valrod.client.modules.settings.mode.ModeSetting;

public class BooleanSetting extends Setting {

	private boolean enabled;
	private Setting parentSetting;
	private Mode parentMode;
	
	public BooleanSetting(String name, String description, boolean enabledByDefault) {
		super(name, description);
		this.enabled = enabledByDefault;
	}
	
	public BooleanSetting(String name, String description, boolean enabledByDefault, BooleanSetting parentSetting) {
		this(name, description, enabledByDefault);
		this.parentSetting = parentSetting;
	}
	
	public BooleanSetting(String name, String description, boolean enabledByDefault, ModeSetting parentSetting, Mode parentMode) {
		this(name, description, enabledByDefault);
		this.parentSetting = parentSetting;
		this.parentMode = parentMode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Setting getParentSetting() {
		return parentSetting;
	}

	public Mode getParentMode() {
		return parentMode;
	}

	public void setParentMode(Mode parentMode) {
		this.parentMode = parentMode;
	}
	
	public boolean isParentSettingEnabled() {
		if (parentSetting instanceof BooleanSetting)
			return ((BooleanSetting)parentSetting).isEnabled();
		
		if (parentSetting instanceof ModeSetting)
			return ((ModeSetting)parentSetting).is(this.parentMode);
			
		return true;
	}
	
	public void toggle() {
		this.enabled = !this.enabled;
	}

}
