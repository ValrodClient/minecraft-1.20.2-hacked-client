package com.valrod.client.modules.movement;

import java.awt.event.KeyEvent;

import com.valrod.client.VClient;
import com.valrod.client.events.Event;
import com.valrod.client.events.EventUpdate;
import com.valrod.client.modules.Category;
import com.valrod.client.modules.Module;
import com.valrod.client.ui.ingame.notifications.Notification;
import com.valrod.client.ui.ingame.notifications.NotificationType;

import net.minecraft.world.phys.Vec3;

public class Fly extends Module {

	public Fly() {
		super("Fly", "I believe I can fly", KeyEvent.VK_F, Category.MOVEMENT);
	}
	
	public void onEnable() {
		VClient.getNotificationManager().show(new Notification(NotificationType.INFO, getName(), getDescription(), 3));
	}
	
	public void onDisable() {
		
	}
	
	public void onUpdate(Event event) {
		if (event instanceof EventUpdate) {
			Vec3 playerMotion = this.mc.player.getDeltaMovement();
			this.mc.player.setDeltaMovement(playerMotion.x, 0, playerMotion.z);
		}
	}

}
