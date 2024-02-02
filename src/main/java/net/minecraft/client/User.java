package net.minecraft.client;

import com.mojang.util.UndashedUuid;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class User {
   private final String name;
   private final UUID uuid;
   private final String accessToken;
   private final Optional<String> xuid;
   private final Optional<String> clientId;
   private final User.Type type;

   public User(String name, UUID uuid, String token, Optional<String> xuid, Optional<String> clientid, User.Type type) {
      this.name = name;
      this.uuid = uuid;
      this.accessToken = token;
      this.xuid = xuid;
      this.clientId = clientid;
      this.type = type;
   }
   
   public User(String name, UUID uuid, String token, User.Type type) {
	      this.name = name;
	      this.uuid = uuid;
	      this.accessToken = token;
	      this.xuid = null;
	      this.clientId = null;
	      this.type = type;
	   }

   public String getSessionId() {
      return "token:" + this.accessToken + ":" + UndashedUuid.toString(this.uuid);
   }

   public UUID getProfileId() {
      return this.uuid;
   }

   public String getName() {
      return this.name;
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public Optional<String> getClientId() {
      return this.clientId;
   }

   public Optional<String> getXuid() {
      return this.xuid;
   }

   public User.Type getType() {
      return this.type;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Type {
      LEGACY("legacy"),
      MOJANG("mojang"),
      MSA("msa");

      private static final Map<String, User.Type> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap((p_92560_) -> {
         return p_92560_.name;
      }, Function.identity()));
      private final String name;

      private Type(String p_92558_) {
         this.name = p_92558_;
      }

      @Nullable
      public static User.Type byName(String p_92562_) {
         return BY_NAME.get(p_92562_.toLowerCase(Locale.ROOT));
      }

      public String getName() {
         return this.name;
      }
   }
}