package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;

public class ConstantInt extends IntProvider {
   public static final ConstantInt ZERO = new ConstantInt(0);
   public static final Codec<ConstantInt> CODEC = ExtraCodecs.withAlternative(Codec.INT, Codec.INT.fieldOf("value").codec()).xmap(ConstantInt::new, ConstantInt::getValue);
   private final int value;

   public static ConstantInt of(int p_146484_) {
      return p_146484_ == 0 ? ZERO : new ConstantInt(p_146484_);
   }

   private ConstantInt(int p_146481_) {
      this.value = p_146481_;
   }

   public int getValue() {
      return this.value;
   }

   public int sample(RandomSource p_216854_) {
      return this.value;
   }

   public int getMinValue() {
      return this.value;
   }

   public int getMaxValue() {
      return this.value;
   }

   public IntProviderType<?> getType() {
      return IntProviderType.CONSTANT;
   }

   public String toString() {
      return Integer.toString(this.value);
   }
}