package com.valrod.client.modules.implement.ui;

import java.awt.Color;

import com.valrod.client.VClient;
import com.valrod.client.events.Event;
import com.valrod.client.events.EventRender2D;
import com.valrod.client.modules.Category;
import com.valrod.client.modules.Module;
import com.valrod.utils.rendering.GuiRenderingUtils;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class ModuleHUD extends Module {

	public ModuleHUD() {
		super("HUD", "display informations", -1, Category.RENDER);
	}

	public void onEnable() {
		
	}

	public void onDisable() {
	}

	public void onUpdate(Event event) {
		if (!(event instanceof EventRender2D))
			return;

		GuiGraphics g = ((EventRender2D)event).getGraphics();
		//		float partialTick = ((EventRender2D)event).getPartialTick();

//		GuiRenderingUtils.fillRounded(g, 30, 30, 40, 40, 4, Color.pink.getRGB());
//		GuiRenderingUtils.circle(g, 14, 14, 12, Color.cyan.getRGB());

		renderModList(g);
		renderNotifications(g);
	}

	private void renderNotifications(GuiGraphics g) {
		VClient.getNotificationManager().render(g);

		/*
		Font font = Minecraft.getInstance().font;
		String title = "Notif test";
		String description = "This is a description";

		int textMargin = 5;
		int statusBarWidth = 3;

		int notifWidth = 180;
		int notifHeight = (font.lineHeight * 2) + (textMargin * 3);

		int windowWidth = g.guiWidth();
		int windowHeight = g.guiHeight();
		int marginToScreen = 6;

		int notifX = windowWidth - notifWidth - marginToScreen;
		int notifY = windowHeight - notifHeight - marginToScreen;

		int backgroundColor = new Color(0, 0, 0, 230).getRGB();
		int accentColor = new Color(255, 120, 240).getRGB();
		int textColor = new Color(245, 245, 245).getRGB();

		g.fill(notifX + statusBarWidth, notifY, notifX + notifWidth, notifY + notifHeight, backgroundColor); // Draw background
		g.fill(notifX, notifY, notifX + statusBarWidth, notifY + notifHeight, accentColor); // Draw notification type color bar
		g.drawString(font, title, notifX + textMargin + statusBarWidth, notifY + textMargin, textColor); // Draw title
		g.drawString(font, description, notifX + textMargin + statusBarWidth, notifY + font.lineHeight + (textMargin * 2), textColor); // Draw description
		 */
	}

	private void renderModList(GuiGraphics g) {
		int windowWidth = g.guiWidth();
		Font font = this.mc.font;

		int modCount = 0;
		for (Module mod : VClient.getModuleManager().getEnabledModules()) {
			if (mod.getName().equals("HUD"))
				continue;

			String text = mod.getName();
			g.drawString(font, text, windowWidth - font.width(text), (font.lineHeight * modCount), -1);

			modCount++;
		}
	}

}
