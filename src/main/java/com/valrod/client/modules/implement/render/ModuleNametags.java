package com.valrod.client.modules.implement.render;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.valrod.client.events.Event;
import com.valrod.client.events.EventEntityRender;
import com.valrod.client.modules.Category;
import com.valrod.client.modules.Module;
import com.valrod.utils.rendering.LevelRenderingUtils;

import net.minecraft.world.entity.Entity;

public class ModuleNametags extends Module {
	
	public ModuleNametags() {
		super("Nametags", "Display entity names", KeyEvent.VK_O, Category.RENDER);
	}

	public void onEnable() {
		
	}

	public void onDisable() {
		
	}

	public void onUpdate(Event event) {
		if (event instanceof EventEntityRender) {
			EventEntityRender e = (EventEntityRender)event;
			
			Entity ent = e.getEntity();
			if (ent.is(this.mc.player)) return;
			
			LevelRenderingUtils.renderNameTag(ent, ent.getName(), e.getPoseStack(), e.getMbs());
		}
	}
}
