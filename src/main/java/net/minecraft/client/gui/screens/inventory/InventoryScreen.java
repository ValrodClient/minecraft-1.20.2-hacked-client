package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class InventoryScreen extends EffectRenderingInventoryScreen<InventoryMenu> implements RecipeUpdateListener {
   private float xMouse;
   private float yMouse;
   private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
   private boolean widthTooNarrow;
   private boolean buttonClicked;

   public InventoryScreen(Player p_98839_) {
      super(p_98839_.inventoryMenu, p_98839_.getInventory(), Component.translatable("container.crafting"));
      this.titleLabelX = 97;
   }

   public void containerTick() {
      if (this.minecraft.gameMode.hasInfiniteItems()) {
         this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()));
      } else {
         this.recipeBookComponent.tick();
      }
   }

   protected void init() {
      if (this.minecraft.gameMode.hasInfiniteItems()) {
         this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()));
      } else {
         super.init();
         this.widthTooNarrow = this.width < 379;
         this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
         this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
         this.addRenderableWidget(new ImageButton(this.leftPos + 104, this.height / 2 - 22, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, (p_289631_) -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            p_289631_.setPosition(this.leftPos + 104, this.height / 2 - 22);
            this.buttonClicked = true;
         }));
         this.addWidget(this.recipeBookComponent);
         this.setInitialFocus(this.recipeBookComponent);
      }
   }

   protected void renderLabels(GuiGraphics p_281654_, int p_283517_, int p_283464_) {
      p_281654_.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
   }

   public void render(GuiGraphics p_283246_, int p_98876_, int p_98877_, float p_98878_) {
      if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
         this.renderBackground(p_283246_, p_98876_, p_98877_, p_98878_);
         this.recipeBookComponent.render(p_283246_, p_98876_, p_98877_, p_98878_);
      } else {
         super.render(p_283246_, p_98876_, p_98877_, p_98878_);
         this.recipeBookComponent.render(p_283246_, p_98876_, p_98877_, p_98878_);
         this.recipeBookComponent.renderGhostRecipe(p_283246_, this.leftPos, this.topPos, false, p_98878_);
      }

      this.renderTooltip(p_283246_, p_98876_, p_98877_);
      this.recipeBookComponent.renderTooltip(p_283246_, this.leftPos, this.topPos, p_98876_, p_98877_);
      this.xMouse = (float)p_98876_;
      this.yMouse = (float)p_98877_;
   }

   protected void renderBg(GuiGraphics g, float p_281299_, int p_283481_, int p_281831_) {
      int i = this.leftPos;
      int j = this.topPos;
      g.blit(INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
      renderEntityInInventoryFollowsMouse(g, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.xMouse, this.yMouse, this.minecraft.player);
   }

   public static void renderEntityInInventoryFollowsMouse(GuiGraphics g, int p_275688_, int p_275245_, int p_275535_, int p_301381_, int p_299741_, float size, float yaw, float pitch, LivingEntity ent) {
      float f = (float)(p_275688_ + p_275535_) / 2.0F;
      float f1 = (float)(p_275245_ + p_301381_) / 2.0F;
      g.enableScissor(p_275688_, p_275245_, p_275535_, p_301381_);
      float f2 = (float)Math.atan((double)((f - yaw) / 40.0F));
      float f3 = (float)Math.atan((double)((f1 - pitch) / 40.0F));
      Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
      Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f3 * 20.0F * ((float)Math.PI / 180F));
      quaternionf.mul(quaternionf1);
      float f4 = ent.yBodyRot;
      float f5 = ent.getYRot();
      float f6 = ent.getXRot();
      float f7 = ent.yHeadRotO;
      float f8 = ent.yHeadRot;
      ent.yBodyRot = 180.0F + f2 * 20.0F;
      ent.setYRot(180.0F + f2 * 40.0F);
      ent.setXRot(-f3 * 20.0F);
      ent.yHeadRot = ent.getYRot();
      ent.yHeadRotO = ent.getYRot();
      Vector3f vector3f = new Vector3f(0.0F, ent.getBbHeight() / 2.0F + size, 0.0F);
      renderEntityInInventory(g, f, f1, p_299741_, vector3f, quaternionf, quaternionf1, ent);
      ent.yBodyRot = f4;
      ent.setYRot(f5);
      ent.setXRot(f6);
      ent.yHeadRotO = f7;
      ent.yHeadRot = f8;
      g.disableScissor();
   }
   
   public static void renderEntityInInventory(GuiGraphics p_282665_, float p_300023_, float p_301239_, int p_283622_, Vector3f p_298037_, Quaternionf p_281880_, @Nullable Quaternionf p_282882_, LivingEntity p_282466_) {
      p_282665_.pose().pushPose();
      p_282665_.pose().translate((double)p_300023_, (double)p_301239_, 50.0D);
      p_282665_.pose().mulPoseMatrix((new Matrix4f()).scaling((float)p_283622_, (float)p_283622_, (float)(-p_283622_)));
      p_282665_.pose().translate(p_298037_.x, p_298037_.y, p_298037_.z);
      p_282665_.pose().mulPose(p_281880_);
      Lighting.setupForEntityInInventory();
      EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
      if (p_282882_ != null) {
         p_282882_.conjugate();
         entityrenderdispatcher.overrideCameraOrientation(p_282882_);
      }

      entityrenderdispatcher.setRenderShadow(false);
      RenderSystem.runAsFancy(() -> {
         entityrenderdispatcher.render(p_282466_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, p_282665_.pose(), p_282665_.bufferSource(), 15728880);
      });
      p_282665_.flush();
      entityrenderdispatcher.setRenderShadow(true);
      p_282665_.pose().popPose();
      Lighting.setupFor3DItems();
   }

   protected boolean isHovering(int p_98858_, int p_98859_, int p_98860_, int p_98861_, double p_98862_, double p_98863_) {
      return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(p_98858_, p_98859_, p_98860_, p_98861_, p_98862_, p_98863_);
   }

   public boolean mouseClicked(double p_98841_, double p_98842_, int p_98843_) {
      if (this.recipeBookComponent.mouseClicked(p_98841_, p_98842_, p_98843_)) {
         this.setFocused(this.recipeBookComponent);
         return true;
      } else {
         return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? false : super.mouseClicked(p_98841_, p_98842_, p_98843_);
      }
   }

   public boolean mouseReleased(double p_98893_, double p_98894_, int p_98895_) {
      if (this.buttonClicked) {
         this.buttonClicked = false;
         return true;
      } else {
         return super.mouseReleased(p_98893_, p_98894_, p_98895_);
      }
   }

   protected boolean hasClickedOutside(double p_98845_, double p_98846_, int p_98847_, int p_98848_, int p_98849_) {
      boolean flag = p_98845_ < (double)p_98847_ || p_98846_ < (double)p_98848_ || p_98845_ >= (double)(p_98847_ + this.imageWidth) || p_98846_ >= (double)(p_98848_ + this.imageHeight);
      return this.recipeBookComponent.hasClickedOutside(p_98845_, p_98846_, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, p_98849_) && flag;
   }

   protected void slotClicked(Slot p_98865_, int p_98866_, int p_98867_, ClickType p_98868_) {
      super.slotClicked(p_98865_, p_98866_, p_98867_, p_98868_);
      this.recipeBookComponent.slotClicked(p_98865_);
   }

   public void recipesUpdated() {
      this.recipeBookComponent.recipesUpdated();
   }

   public RecipeBookComponent getRecipeBookComponent() {
      return this.recipeBookComponent;
   }
}