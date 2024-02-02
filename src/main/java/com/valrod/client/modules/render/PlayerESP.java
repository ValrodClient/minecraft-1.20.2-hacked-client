package com.valrod.client.modules.render;

import java.awt.event.KeyEvent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valrod.client.events.Event;
import com.valrod.client.modules.Category;
import com.valrod.client.modules.Module;


public class PlayerESP extends Module {

	public PlayerESP() {
		super("PlayerESP", "See player through walls", KeyEvent.VK_O, Category.RENDER);
	}

	public void onEnable() {
		//		this.mc.grabPanoramixScreenshot(this.mc.gameDirectory, 300, 300);
	}

	public void onDisable() {
	}

	public void onUpdate(Event event) {
//		if (!(event instanceof EventRender3D))
//			return;
//
//		for (Entity ent : mc.level.entitiesForRendering()) {
//			AABB aabb = ent.getBoundingBox().move(0, mc.player.getY(), 0);
//			MultiBufferSource mbs = this.mc.levelRenderer.renderBuffers.bufferSource();
//			PoseStack stack = new PoseStack();
//
//			stack.pushPose();
//			Vec3 vec3 = this.mc.getEntityRenderDispatcher().getRenderer(ent).getRenderOffset(ent, 1F);
//	         double d2 = mc.player.getX() + vec3.x();
//	         double d3 = mc.player.getY() + vec3.y();
//	         double d0 = mc.player.getZ() + vec3.z();
//
//			stack.translate(d2, d3, d0);
//			stack.rotateAround(null, getKeyCode(), getKeyCode(), getKeyCode());
//
//			LevelRenderer.renderLineBox(stack, mbs.getBuffer(RenderType.lines()), aabb, 1.0F, 1.0F, 1.0F, 1.0F);
//			LevelRenderer.renderLineBox(stack, mbs.getBuffer(RenderType.lines()), aabb.minX, (double)(ent.getEyeHeight() - 0.01F), aabb.minZ, aabb.maxX, (double)(ent.getEyeHeight() + 0.01F), aabb.maxZ, 1.0F, 0.0F, 0.0F, 1.0F);
//			stack.popPose();
//		}
	}

}
