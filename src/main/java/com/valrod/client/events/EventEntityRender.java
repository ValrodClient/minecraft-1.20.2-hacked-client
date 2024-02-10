package com.valrod.client.events;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;

public class EventEntityRender extends Event{
	
	private Entity entity;
	private double x, y, z;
	private PoseStack poseStack;
	private MultiBufferSource mbs;
	private float partialTick;
	private boolean shouldRenderOutline;
	
	public <E extends Entity> EventEntityRender(E ent, double x, double y, double z, PoseStack poseStack,
			MultiBufferSource mbs, float partialTick) {
		this.entity = ent;
		this.x = x;
		this.y = y;
		this.z = z;
		this.poseStack = poseStack;
		this.mbs = mbs;
		this.partialTick = partialTick;
		this.shouldRenderOutline = false;
	}

	public Entity getEntity() {
		return entity;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public PoseStack getPoseStack() {
		return poseStack;
	}

	public MultiBufferSource getMbs() {
		return mbs;
	}
	
	public void setMbs(MultiBufferSource mbs) {
		this.mbs = mbs;
	}

	public float getPartialTick() {
		return partialTick;
	}

	public void shouldRenderOutline(boolean shouldRenderOutline) {
		this.shouldRenderOutline = shouldRenderOutline;
	}

	public boolean shouldRenderOutline() {
		return this.shouldRenderOutline;
	}
	
}