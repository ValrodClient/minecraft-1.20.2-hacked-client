package com.valrod.utils.rendering;

import java.util.UUID;

import javax.annotation.Nullable;

import org.joml.Matrix4f;

import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.CommonComponents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomPlayerSkinWidget extends AbstractWidget {
	private static final float MODEL_OFFSET = 0.0625F;
	private static final float MODEL_HEIGHT = 2.125F;
	private static final float Z_OFFSET = 100.0F;
	private final CustomPlayerSkinWidget.Model model;
	private PlayerSkin skin;
	private UUID uuid;
	private float rotationX = -8.0F;
	private float rotationY;
	private int tick = 0;
	private long lastSkinChange;

	public CustomPlayerSkinWidget(int x, int y, int height, EntityModelSet model) {
		super(x, y, 0, height, CommonComponents.EMPTY);
		this.model = CustomPlayerSkinWidget.Model.bake(model);
		this.lastSkinChange = 0;
		updateSkin();
	}


	private void updateSkin() {
		Minecraft minecraft = Minecraft.getInstance();
		UUID playerID = minecraft.user.getProfileId();
		ProfileResult profileresult = minecraft.getMinecraftSessionService().fetchProfile(playerID, false);
		PlayerSkin playerskin = profileresult != null ? minecraft.getSkinManager().getInsecureSkin(profileresult.profile()) : DefaultPlayerSkin.get(playerID);

		if(this.uuid != playerID) {
			this.lastSkinChange = System.currentTimeMillis();
			this.tick = -20;
		}
		
		this.uuid = playerID;
		this.skin = playerskin;
	}
	
	private float maxRotationAngle() {
		float maxAnimationRot = 2000;
		int animationDurationMillis = 200;
		double animationProgress = 1;
		
		double a = animationDurationMillis - (System.currentTimeMillis() - this.lastSkinChange);
		animationProgress = 1 - (a / animationDurationMillis);
		
		if (animationProgress > 1)
			animationProgress = 1;
		
		float normalMaxRotation = 30F;
		
		float rotation = (float) (((maxAnimationRot - normalMaxRotation) * (1 - animationProgress)) + normalMaxRotation);
		return rotation;
	}

	protected void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
		updateSkin();

		this.rotationY =  (float) Math.sin(++this.tick / 80F) * maxRotationAngle();

		g.pose().pushPose();
		g.pose().translate((float)this.getX() + (float)this.getWidth() / 2.0F, (float)(this.getY() + this.getHeight()), 100.0F);
		float scale = (float)this.getHeight() / 2.125F;
		g.pose().scale(scale, scale, scale);
		g.pose().translate(0.0F, -0.0625F, 0.0F);
		Matrix4f matrix4f = g.pose().last().pose();
		matrix4f.rotateAround(Axis.XP.rotationDegrees(this.rotationX), 0.0F, -1.0625F, 0.0F);
		g.pose().mulPose(Axis.YP.rotationDegrees(this.rotationY));
		this.model.render(g, this.skin);
		g.pose().popPose();
	}

	public boolean isActive() {
		return false;
	}

	@Nullable
	public ComponentPath nextFocusPath(FocusNavigationEvent p_300388_) {
		return null;
	}

	@OnlyIn(Dist.CLIENT)
	static record Model(PlayerModel<?> wideModel, PlayerModel<?> slimModel) {
		public static CustomPlayerSkinWidget.Model bake(EntityModelSet p_300414_) {
			PlayerModel<?> playermodel = new PlayerModel(p_300414_.bakeLayer(ModelLayers.PLAYER), false);
			PlayerModel<?> playermodel1 = new PlayerModel(p_300414_.bakeLayer(ModelLayers.PLAYER_SLIM), true);
			playermodel.young = false;
			playermodel1.young = false;
			return new CustomPlayerSkinWidget.Model(playermodel, playermodel1);
		}

		public void render(GuiGraphics g, PlayerSkin skin) {
			g.flush();
			Lighting.setupForEntityInInventory();
			g.pose().pushPose();
			g.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, 1.0F, -1.0F));
			g.pose().translate(0.0F, -1.5F, 0.0F);
			PlayerModel<?> playermodel = skin.model() == PlayerSkin.Model.SLIM ? this.slimModel : this.wideModel;
			RenderType rendertype = playermodel.renderType(skin.texture());
			playermodel.renderToBuffer(g.pose(), g.bufferSource().getBuffer(rendertype), 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			g.pose().popPose();
			g.flush();
			Lighting.setupFor3DItems();
		}
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

	}
}