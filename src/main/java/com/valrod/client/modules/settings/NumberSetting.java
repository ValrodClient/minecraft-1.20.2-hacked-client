package com.valrod.client.modules.settings;

import net.minecraft.util.Mth;

public class NumberSetting extends Setting {

	private float value, minimum, maximum, increment;
	
	public NumberSetting(String name, String description, float value, float minimum, float maximum, float increment) {
		super(name, description);
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		value = Mth.lerp(value, this.minimum, this.maximum); // cap value to range
		this.value = Math.round(value / this.increment) * this.increment; // round value using increment
	}

	public float getMinimum() {
		return minimum;
	}

	public void setMinimum(float minimum) {
		this.minimum = minimum;
	}

	public float getMaximum() {
		return maximum;
	}

	public void setMaximum(float maximum) {
		this.maximum = maximum;
	}

	public float getIncrement() {
		return increment;
	}

	public void setIncrement(float increment) {
		this.increment = increment;
	}

}
