package com.valrod.client;

import com.valrod.client.ui.ingame.notifications.NotificationManager;
import com.valrod.client.modules.ModuleManager;

import com.valrod.client.modules.implement.movement.*;
import com.valrod.client.modules.implement.render.*;
import com.valrod.client.modules.implement.ui.*;

public class VClient {

	public static final long startTime = System.currentTimeMillis();
	
	public static final String USERNAME = "Valrod";
	public static final String CLIENT_NAME = "ValrodClient";
	public static final String CLIENT_VERSION = "dev";
	
	private static ModuleManager moduleManager;
	private static NotificationManager notificationManager;

	public static void onStartup() {
		moduleManager = new ModuleManager();
		notificationManager = new NotificationManager();

		moduleManager.register(new ModuleFly());
		moduleManager.register(new ModulePlayerESP());
		moduleManager.register(new ModuleHUD());
		moduleManager.register(new ModuleNametags());
		
		moduleManager.enableModule("HUD");
	}

	public static ModuleManager getModuleManager() {
		return moduleManager;
	}
	
	public static NotificationManager getNotificationManager() {
		return notificationManager;
	}

	public static void onShutdown() {

	}

}
