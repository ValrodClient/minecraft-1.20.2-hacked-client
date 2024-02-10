package com.valrod.client.modules.implement.render;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.checkerframework.common.returnsreceiver.qual.This;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.gui.screens.RealmsBackupInfoScreen;
import com.valrod.client.events.Event;
import com.valrod.client.events.EventEntityRender;
import com.valrod.client.modules.Category;
import com.valrod.client.modules.Module;
import com.valrod.client.modules.settings.mode.Mode;
import com.valrod.client.modules.settings.mode.ModeSetting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


public class ModulePlayerESP extends Module {

	private final Mode MODE_OUTLINE = new Mode("Outline");
	private final Mode MODE_BOX = new Mode("Box");
	private final Mode MODE_SOLID = new Mode("Solid");
	private final ModeSetting modeSetting = new ModeSetting("Style", "ESP style", MODE_OUTLINE, MODE_BOX, MODE_SOLID);

	public ModulePlayerESP() {
		super("PlayerESP", "See player through walls", KeyEvent.VK_O, Category.RENDER);
	}

	public void onEnable() {
		this.modeSetting.cycle();
	}

	public void onDisable() {

	}

	public void onUpdate(Event event) {

		if (event instanceof EventEntityRender) {
			EventEntityRender eventEntityRenderer = (EventEntityRender)event;
			float partialTick = eventEntityRenderer.getPartialTick();
			Entity ent = eventEntityRenderer.getEntity();

			if (ent.is(this.mc.player)) return;

			PoseStack stack = eventEntityRenderer.getPoseStack();
			MultiBufferSource mbs = eventEntityRenderer.getMbs();

			int color = new Color(30, 240, 110).getRGB();
			int entityTeamColor = ent.getTeamColor();

			Mode currMode = this.modeSetting.getCurrentMode();
			if (currMode == MODE_OUTLINE) {
				outlineESP(ent, stack, eventEntityRenderer, partialTick, color);
			} else if (currMode == MODE_BOX) {
				boxESP(ent, stack, mbs, eventEntityRenderer, partialTick, color);
			} else if (currMode == MODE_SOLID) {
				solidESP(ent, stack, eventEntityRenderer, color);
			}
		}
	}

	private void outlineESP(Entity ent, PoseStack stack, EventEntityRender eventEntityRenderer, float partialTick, int color) {
		OutlineBufferSource outlinebuffersource = this.mc.levelRenderer.renderBuffers.outlineBufferSource();
		outlinebuffersource.setColor(FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color), 255);
		eventEntityRenderer.setMbs(outlinebuffersource);
		eventEntityRenderer.shouldRenderOutline(true);
	}

	private void solidESP(Entity ent, PoseStack stack, EventEntityRender eventEntityRenderer, int color) {
		OutlineBufferSource outlinebuffersource = this.mc.levelRenderer.renderBuffers.outlineBufferSource();
		outlinebuffersource.setColor(FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color), 255);
		eventEntityRenderer.setMbs(outlinebuffersource);
	}

	private void boxESP(Entity ent, PoseStack stack, MultiBufferSource mbs, EventEntityRender eventEntityRenderer, float partialTick, int color) {
		double x = -Mth.lerp((double)partialTick, this.mc.player.xOld, this.mc.player.getX());
        double y = -Mth.lerp((double)partialTick, this.mc.player.yOld, this.mc.player.getY()) - this.mc.player.getEyeHeight();
        double z = -Mth.lerp((double)partialTick, this.mc.player.zOld, this.mc.player.getZ());
		
        double entXdiff = Mth.lerp((double)partialTick, ent.xOld, ent.getX()) - ent.getX();
        double entYdiff = Mth.lerp((double)partialTick, ent.yOld, ent.getY()) - ent.getY();
        double entZdiff = Mth.lerp((double)partialTick, ent.zOld, ent.getZ()) - ent.getZ();

		AABB aabb = ent.getBoundingBox().move(x + entXdiff, y + entYdiff, z + entZdiff);
		LevelRenderer.renderLineBox(stack, mbs.getBuffer(RenderType.lines()), aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ, FastColor.ARGB32.red(color) / 255F, FastColor.ARGB32.green(color) / 255F, FastColor.ARGB32.blue(color) / 255F, 1F);
	}

}
