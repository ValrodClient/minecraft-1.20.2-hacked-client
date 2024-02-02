package com.valrod.client.events;

import net.minecraft.client.gui.GuiGraphics;

public class EventRender2D extends Event{

	private GuiGraphics graphics;
	private float partialTick;

	public EventRender2D(GuiGraphics graphics, float partialTick) {
		this.graphics = graphics;
		this.partialTick = partialTick;
	}

	public GuiGraphics getGraphics() {
		return graphics;
	}

	public float getPartialTick() {
		return partialTick;
	}

}
