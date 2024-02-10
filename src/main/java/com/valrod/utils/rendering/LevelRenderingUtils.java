package com.valrod.utils.rendering;

import java.awt.Color;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class LevelRenderingUtils {

	public static <T extends Entity> void renderNameTag(T ent, Component text, PoseStack pose, MultiBufferSource mbs) {
		pose.pushPose();
		pose.translate(0.0F, 1.4F, 0.0F);
		pose.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		pose.scale(-0.025F, -0.025F, 0.025F);
		Matrix4f matrix4f = pose.last().pose();
		Font font = EntityRenderer.getFont();
		float f2 = (float)(-font.width(text) / 2);
		float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int)(f1 * 255.0F) << 24;

		font.drawInBatch(text, f2, 0F, Color.blue.getRGB(), false, matrix4f, mbs, Font.DisplayMode.NORMAL, Color.red.getRGB(), 15728880);

		pose.popPose();
	}
}
