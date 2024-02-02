package com.valrod.client.events;

public class EventMotion extends Event{
	
	private double x, y, z, prevX, prevY, prevZ;
	private static float yaw;
	private static float pitch;
	private static float prevYaw, prevPitch;
	private boolean onGround, prevOnGround;
	
	public EventMotion(double posX, double posY, double posZ, float yaw, float pitch, float prevYaw, float prevPitch,
			boolean onGround) {
		super();
		this.x = posX;
		this.y = posY;
		this.z = posZ;
		this.yaw = yaw;
		this.pitch = pitch;
		this.prevYaw = prevYaw;
		this.prevPitch = prevPitch;
		this.onGround = onGround;
	}

	public boolean isPrevOnGround() {
		return prevOnGround;
	}

	public void setPrevOnGround(boolean prevOnGround) {
		this.prevOnGround = prevOnGround;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getPrevX() {
		return prevX;
	}

	public void setPrevX(double prevX) {
		this.prevX = prevX;
	}

	public double getPrevY() {
		return prevY;
	}

	public void setPrevY(double prevY) {
		this.prevY = prevY;
	}

	public double getPrevZ() {
		return prevZ;
	}

	public void setPrevZ(double prevZ) {
		this.prevZ = prevZ;
	}

	public static float getYRot() {
		return yaw;
	}

	public void setYRot(float yaw) {
		this.yaw = yaw;
	}

	public static float getXRot() {
		return pitch;
	}

	public void setXRot(float pitch) {
		this.pitch = pitch;
	}

	public static float getPrevYRot() {
		return prevYaw;
	}

	public void setPrevYRot(float prevYaw) {
		this.prevYaw = prevYaw;
	}

	public static float getPrevXRot() {
		return prevPitch;
	}

	public void setPrevXRot(float prevPitch) {
		this.prevPitch = prevPitch;
	}

	public boolean onGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
}
