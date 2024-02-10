package com.valrod.client.ui.ingame.notifications;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class Notification {
	private NotificationType type;
	private int notificationColor;
	private String title;
	private String description;
	private long start;

	private double fadedIn;
	private double fadeOut;
	private double end;
	
	private final int textMargin = 5;
	private final int statusBarWidth = 3;
	private final int notifWidth = 180;
	private final int notifHeight = (Minecraft.getInstance().font.lineHeight * 2) + (textMargin * 3);
	private final int marginToScreen = 4;

	private final int backgroundColor = new Color(0, 0, 0, 230).getRGB();
	private final int textColor = new Color(245, 245, 245).getRGB();
	
	public Notification(NotificationType type, String title, String messsage, int length) {
		this.type = type;
		this.title = title;
		this.description = messsage;
		
		switch (type) {
			case INFO -> this.notificationColor = new Color(255, 120, 240).getRGB();
			case WARNING -> this.notificationColor = new Color(204, 193, 0).getRGB();
			case ERROR -> this.notificationColor = new Color(204, 0, 18).getRGB();
		}

		this.fadedIn = 200 * length;
		this.fadeOut = this.fadedIn + 500 * length;
		this.end = this.fadeOut + this.fadedIn;
	}

	public void show() {
		start = System.currentTimeMillis();
	}

	public boolean isShown() {
		return getTime() <= end;
	}

	private long getTime() {
		return System.currentTimeMillis() - start;
	}

	public void render(GuiGraphics g) {
		final Font font = Minecraft.getInstance().font;
		final int windowWidth = g.guiWidth();
		final int windowHeight = g.guiHeight();

		float offsetX = 0;
		float offsetY = 0;
		long time = getTime();

		if (time < fadedIn) {
			// is poping in
			offsetX = (float) Math.tanh(time / fadedIn * 5.0);
			offsetY = (float) Math.tanh(time / fadedIn * 3.0);
		} else if (time > fadeOut) {
			// is poping out
			offsetX = (float) (Math.tanh(3.0 - (time - fadeOut) / (end - fadeOut) * 4.0));
			offsetY = 1;
		} else {
			// normal
			offsetX = offsetY = 1;
		}
		
		float opacityFactor = Mth.clamp((offsetX - 0.2F) * 1.4F, 0F, 1F);
		
		int notifX = windowWidth - (int)(this.notifWidth * offsetX) - this.marginToScreen;
		int notifY = windowHeight - (int)(this.notifHeight * offsetY) - this.marginToScreen - 2;
		
		g.pose().pushPose();
		g.enableScissor(notifX, notifY, notifX + this.notifWidth - this.statusBarWidth, notifY + this.notifHeight);
		g.setColor(1F, 1F, 1F, opacityFactor);
		
		g.fill(notifX + this.statusBarWidth, notifY, notifX + this.notifWidth - this.statusBarWidth, notifY + this.notifHeight, this.backgroundColor); // Draw background
		g.fill(notifX, notifY, notifX + this.statusBarWidth, notifY + this.notifHeight, this.notificationColor); // Draw notification type color bar
		g.drawString(font, this.title, notifX + this.textMargin + this.statusBarWidth, notifY + this.textMargin, this.textColor); // Draw title
		g.drawString(font, this.description, notifX + this.textMargin + this.statusBarWidth, notifY + font.lineHeight + (this.textMargin * 2), this.textColor); // Draw description
	
		g.disableScissor();
		g.pose().popPose();
	}
}
