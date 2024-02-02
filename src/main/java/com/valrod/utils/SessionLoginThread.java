package com.valrod.utils;

import java.util.UUID;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

public final class SessionLoginThread
extends Thread {
	private final String password;
	private String status;
	private final String username;
	private Minecraft mc = Minecraft.getInstance();

	public SessionLoginThread(String username, String password) {
		super("Alt Login Thread");
		this.username = username;
		this.password = password;
		this.status = "Waiting...";
	}

	private User createSession(String username, String password) {
		try {
			MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
			MicrosoftAuthResult result = null;

			result = authenticator.loginWithCredentials(username, password);
			MinecraftProfile profile = result.getProfile();

			System.out.printf("Logged in with '%s'%n", result.getProfile().getName());
			
			String id = result.getProfile().getId();
			String formattedUUID = String.format(
		            "%s-%s-%s-%s-%s",
		            id.substring(0, 8),
		            id.substring(8, 12),
		            id.substring(12, 16),
		            id.substring(16, 20),
		            id.substring(20)
		        );
			
			UUID uuid = UUID.fromString(formattedUUID);
			return new User(profile.getName(), uuid, result.getAccessToken(), User.Type.MSA);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getStatus() {
		return this.status;
	}

	@Override
	public void run() {
		if (this.password.equals("")) {
			this.mc.user = new User(this.username, null, "", User.Type.LEGACY);
			this.status = "Logged in. (" + this.username + " - offline name)";
			return;
		}
		this.status = "Logging in...";
		User auth = this.createSession(this.username, this.password);
		if (auth == null) {
			this.status = "Login failed!";
		} else {
			this.status = "Logged in. (" + auth.getName() + ")";
			this.mc.user = auth;
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}
}