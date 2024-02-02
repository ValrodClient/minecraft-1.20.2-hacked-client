package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ClientInformation;

public record CommonListenerCookie(GameProfile gameProfile, int latency, ClientInformation clientInformation) {
   public static CommonListenerCookie createInitial(GameProfile p_297256_) {
      return new CommonListenerCookie(p_297256_, 0, ClientInformation.createDefault());
   }
}