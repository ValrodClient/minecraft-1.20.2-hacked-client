package com.valrod.client.ui.gui.screens;

import com.valrod.client.ui.gui.components.PasswordBox;
import com.valrod.utils.SessionLoginThread;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AltManager extends Screen {

	private Screen previousScreen;
	private EditBox usernameEditBox;
	private PasswordBox passwordEditBox;

	public AltManager() {
		super(Component.literal("Alt Manager"));
	}

	public AltManager(Screen previousScreen) {
		this();
		this.previousScreen = previousScreen;
	}

	protected void init() {
		super.init();
		int l = this.height / 4 + 48;
		int xCenter = this.width / 2;

		this.usernameEditBox = new EditBox(font, xCenter - 75, l, 150, 20, Component.literal("username"));
		this.usernameEditBox.setMaxLength(16);
		this.usernameEditBox.setHint(Component.literal("username").withStyle(ChatFormatting.DARK_GRAY));
		this.addRenderableWidget(this.usernameEditBox);

		this.passwordEditBox = new PasswordBox(font, xCenter - 75, l + 24, 150, 20, Component.literal("password"));
		this.passwordEditBox.setHint(Component.literal("password").withStyle(ChatFormatting.DARK_GRAY));
		this.addRenderableWidget(this.passwordEditBox);


		this.addRenderableWidget(Button.builder(Component.literal("Back"), (p_280838_) -> {
			this.minecraft.setScreen(this.previousScreen);
		}).bounds(this.width / 2 - 100, l + 72 + 12, 98, 20).build());

		this.addRenderableWidget(Button.builder(Component.literal("login"), (p_280831_) -> {
			login();
		}).bounds(this.width / 2 + 2, l + 72 + 12, 98, 20).build());

	}

	private void login() {
		try
		{
			new SessionLoginThread(this.usernameEditBox.getValue(), this.passwordEditBox.getValue()).start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void render(GuiGraphics g, int mouseX, int mouseY, float pt) {
		super.render(g, mouseX, mouseY, pt);

		//		g.drawCenteredString(this.font, this.title, mouseX, mouseY, 16777215);
	}

	public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float pt) {
		super.renderBackground(g, mouseX, mouseY, pt);
	}

	public void onClose() {

	}

}
