package net.minecraft.client.renderer;

import com.valrod.client.VClient;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PanoramaRenderer {
   private final Minecraft minecraft;
   private final CubeMap cubeMap;
   private float spin;
   private float bob;

   public PanoramaRenderer(CubeMap p_110002_) {
      this.cubeMap = p_110002_;
      this.minecraft = Minecraft.getInstance();
   }

   public void render(float p_110004_, float p_110005_) {
      float f = (float)((double)p_110004_ * this.minecraft.options.panoramaSpeed().get());
      float a = (System.currentTimeMillis() - VClient.startTime) / 1000F;
      this.spin = wrap(a + f * 0.1F, 360.0F);
      this.bob = wrap(this.bob + f * 0.001F, ((float)Math.PI * 2F));
      this.cubeMap.render(this.minecraft, 10.0F, -this.spin, p_110005_);
   }

   private static float wrap(float p_249058_, float p_249548_) {
      return p_249058_ > p_249548_ ? p_249058_ - p_249548_ : p_249058_;
   }
}