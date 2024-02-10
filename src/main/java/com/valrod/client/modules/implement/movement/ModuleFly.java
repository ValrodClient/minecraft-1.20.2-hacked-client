package com.valrod.client.modules.implement.movement;

import java.awt.event.KeyEvent;

import com.valrod.client.VClient;
import com.valrod.client.events.Event;
import com.valrod.client.events.EventUpdate;
import com.valrod.client.modules.Category;
import com.valrod.client.modules.Module;
import com.valrod.client.modules.settings.mode.Mode;
import com.valrod.client.modules.settings.mode.ModeSetting;
import com.valrod.client.ui.ingame.notifications.Notification;
import com.valrod.client.ui.ingame.notifications.NotificationType;

import net.minecraft.world.phys.Vec3;

public class ModuleFly extends Module {

	private final Mode MODE_VANILLA = new Mode("Vanilla", "Fly on vanilla servers");

	private ModeSetting mode = new ModeSetting("Mode", "Bypass mode", MODE_VANILLA);

	public ModuleFly() {
		super("Fly", "I believe I can fly", KeyEvent.VK_F, Category.MOVEMENT);
		this.registerSettings(this.mode);
	}

	public void onEnable() {
		VClient.getNotificationManager().show(new Notification(NotificationType.INFO, getName(), "Bypass: " + this.mode.getCurrentMode(), 3));
	}

	public void onDisable() {
	}

	public void onUpdate(Event event) {
		Mode mode = this.mode.getCurrentMode();
		
		if (mode == MODE_VANILLA) { vanilla(event); }
	}

	private void vanilla(Event event) {
		if (event instanceof EventUpdate) {
			Vec3 playerMotion = this.mc.player.getDeltaMovement();
			this.mc.player.setDeltaMovement(playerMotion.x, 0, playerMotion.z);
		}
	}

	private void aac(Event event) {
		
	}

}
