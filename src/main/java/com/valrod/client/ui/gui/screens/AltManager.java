package com.valrod.client.ui.gui.screens;

import com.valrod.client.ui.gui.components.PasswordBox;
import com.valrod.utils.SessionLoginThread;
import com.valrod.utils.rendering.CustomPlayerSkinWidget;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AltManager extends Screen {

	private Screen previousScreen;
	private EditBox usernameEditBox;
	private EditBox emailEditBox;
	private PasswordBox passwordEditBox;

	private static String status;

	public AltManager() {
		super(Component.literal("Alt Manager"));
	}

	public AltManager(String status) {
		this();
		this.status = status;
	}

	public AltManager(Screen previousScreen) {
		this("Logged in as : " + Minecraft.getInstance().user.getName());
		this.previousScreen = previousScreen;
	}

	public static void setStatus(String status) {
		AltManager.status = status;
	}

	protected void init() {
		super.init();
		int l = this.height / 4;
		int xCenter = this.width / 2;
//		int yCenter = this.height / 2;
		
//		int panelWidth = 300;
//		int panelHeight = 500;
//		
//		int xPos = xCenter - (panelWidth / 2);
//		int yPos = yCenter - (panelHeight / 2);
		
		// Username text field
		this.usernameEditBox = new EditBox(font, xCenter - 75, l, 150, 20, Component.literal("username"));
		this.usernameEditBox.setMaxLength(64);
		this.usernameEditBox.setHint(Component.literal("username").withStyle(ChatFormatting.DARK_GRAY));
		this.addRenderableWidget(this.usernameEditBox);

		// Email text field
		this.emailEditBox = new EditBox(font, xCenter - 75, l + 24, 150, 20, Component.literal("email"));
		this.emailEditBox.setMaxLength(64);
		this.emailEditBox.setHint(Component.literal("email").withStyle(ChatFormatting.DARK_GRAY));
		this.addRenderableWidget(this.emailEditBox);

		// Password text field
		this.passwordEditBox = new PasswordBox(font, xCenter - 75, l + 24 * 2, 150, 20, Component.literal("password"));
		this.passwordEditBox.setHint(Component.literal("password").withStyle(ChatFormatting.DARK_GRAY));
		this.addRenderableWidget(this.passwordEditBox);

		// Player skin widget
		CustomPlayerSkinWidget playerSkinWidget = new CustomPlayerSkinWidget(xCenter - 140, l - 50, 150, this.minecraft.getEntityModels());
		this.addRenderableWidget(playerSkinWidget);

		// Previous button
		this.addRenderableWidget(Button.builder(Component.literal("Back"), (p_280838_) -> {
			this.minecraft.setScreen(this.previousScreen);
		}).bounds(this.width / 2 - 100, l + 72 + 12, 98, 20).build());

		// Login button
		this.addRenderableWidget(Button.builder(Component.literal("login"), (p_280831_) -> {
			login();
		}).bounds(this.width / 2 + 2, l + 72 + 12, 98, 20).build());

	}

	private void login() {
		String username = this.usernameEditBox.getValue();
		String email = this.emailEditBox.getValue();
		String password = this.passwordEditBox.getValue();

		new SessionLoginThread(email.equals("") ? username : email, password).start();
	}

	public void render(GuiGraphics g, int mouseX, int mouseY, float pt) {
		super.render(g, mouseX, mouseY, pt);

		g.drawCenteredString(this.font, AltManager.status, g.guiWidth()/2, g.guiHeight()/8, 16777215);
	}

	public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float pt) {
		super.renderBackground(g, mouseX, mouseY, pt);
	}

	public void onClose() {

	}

}
