package com.valrod.client.ui.gui.screens;

import java.awt.Color;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.systems.RenderSystem;
import com.valrod.client.VClient;
import com.valrod.utils.rendering.GuiRenderingUtils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;

public class TitleScreen extends Screen {

	private final boolean fading;
	private final LogoRenderer logoRenderer;

	public TitleScreen() {
		this(false);
	}

	public TitleScreen(boolean fading) {
		this(fading, (LogoRenderer)null);
	}

	public TitleScreen(boolean fading, @Nullable LogoRenderer logoRenderer) {
		super(Component.translatable("narrator.screen.title"));
		this.fading = fading;
		this.logoRenderer = Objects.requireNonNullElseGet(logoRenderer, () -> {
			return new LogoRenderer(false);
		});
	}

	protected void init() {
		super.init();
		int yPos = this.height / 4 + 48;
		int xCenter = this.width / 2;

		this.addRenderableWidget(Button.builder(Component.translatable("menu.options"), (callback) -> {
			this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
		}).bounds(xCenter - 100, yPos + 72 + 12, 98, 20).build());

		this.addRenderableWidget(Button.builder(Component.translatable("menu.quit"), (callback) -> {
			this.minecraft.stop();
		}).bounds(xCenter + 2, yPos + 72 + 12, 98, 20).build());

		this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer"), (callback) -> {
			this.minecraft.setScreen(new SelectWorldScreen(this));
		}).bounds(xCenter - 100, yPos, 200, 20).build());

		this.addRenderableWidget(Button.builder(Component.literal("Alt Manager"), (callback) -> {
			this.minecraft.setScreen(new AltManager(this));
		}).bounds(xCenter - 100, yPos + 24 * 2, 200, 20).build());

		Component component = this.getMultiplayerDisabledReason();
		boolean flag = component == null;
		Tooltip tooltip = component != null ? Tooltip.create(component) : null;
		(this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), (callback) -> {
			this.minecraft.setScreen(new JoinMultiplayerScreen(this));
		}).bounds(xCenter - 100, yPos + 24 * 1, 200, 20).tooltip(tooltip).build())).active = flag;
	}

	@Nullable
	private Component getMultiplayerDisabledReason() {
		if (this.minecraft.allowsMultiplayer()) {
			return null;
		} else if (this.minecraft.isNameBanned()) {
			return Component.translatable("title.multiplayer.disabled.banned.name");
		} else {
			BanDetails bandetails = this.minecraft.multiplayerBan();
			if (bandetails != null) {
				return bandetails.expires() != null ? Component.translatable("title.multiplayer.disabled.banned.temporary") : Component.translatable("title.multiplayer.disabled.banned.permanent");
			} else {
				return Component.translatable("title.multiplayer.disabled");
			}
		}
	}

	public void render(GuiGraphics g, int mouseX, int mouseY, float pt) {
		super.render(g, mouseX, mouseY, pt);
		g.pose().pushPose();
		g.pose().translate(this.width/2, this.height / 4 - 50, 0F);	
		g.pose().scale(3F, 3F, 0F);
		g.drawCenteredString(this.font, VClient.CLIENT_NAME, 0, 0, 16777215);
		g.pose().popPose();

//		RenderSystem.limitDisplayFPS(1);
//		GuiRenderingUtils.circle(g, 40, 40, 20, Color.red.getRGB());

	}

	public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float pt) {
		super.renderBackground(g, mouseX, mouseY, pt);
	}

	public void onClose() {

	}

	public static CompletableFuture<Void> preloadResources(TextureManager tm, Executor ex) {
		return CompletableFuture.allOf(tm.preload(LogoRenderer.MINECRAFT_LOGO, ex), tm.preload(LogoRenderer.MINECRAFT_EDITION, ex));
	}

}
