package net.minecraft.client.multiplayer;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record CommonListenerCookie(GameProfile localGameProfile, RegistryAccess.Frozen receivedRegistries, FeatureFlagSet enabledFeatures, @Nullable String serverBrand, @Nullable ServerData serverData, @Nullable Screen postDisconnectScreen) {
}