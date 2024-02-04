package com.valrod.client.events;

import net.minecraft.network.protocol.Packet;

public class EventPacketReceived extends Event{
	
	private Packet packet;
	private boolean canceled;
	
	public EventPacketReceived(Packet packet) {
		this.packet = packet;
	}
	
	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Packet getPacket() {
		return this.packet;
	}
}
